package com.itau.proposta.recuperaSenha;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

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

import com.itau.proposta.cartao.Cartao;
import com.itau.proposta.exception.ApiErroException;

@RestController
@RequestMapping("/api")
@Transactional
public class RecuperaSenhaController {

	@PersistenceContext
	private EntityManager manager;

	@PostMapping("/cartoes/{id}/recupera_senha")
	public ResponseEntity<?> RecuperaSenha(@PathVariable("id") String id, @RequestHeader HttpHeaders headers,
			HttpServletRequest httpRequest, UriComponentsBuilder builder) {

		validaRequest(headers, httpRequest);

		Cartao cartao = manager.find(Cartao.class, id);
		if(cartao == null) {
			throw new ApiErroException(HttpStatus.NOT_FOUND, "Não encontrado.");
		}
 
		RecuperaSenha recuperaSenha = new RecuperaSenha(cartao, headers.get(HttpHeaders.USER_AGENT).get(0),
				httpRequest.getRemoteAddr());
		manager.persist(recuperaSenha);
		
		URI enderecoConsulta = builder.path("/cartoes/{idCartao}/recupera_senha/{idRecuperaSenha}").build(id, recuperaSenha.getId());
		return ResponseEntity.created(enderecoConsulta).build();
	}

	private void validaRequest(HttpHeaders headers, HttpServletRequest httpRequest) {

		if (!headers.containsKey(HttpHeaders.USER_AGENT) || headers.get(HttpHeaders.USER_AGENT).isEmpty()) {
			throw new ApiErroException(HttpStatus.PRECONDITION_FAILED, "Não possui user-agent.");
		}
		if (!StringUtils.hasText(httpRequest.getRemoteAddr())) {
			throw new ApiErroException(HttpStatus.PRECONDITION_FAILED, "Não possui ip.");
		}

	}

}
