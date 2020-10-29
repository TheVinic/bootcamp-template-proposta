package com.itau.proposta.cartao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.CreditCardNumber;

import com.itau.proposta.proposta.PropostaEntity;

@Entity
public class Cartao {

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
	
	@OneToOne
	private PropostaEntity proposta;
	
	@NotBlank
	private String numero;

	@Deprecated
	public Cartao() {}
	
	public Cartao(PropostaEntity proposta, @NotBlank @CreditCardNumber String numero) {
		super();
		this.proposta = proposta;
		this.numero = numero;
	}

	public String getId() {
		return id;
	}

	public PropostaEntity getProposta() {
		return proposta;
	}

	public String getNumero() {
		return numero;
	}
	
}
