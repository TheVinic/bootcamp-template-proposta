package com.itau.proposta.proposta;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itau.proposta.validator.CpfCnpj;
import com.itau.proposta.validator.UniqueValue;

public class PropostaRequest {

	@JsonProperty("cpf_cnpj")
	@NotBlank
	@CpfCnpj
	@UniqueValue(domainClass = PropostaEntity.class, fieldName = "cpf_cnpj")
	private String cpfCnpj;
	
	@JsonProperty("email")
	@Email
	@NotBlank
	private String email;

	@JsonProperty("nome")
	@NotBlank
	private String nome;

	@JsonProperty("endereco")
	@NotBlank
	private String endereco;

	@JsonProperty("salario")
	@NotNull
	@Positive
	private BigDecimal salario;

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public PropostaEntity toModel() {
		return new PropostaEntity(cpfCnpj, email, nome, endereco, salario);
	}
	
}
