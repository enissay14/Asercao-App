package com.asercao.repository;

import com.asercao.domain.Bonfacture;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Bonfacture entity.
 */
public interface BonfactureRepository extends JpaRepository<Bonfacture,Long> {

    List<Bonfacture> findByAffaireId(Long id);

}
