package com.itau.proposta.cartao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.http.HttpStatus;

import com.itau.proposta.biometria.Biometria;
import com.itau.proposta.carteira.CarteiraPaypal;
import com.itau.proposta.carteira.CarteiraSamsung;
import com.itau.proposta.exception.ApiErroException;
import com.itau.proposta.proposta.PropostaEntity;

@Entity
public class Cartao {

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
	
	@OneToOne
	private PropostaEntity proposta;
	
	@NotBlank
	private String numero;
	
	@ElementCollection
	private Set<Biometria> biometrias = new HashSet<>();
	@OneToMany(mappedBy = "cartao")
	private List<StatusUso> statusUsos = new ArrayList<>();
	@OneToOne
	private CarteiraPaypal carteiraPaypal;
	@OneToOne
	private CarteiraSamsung carteiraSamsung;

	@Deprecated
	public Cartao() {}
	
	public Cartao(PropostaEntity proposta, @NotBlank @CreditCardNumber String numero) {
		super();
		this.proposta = proposta;
		this.numero = numero;
	}

	public String getId() {
		return id;
	}

	public PropostaEntity getProposta() {
		return proposta;
	}

	public String getNumero() {
		return numero;
	}

	public void adicionaBiometria(String digital) {
		
		this.biometrias.add(new Biometria(digital));
		
	}

	public void bloqueia(String userAgent, String ipRemoto) {
		this.statusUsos.add(new StatusUso(PossiveisStatusUso.BLOQUEADO, this, userAgent, ipRemoto));
	}

	public void validaCarteiraPaypal() {
		if(this.carteiraPaypal != null) {
			throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "Cartão já possui carteira associada.");
		}
	}

	public Cartao associaPaypal(String email) {
		this.carteiraPaypal = new CarteiraPaypal(this, email);
		return this;
	}

	public Cartao associaSamguns(String email) {
		this.carteiraSamsung = new CarteiraSamsung(this, email);
		return this;
	}

	public Set<Biometria> getBiometrias() {
		return biometrias;
	}

	public List<StatusUso> getStatusUsos() {
		return statusUsos;
	}

	public CarteiraPaypal getCarteiraPaypal() {
		return carteiraPaypal;
	}
	
}
