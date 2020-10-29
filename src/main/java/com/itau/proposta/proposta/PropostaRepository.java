package com.itau.proposta.proposta;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropostaRepository extends CrudRepository<PropostaEntity, Long>{

	//@Query("select p from proposta p left join p.cartao c where p.statusAvaliacao = :status and c.id is null")
	@Query("select p from PropostaEntity p left join p.cartao c where p.statusAvaliacao = :status and c.id is null")
	List<PropostaEntity> todasSemCartao(@Param("status") StatusAvaliacaoProposta status); 
	
}
