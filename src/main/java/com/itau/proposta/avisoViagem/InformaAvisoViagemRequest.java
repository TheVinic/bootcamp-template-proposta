package com.itau.proposta.avisoViagem;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

public class InformaAvisoViagemRequest {

	@NotBlank
	private String destino;
	
	@Future
	private LocalDate validoAte;

	public InformaAvisoViagemRequest(String destinoViagem, LocalDate dataTerminoViagem) {
		this.destino = destinoViagem;
		this.validoAte = dataTerminoViagem;
	}

	public String getDestino() {
		return destino;
	}

	public LocalDate getValidoAte() {
		return validoAte;
	}
	
}
