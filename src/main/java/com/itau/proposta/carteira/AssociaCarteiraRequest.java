package com.itau.proposta.carteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AssociaCarteiraRequest {

	@Email
	private String email;
	
	@NotNull
	private CarteirasDisponiveis carteira;

	public AssociaCarteiraRequest(@NotBlank @Email String email, CarteirasDisponiveis carteira) {
		this.email = email;
		this.carteira = carteira;
	}

	public String getEmail() {
		return email;
	}

	public CarteirasDisponiveis getCarteira() {
		return carteira;
	}
	
}
