package com.itau.proposta.controller;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.itau.proposta.cartao.Cartao;
import com.itau.proposta.cartao.IntegracoesCartoes;
import com.itau.proposta.carteira.AssociaCarteiraResponse;
import com.itau.proposta.carteira.CarteirasDisponiveis;
import com.itau.proposta.carteira.RetornosPossiveisAssociaCarteira;
import com.itau.proposta.exception.ApiErroException;
import com.itau.proposta.exception.FeignSkipBadRequestsConfiguration.FeignBadResponseWrapper;
import com.itau.proposta.geral.ExecutorTransacao;
import com.netflix.hystrix.exception.HystrixBadRequestException;

@Service
public class AssociaCarteiraService {

	@Autowired
	private EntityManager manager;
	@Autowired
	private IntegracoesCartoes integracoes;
	@Autowired
	private ExecutorTransacao executorTransacao;

	public String ComunicaComSistemaCartoes(String id, AssociaCarterteiraRequest request,
			@NotNull CarteirasDisponiveis carteira) {
		Cartao cartao = manager.find(Cartao.class, id);
		if (cartao == null) {
			throw new ApiErroException(HttpStatus.NOT_FOUND, "Não encontrado.");
		}

		cartao.validaCarteiraPaypal();
		AssociaCarteiraResponse retornoCartoes = null;

		try {
			retornoCartoes = integracoes.associaPaypal(cartao.getNumero(), request.toModel(carteira));
		} catch (HystrixBadRequestException he) {
			throw new ApiErroException(((FeignBadResponseWrapper) he).getStatus(), "Erro na comunicação com cartões.");
		}

		if (retornoCartoes.getResultado() != RetornosPossiveisAssociaCarteira.ASSOCIADA) {
			throw new ApiErroException(HttpStatus.BAD_REQUEST, "Erro na comunicação.");
		}

		if (carteira == CarteirasDisponiveis.PAYPAL) {
			executorTransacao.atualizaEComita(GravaCarteiraPaypal(cartao, request.getEmail()));
		} else if (carteira == CarteirasDisponiveis.SAMSUNG) {
			executorTransacao.atualizaEComita(GravaCarteiraSamsung(cartao, request.getEmail()));
		}

		return cartao.getCarteiraPaypal().getId();
	}

	public Cartao GravaCarteiraPaypal(Cartao cartao, String email) {
		return cartao.associaPaypal(email);
	}

	public Cartao GravaCarteiraSamsung(Cartao cartao, String email) {
		return cartao.associaSamguns(email);
	}

}
