package com.itau.proposta.dadosSolicitante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itau.proposta.geral.Integracoes;
import com.itau.proposta.proposta.PropostaEntity;
import com.itau.proposta.proposta.StatusAvaliacaoProposta;

@Service
public class ConsultaDadosSolicitante {

	@Autowired
	private Integracoes integracoes;

	public StatusAvaliacaoProposta consulta(PropostaEntity proposta) {

		DadosFinanceirosResponse resultadoAvaliacao = integracoes.avalia(new DadosFinanceirosRequest(proposta));

		return resultadoAvaliacao.getresultadoSolicitacao().getStatusAvaliacao();
		
	}

}
