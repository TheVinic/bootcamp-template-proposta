package com.itau.proposta.avisoViagem;

import java.net.URI;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.itau.proposta.cartao.Cartao;
import com.itau.proposta.exception.ApiErroException;

@RestController
@RequestMapping("/api")
@Transactional
public class AvisoViagemController {

	@Autowired
	private EntityManager manager;

	@PostMapping("/cartoes/{id}/aviso_viagem")
	public ResponseEntity<?> NovoAvisoViagem(@PathVariable("id") String id, @RequestHeader HttpHeaders headers,
			@RequestBody AvisoViagemRequest request, HttpServletRequest httpRequest, UriComponentsBuilder builder) {

		Cartao cartao = manager.find(Cartao.class, id);
		if (cartao == null) {
			throw new ApiErroException(HttpStatus.NOT_FOUND, "Não encontrado.");
		}

		AvisoViagem avisoViagem = new AvisoViagem(cartao, request.getDestinoViagem(), request.getDataTerminoViagem(),
				headers.get(HttpHeaders.USER_AGENT).get(0), httpRequest.getRemoteAddr());
		manager.persist(avisoViagem);

		URI enderecoConsulta = builder.path("/cartoes/cartoes/{id}/aviso_viagem/{idRecuperaSenha}").build(id, avisoViagem.getId());
		return ResponseEntity.created(enderecoConsulta).build();

	}

}
