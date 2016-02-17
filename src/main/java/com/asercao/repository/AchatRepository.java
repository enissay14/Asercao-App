package com.asercao.repository;

import com.asercao.domain.Achat;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Achat entity.
 */
public interface AchatRepository extends JpaRepository<Achat,Long> {

    List<Achat> findByAffaireId(Long id);

}
