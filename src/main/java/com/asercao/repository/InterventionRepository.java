package com.asercao.repository;

import com.asercao.domain.Intervention;
import com.asercao.domain.Salarie;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Intervention entity.
 */
public interface InterventionRepository extends JpaRepository<Intervention,Long> {

    List<Intervention> findBySalarieId(Long id);
    List<Intervention> findByAffaireId(Long id);

}
