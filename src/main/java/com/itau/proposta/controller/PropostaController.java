package com.itau.proposta.controller;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.itau.proposta.proposta.PropostaEntity;
import com.itau.proposta.proposta.PropostaRequest;
import com.itau.proposta.proposta.PropostaResponse;
import com.itau.proposta.proposta.PropostaService;

@RestController
@RequestMapping("/api")
public class PropostaController {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PropostaService propostaService;
	
	@PostMapping("/propostas")
	public ResponseEntity<?> NovaProposta(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder builder){
		
		String idProposta = propostaService.novaProposta(request, manager);
		
		URI enderecoConsulta = builder.path("/api/propostas/{id}").build(idProposta);
		
		return ResponseEntity.created(enderecoConsulta).build();
		
	}
	
	@GetMapping("/propostas/{id_proposta}")
	public ResponseEntity<?> ConsultaProposta(@PathVariable("id_proposta") @NotBlank String id_compra){
		
		PropostaEntity proposta = propostaService.consultaProposta(id_compra, manager);
		if(proposta == null) {
			return ResponseEntity.notFound().build();
		}else {
			PropostaResponse propostaResponse = proposta.toResponse();
			return ResponseEntity.ok(propostaResponse);
		}
		
	}
	
}
