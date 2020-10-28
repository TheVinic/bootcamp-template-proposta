package com.itau.proposta.dadosSolicitante;

import com.itau.proposta.proposta.PropostaEntity;
import com.itau.proposta.validator.CpfCnpj;

public class DadosFinanceirosRequest {

	private String idProposta;
	
	@CpfCnpj
	private String documento;
	
	private String nome;
	
	public DadosFinanceirosRequest(PropostaEntity proposta) {
		super();
		this.idProposta = proposta.getId();
		this.documento = proposta.getCpfCnpj();
		this.nome = proposta.getNome();
	}
	
	@Deprecated
	public DadosFinanceirosRequest() {}

	public String getIdProposta() {
		return idProposta;
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}
	
}
