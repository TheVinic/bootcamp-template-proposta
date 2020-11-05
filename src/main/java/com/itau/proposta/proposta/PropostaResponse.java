package com.itau.proposta.proposta;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PropostaResponse {

	@NotBlank
	private String id;
	
	@NotNull
	private StatusAvaliacaoProposta statusAvaliacao;

	private String idCartao;
	
	public PropostaResponse(String id, @NotNull StatusAvaliacaoProposta statusAvaliacao, String idCartao) {
		this.id = id;
		this.statusAvaliacao = statusAvaliacao;
		this.idCartao = idCartao;
	}

	public PropostaResponse(String id, @NotNull StatusAvaliacaoProposta statusAvaliacao) {

		this.id = id;
		this.statusAvaliacao = statusAvaliacao;
		this.idCartao = null;
	}

	public String getId() {
		return id;
	}

	public StatusAvaliacaoProposta getStatusAvaliacao() {
		return statusAvaliacao;
	}

	public String getIdCartao() {
		return idCartao;
	}
	
}
