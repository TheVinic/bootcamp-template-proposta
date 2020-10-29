package com.itau.proposta.biometria;

import javax.validation.constraints.NotBlank;

public class BiometriaRequest {

	@NotBlank
	private String digital;

	public String getDigital() {
		return digital;
	}
	
}
