package com.itau.proposta.avisoViagem;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AvisoViagemRequest {
	
	@NotBlank
	private String destinoViagem;
	
	@NotNull
	@Future
	private LocalDate dataTerminoViagem;

	public String getDestinoViagem() {
		return destinoViagem;
	}

	public LocalDate getDataTerminoViagem() {
		return dataTerminoViagem;
	}
	
}
