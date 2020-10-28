package com.itau.proposta.dadosSolicitante;

public class DadosFinanceirosResponse {

	private String documento;
	
	private String nome;
	
	private String idProposta;
	
	private RespostaStatusAvaliacao resultadoSolicitacao;

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public String getIdProposta() {
		return idProposta;
	}

	public RespostaStatusAvaliacao getresultadoSolicitacao() {
		return resultadoSolicitacao;
	}
	
}
