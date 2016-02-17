package com.asercao.repository;

import com.asercao.domain.Montantcorrige;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Montantcorrige entity.
 */
public interface MontantcorrigeRepository extends JpaRepository<Montantcorrige,Long> {

    List<Montantcorrige> findByAffaireId(Long id);

}
