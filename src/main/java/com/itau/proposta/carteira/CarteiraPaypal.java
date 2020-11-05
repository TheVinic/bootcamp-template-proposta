package com.itau.proposta.carteira;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.itau.proposta.cartao.Cartao;

@Entity
public class CarteiraPaypal {

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
	
	@OneToOne
	@NotNull
	private Cartao cartao;
	
	@NotBlank
	@Email
	private String email;

	public CarteiraPaypal(Cartao cartao, String email) {
		this.cartao = cartao;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public Cartao getCartao() {
		return cartao;
	}

	public String getEmail() {
		return email;
	}
	
}
