package com.itau.proposta.controller;

import java.net.URI;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.itau.proposta.cartao.Cartao;
import com.itau.proposta.cartao.IntegracoesCartoes;
import com.itau.proposta.carteira.AssociaCarteiraResponse;
import com.itau.proposta.carteira.AssociaPaypalRequest;
import com.itau.proposta.carteira.CarteirasDisponiveis;
import com.itau.proposta.carteira.RetornosPossiveisAssociaCarteira;
import com.itau.proposta.exception.ApiErroException;
import com.itau.proposta.exception.FeignSkipBadRequestsConfiguration.FeignBadResponseWrapper;
import com.itau.proposta.geral.ExecutorTransacao;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@RestController
@Validated
@RequestMapping("/api")
public class CarteirasPamentoController {

	@Autowired
	private EntityManager manager;
	@Autowired
	private IntegracoesCartoes integracoes;
	@Autowired
	private ExecutorTransacao executorTransacao;

	@PostMapping(value = "/cartoes/{id}/associa-paypal")
	public ResponseEntity<?> AssociaPaypal(@PathVariable("id") String id, @RequestBody AssociaPaypalRequest request,
			UriComponentsBuilder builder) {

		Cartao cartao = manager.find(Cartao.class, id);
		if (cartao == null) {
			throw new ApiErroException(HttpStatus.NOT_FOUND, "Não encontrado.");
		}

		cartao.validaCarteiraPaypal();
		AssociaCarteiraResponse retornoCartoes = null;

		try {
			retornoCartoes = integracoes.associaPaypal(cartao.getNumero(),
					request.toModel(CarteirasDisponiveis.PAYPAL));
		} catch (HystrixBadRequestException he) {
			throw new ApiErroException(((FeignBadResponseWrapper) he).getStatus(), "Erro na comunicação com cartões.");
		}

		if (retornoCartoes.getResultado() != RetornosPossiveisAssociaCarteira.ASSOCIADA) {
			throw new ApiErroException(HttpStatus.BAD_REQUEST, "Erro na comunicação.");
		}

		cartao.associaPaypal(request.getEmail());
		executorTransacao.atualizaEComita(cartao);

		URI enderecoConsulta = builder.path("/cartoes/cartoes/{id}/associa-paypal/{idCarteiraPaypal}").build(id,
				cartao.getCarteiraPaypal().getId());
		return ResponseEntity.created(enderecoConsulta).build();

	}

}
