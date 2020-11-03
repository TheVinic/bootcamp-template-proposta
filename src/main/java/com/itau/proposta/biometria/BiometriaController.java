package com.itau.proposta.biometria;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.itau.proposta.geral.ExecutorTransacao;

@RestController
@RequestMapping("/api")
public class BiometriaController {

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	private ExecutorTransacao executorTransacao;
	
	@PostMapping("/cartoes/{id}/biometria") 
	public ResponseEntity<?> NovaBiometria(@PathVariable("id") String id,
			@RequestBody @Valid BiometriaRequest request, UriComponentsBuilder builder) {
		
		Cartao cartao = manager.find(Cartao.class, id);
		if(cartao == null) {
			throw new ApiErroException(HttpStatus.NOT_FOUND,"Não encontrado.");
		}

		cartao.adicionaBiometria(request.getDigital());
		executorTransacao.atualizaEComita(cartao);		
		
		URI enderecoConsulta = builder.path("/api/cartoes/{idCartao}/biometria").build(cartao.getId());
		
		return ResponseEntity.created(enderecoConsulta).build();

	}

}
