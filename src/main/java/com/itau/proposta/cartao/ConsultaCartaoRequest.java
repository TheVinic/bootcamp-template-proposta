package com.itau.proposta.cartao;

public class ConsultaCartaoRequest {

	private String idProposta;

	public ConsultaCartaoRequest(String idProposta) {
		super();
		this.idProposta = idProposta;
	}

	public String getIdProposta() {
		return idProposta;
	}
	
}
