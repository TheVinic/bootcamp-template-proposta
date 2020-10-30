package com.itau.proposta.biometria;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.itau.proposta.cartao.Cartao;
import com.itau.proposta.exception.ApiErroException;

@RestController
@RequestMapping("/api")
@Transactional
public class BiometriaController {

	@PersistenceContext
	private EntityManager manager;
	
	@PostMapping("/cartoes/{id}/biometria") 
	public ResponseEntity<?> NovaBiometria(@PathVariable("id") String id,
			@RequestBody @Valid BiometriaRequest request, UriComponentsBuilder builder) {
		
		Cartao cartao = manager.find(Cartao.class, id);
		if(cartao == null) {
			throw new ApiErroException(HttpStatus.NOT_FOUND,"Não encontrado.");
		}

		cartao.adicionaBiometria(request.getDigital());
		
		URI enderecoConsulta = builder.path("/api/cartoes/{idCartao}/biometria").build(cartao.getId());
		
		return ResponseEntity.created(enderecoConsulta).build();

	}

}
