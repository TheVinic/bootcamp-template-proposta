package com.itau.proposta.geral;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.itau.proposta.dadosSolicitante.DadosFinanceirosRequest;
import com.itau.proposta.dadosSolicitante.DadosFinanceirosResponse;

@FeignClient(url = "${enderecos-externos.base-url}", name = "integracoes")
public interface Integracoes {

	@PostMapping("/api/solicitacao")
	public DadosFinanceirosResponse avalia(DadosFinanceirosRequest request);
	
}