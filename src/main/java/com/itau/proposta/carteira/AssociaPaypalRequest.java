package com.itau.proposta.carteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.itau.proposta.controller.AssociaCarterteiraRequest;

public class AssociaPaypalRequest implements AssociaCarterteiraRequest{

	@NotBlank
	@Email
	private String email;

	public String getEmail() {
		return email;
	}

	public AssociaCarteiraRequest toModel(CarteirasDisponiveis carteira) {
		return new AssociaCarteiraRequest(this.email, carteira);
	}
	
}
