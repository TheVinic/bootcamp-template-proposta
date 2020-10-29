package com.itau.proposta.proposta;

import javax.validation.constraints.NotNull;

public class PropostaResponse {

	private String id;
	
	private StatusAvaliacaoProposta statusAvaliacao;

	public PropostaResponse(String id, @NotNull StatusAvaliacaoProposta statusAvaliacao) {
		this.id = id;
		this.statusAvaliacao = statusAvaliacao;
	}

	public String getId() {
		return id;
	}

	public StatusAvaliacaoProposta getStatusAvaliacao() {
		return statusAvaliacao;
	}
	
}
