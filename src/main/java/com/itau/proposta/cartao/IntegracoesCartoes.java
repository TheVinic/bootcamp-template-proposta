package com.itau.proposta.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itau.proposta.bloqueio.CartoesBloqueioResponse;

@FeignClient(url = "${endereco-cartoes.base-url}", name = "cartao")
public interface IntegracoesCartoes {

	@GetMapping("/api/cartoes")
	public CartoesResponse buscaNumeroCartao(@RequestParam(value = "idProposta") String idProposta);
	
	@PostMapping("/api/cartoes/{id}/bloqueios")
	public CartoesBloqueioResponse bloqueiaCartao(@PathVariable(value = "id") String idProposta);
	
}
