package com.itau.proposta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itau.proposta.cartao.CartoesResponse;
import com.itau.proposta.cartao.IntegracoesCartoes;
import com.itau.proposta.geral.ExecutorTransacao;
import com.itau.proposta.proposta.PropostaEntity;
import com.itau.proposta.proposta.PropostaRepository;
import com.itau.proposta.proposta.StatusAvaliacaoProposta;

@RestController
@RequestMapping("/api")
public class AssociaPropostaCartao {

	@Autowired
	private IntegracoesCartoes integracoes;
	@Autowired
	private ExecutorTransacao executorTransacao;
	@Autowired
	private PropostaRepository propostaRepository;
		
	@Scheduled(fixedDelayString = "${periodicidade.associa-proposta-cartao}")
	@GetMapping("/executa-associacao-proposta")
	public void RecuperaCartao (){
		
		Iterable<PropostaEntity> propostas = propostaRepository.todasSemCartao(StatusAvaliacaoProposta.ELEGIVEL);
		
 		for (PropostaEntity proposta : propostas) {
			CartoesResponse cartao = integracoes.buscaNumeroCartao(proposta.getId());
			proposta.associaCartao(cartao);
			executorTransacao.atualizaEComita(proposta);
		}
		
	}
	
}
