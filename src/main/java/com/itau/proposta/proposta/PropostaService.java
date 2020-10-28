package com.itau.proposta.proposta;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

@Service
public class PropostaService {

	public String novaProposta(@Valid PropostaRequest request, EntityManager manager) {
		
		PropostaEntity proposta = request.toModel();
		
		manager.persist(proposta);
		
		return proposta.getId();
		
	}

	public PropostaEntity consultaProposta(String id_compra, EntityManager manager) {

		return manager.find(PropostaEntity.class, id_compra);
		
	}

}
