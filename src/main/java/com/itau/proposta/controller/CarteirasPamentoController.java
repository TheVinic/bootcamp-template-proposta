package com.itau.proposta.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.itau.proposta.carteira.AssociaPaypalRequest;
import com.itau.proposta.carteira.AssociaSamsungRequest;
import com.itau.proposta.carteira.CarteirasDisponiveis;

@RestController
@Validated
@RequestMapping("/api")
public class CarteirasPamentoController {

	@Autowired
	private AssociaCarteiraService associaCarteiraService;

	@PostMapping(value = "/cartoes/{id}/associa-paypal")
	public ResponseEntity<?> AssociaPaypal(@PathVariable("id") String id, @RequestBody AssociaPaypalRequest request,
			UriComponentsBuilder builder) {

		String idCarteiraPaypal = associaCarteiraService.ComunicaComSistemaCartoes(id, request,
				CarteirasDisponiveis.PAYPAL);

		URI enderecoConsulta = builder.path("/cartoes/cartoes/{id}/associa-paypal/{idCarteiraPaypal}").build(id,
				idCarteiraPaypal);
		return ResponseEntity.created(enderecoConsulta).build();
	}

	@PostMapping(value = "/cartoes/{id}/associa-samsung")
	public ResponseEntity<?> AssociaSamsung(@PathVariable("id") String id, @RequestBody AssociaSamsungRequest request,
			UriComponentsBuilder builder) {

		String idCarteiraSamsung = associaCarteiraService.ComunicaComSistemaCartoes(id, request,
				CarteirasDisponiveis.SAMSUNG);

		URI enderecoConsulta = builder.path("/cartoes/cartoes/{id}/associa-samsung/{idCarteiraSamsung}").build(id,
				idCarteiraSamsung);
		return ResponseEntity.created(enderecoConsulta).build();
	}
}
