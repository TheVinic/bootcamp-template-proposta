package com.itau.proposta.proposta;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itau.proposta.dadosSolicitante.ConsultaDadosSolicitante;
import com.itau.proposta.exception.ApiErroException;
import com.itau.proposta.exception.FeignSkipBadRequestsConfiguration.FeignBadResponseWrapper;
import com.itau.proposta.geral.ExecutorTransacao;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@Service
public class PropostaService {

	@Autowired
	private ConsultaDadosSolicitante consultaDadosFinanceiros;

	@Autowired
	private ExecutorTransacao executorTransacao;

	public String novaProposta(@Valid PropostaRequest request, EntityManager manager) {

		PropostaEntity proposta = request.toModel();
		executorTransacao.salvaEComita(proposta);
		StatusAvaliacaoProposta avaliacao = null;

		try {
			avaliacao = consultaDadosFinanceiros.consulta(proposta);
		} catch (HystrixBadRequestException he) {
			if ((((FeignBadResponseWrapper) he).getStatus()) == 422) {
				proposta.setStatusAvaliacao(StatusAvaliacaoProposta.NAO_ELEGIVEL);
				executorTransacao.atualizaEComita(proposta);

				return proposta.getId();
			}
			throw new ApiErroException(((FeignBadResponseWrapper) he).getStatus(), "Erro na comunicação com cartões.");
		}
		proposta.setStatusAvaliacao(avaliacao);
		executorTransacao.atualizaEComita(proposta);

		return proposta.getId();

	}

	public PropostaEntity consultaProposta(String id_compra, EntityManager manager) {

		return manager.find(PropostaEntity.class, id_compra);

	}

}
