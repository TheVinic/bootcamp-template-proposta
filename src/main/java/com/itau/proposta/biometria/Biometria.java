package com.itau.proposta.biometria;

import java.time.LocalDateTime;
import java.util.Base64;

import javax.persistence.Embeddable;

@Embeddable
public class Biometria {

	private byte[] digital;
	private LocalDateTime instante = LocalDateTime.now();
	
	public Biometria(String digital) {
		this.digital = Base64.getEncoder().encode(digital.getBytes());
	}

	public byte[] getDigital() {
		return digital;
	}
	
	public LocalDateTime getInstante() {
		return instante;
	}
	
}
