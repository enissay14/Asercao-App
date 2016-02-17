package com.asercao.repository;

import com.asercao.domain.Commande;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Commande entity.
 */
public interface CommandeRepository extends JpaRepository<Commande,Long> {

    List<Commande> findByAffaireId(Long id);

}
