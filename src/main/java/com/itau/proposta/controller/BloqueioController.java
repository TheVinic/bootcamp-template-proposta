package com.itau.proposta.controller;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.itau.proposta.bloqueio.CartoesBloqueioResponse;
import com.itau.proposta.bloqueio.StatusRetornoBloqueioCartao;
import com.itau.proposta.cartao.Cartao;
import com.itau.proposta.cartao.IntegracoesCartoes;
import com.itau.proposta.exception.ApiErroException;
import com.itau.proposta.geral.ExecutorTransacao;

@RestController
@RequestMapping("/api")
public class BloqueioController {

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	private IntegracoesCartoes integracoes;
	@Autowired
	private ExecutorTransacao executorTransacao;
	
	@PostMapping("/cartoes/{id}/bloqueio")
	public ResponseEntity<?> BloqueiaCartao(@PathVariable("id") String id, @RequestHeader HttpHeaders headers,
			HttpServletRequest httpRequest, UriComponentsBuilder builder) {

		validaRequest(headers, httpRequest);

		Cartao cartao = manager.find(Cartao.class, id);
		
		CartoesBloqueioResponse responseCartoes = integracoes.bloqueiaCartao(cartao.getNumero());
		if(responseCartoes.getResultado()!=StatusRetornoBloqueioCartao.BLOQUEADO) {
			throw new ApiErroException(HttpStatus.PRECONDITION_FAILED, "Falha ao bloquear cartão.");
		}
		
		cartao.bloqueia(headers.get(HttpHeaders.USER_AGENT).get(0), httpRequest.getRemoteAddr());
		executorTransacao.atualizaEComita(cartao);
		
		URI enderecoConsulta = builder.path("/api/cartoes/{id}/bloqueio").build(cartao.getId());
		
		return ResponseEntity.created(enderecoConsulta).build();

	}

	private void validaRequest(HttpHeaders headers, HttpServletRequest httpRequest) {

		if(!headers.containsKey(HttpHeaders.USER_AGENT) || headers.get(HttpHeaders.USER_AGENT).isEmpty()) {
			throw new ApiErroException(HttpStatus.PRECONDITION_FAILED, "Não possui user-agent.");
		}
		if(!StringUtils.hasText(httpRequest.getRemoteAddr())) {
			throw new ApiErroException(HttpStatus.PRECONDITION_FAILED, "Não possui ip.");			
		}
		
	}

}
