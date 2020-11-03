package com.itau.proposta.recuperaSenha;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.itau.proposta.cartao.Cartao;

@Entity
public class RecuperaSenha {

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
	
	@ManyToOne
	private @NotNull @Valid Cartao cartao;
	
	private @NotBlank String ipRemoto;
	
	private @NotBlank String userAgent;
	
	private LocalDateTime instante;

	public RecuperaSenha(Cartao cartao, String userAgent, String ipRemoto) {
		this.cartao = cartao;
		this.ipRemoto = ipRemoto;
		this.userAgent = userAgent;
		this.instante = LocalDateTime.now();
	}

	public String getId() {
		return id;
	}

	public Cartao getCartao() {
		return cartao;
	}

	public LocalDateTime getInstante() {
		return instante;
	}

	public String getIpRemoto() {
		return ipRemoto;
	}

	public String getUserAgent() {
		return userAgent;
	}
	
}
