package com.itau.proposta.controller;

import com.itau.proposta.carteira.AssociaCarteiraRequest;
import com.itau.proposta.carteira.CarteirasDisponiveis;

public interface AssociaCarterteiraRequest {

	public AssociaCarteiraRequest toModel(CarteirasDisponiveis carteira);
	
	public String getEmail();
}
