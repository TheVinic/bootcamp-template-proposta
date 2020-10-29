package com.itau.proposta.geral;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.itau.proposta.dadosSolicitante.DadosFinanceirosRequest;
import com.itau.proposta.dadosSolicitante.DadosFinanceirosResponse;

@FeignClient(url = "${endereco-solicitacao-financeira.base-url}", name = "solicitacao")
public interface IntegracoesSolicitacaoFinanceira {
	
	@PostMapping("/api/solicitacao")
	public DadosFinanceirosResponse avalia(DadosFinanceirosRequest request);
	
}