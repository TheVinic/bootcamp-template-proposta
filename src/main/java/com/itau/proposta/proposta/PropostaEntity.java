package com.itau.proposta.proposta;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.http.HttpStatus;

import com.itau.proposta.exception.ApiErroException;
import com.itau.proposta.validator.CpfCnpj;

@Entity
@Table(name = "proposta")
public class PropostaEntity {

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
	
	@NotBlank
	@CpfCnpj
	private String cpfCnpj;
	
	@Email
	@NotBlank
	private String email;

	@NotBlank
	private String nome;

	@NotBlank
	private String endereco;

	@NotNull
	@Positive
	private BigDecimal salario;
	
	@NotNull
	private StatusAvaliacaoProposta statusAvaliacao;

	public PropostaEntity() {
		super();
	}
	
	public PropostaEntity(@NotBlank String cpfCnpj, @Email @NotBlank String email, @NotBlank String nome,
			@NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
		this.cpfCnpj = cpfCnpj;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
		this.statusAvaliacao = StatusAvaliacaoProposta.NAO_ELEGIVEL;
	}

	public String getId() {
		return id;
	}

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

	public StatusAvaliacaoProposta getStatusAvaliacao() {
		return statusAvaliacao;
	}

	public void setStatusAvaliacao(StatusAvaliacaoProposta statusAvaliacao) {
		if(this.statusAvaliacao.equals(StatusAvaliacaoProposta.ELEGIVEL)) {
			throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "Proposta já se encontra como elegível, não é possível voltar o status.");
		}
		this.statusAvaliacao = statusAvaliacao;
	}

}
