package com.itau.proposta.controller;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.itau.proposta.carteira.AssociaCarteiraRequest;
import com.itau.proposta.carteira.CarteirasDisponiveis;

public class AssociaSamsungRequest implements AssociaCarterteiraRequest{

	@NotBlank
	@Email
	private String email;
	
	@Override
	public AssociaCarteiraRequest toModel(CarteirasDisponiveis carteira) {
		return new AssociaCarteiraRequest(this.email, carteira);
	}

	@Override
	public String getEmail() {
		return email;
	}

}
