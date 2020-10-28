package com.itau.proposta.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;


public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, Object>{

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		CPFValidator cpfValidator = new CPFValidator();
		cpfValidator.initialize(null);
		
		CNPJValidator cnpjValidator = new CNPJValidator();
		
		cnpjValidator.initialize(null);
		
		return (cpfValidator.isValid(value.toString(), null) || cnpjValidator.isValid(value.toString(), null));
	}

}
