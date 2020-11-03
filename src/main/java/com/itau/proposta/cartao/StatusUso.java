package com.itau.proposta.cartao;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class StatusUso {
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
	
	private @NotNull PossiveisStatusUso statusSolicitado;
	
	@ManyToOne
	private @Valid @NotNull Cartao cartao;
	
	private @NotBlank String userAgent;
	private @NotBlank String ipRemoto;
	private LocalDateTime instante;

	public StatusUso(PossiveisStatusUso status, Cartao cartao, String userAgent, String ipRemoto) {
		this.statusSolicitado = status;
		this.cartao = cartao;
		this.userAgent = userAgent;
		this.ipRemoto = ipRemoto;
		this.instante = LocalDateTime.now();
	}

	public String getId() {
		return id;
	}

	public PossiveisStatusUso getStatusSolicitado() {
		return statusSolicitado;
	}

	public Cartao getCartao() {
		return cartao;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public String getIpRemoto() {
		return ipRemoto;
	}

	public LocalDateTime getInstante() {
		return instante;
	}
	
}
