package com.asercao.repository;

import com.asercao.domain.Affaire;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Affaire entity.
 */
public interface AffaireRepository extends JpaRepository<Affaire,Long> {

}
