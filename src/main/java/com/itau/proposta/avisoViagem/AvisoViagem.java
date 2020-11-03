package com.itau.proposta.avisoViagem;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.itau.proposta.cartao.Cartao;

@Entity
public class AvisoViagem {

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
	
	@NotNull
	@ManyToOne
	private Cartao cartao;
	
	@NotBlank
	private String destinoViagem;
	
	@Future
	private LocalDate dataTerminoViagem;
	
	@NotNull
	private LocalDateTime instanteCriacao;

	@NotBlank
	private String remoteAddr;

	@NotBlank
	private String userAgent;
	
	public AvisoViagem() {
		super();
	}

	public AvisoViagem(Cartao cartao, String destinoViagem, LocalDate dataTerminoViagem, String userAgent,
			String remoteAddr) {
		this.cartao = cartao;
		this.destinoViagem = destinoViagem;
		this.dataTerminoViagem = dataTerminoViagem;
		this.remoteAddr = remoteAddr;
		this.userAgent = userAgent;
		this.instanteCriacao = LocalDateTime.now();
	}

	public String getId() {
		return id;
	}

	public Cartao getCartao() {
		return cartao;
	}

	public String getDestinoViagem() {
		return destinoViagem;
	}

	public LocalDate getDataTerminoViagem() {
		return dataTerminoViagem;
	}

	public LocalDateTime getInstanteCriacao() {
		return instanteCriacao;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public String getUserAgent() {
		return userAgent;
	}
	
}
