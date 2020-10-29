package com.itau.proposta.proposta;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itau.proposta.dadosSolicitante.ConsultaDadosSolicitante;
import com.itau.proposta.geral.ExecutorTransacao;

@Service
public class PropostaService {

	@Autowired
	private ConsultaDadosSolicitante consultaDadosFinanceiros;
	
	@Autowired
	private ExecutorTransacao executorTransacao;
	
	public String novaProposta(@Valid PropostaRequest request, EntityManager manager) {
		
		PropostaEntity proposta = request.toModel();
		executorTransacao.salvaEComita(proposta);
		
		StatusAvaliacaoProposta avaliacao = consultaDadosFinanceiros.consulta(proposta);
		proposta.setStatusAvaliacao(avaliacao);
		executorTransacao.atualizaEComita(proposta);
		
		return proposta.getId();
		
	}

	public PropostaEntity consultaProposta(String id_compra, EntityManager manager) {

		return manager.find(PropostaEntity.class, id_compra);
		
	}

}
