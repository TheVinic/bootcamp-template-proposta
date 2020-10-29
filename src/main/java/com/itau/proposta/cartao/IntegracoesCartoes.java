package com.itau.proposta.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${endereco-cartoes.base-url}", name = "cartao")
public interface IntegracoesCartoes {

	@GetMapping("/api/cartoes")
	public CartoesResponse buscaNumeroCartao(@RequestParam(value = "idProposta") String idProposta);
	
}
