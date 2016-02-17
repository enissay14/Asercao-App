package com.asercao.repository;

import com.asercao.domain.Typeaffaire;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Typeaffaire entity.
 */
public interface TypeaffaireRepository extends JpaRepository<Typeaffaire,Long> {

}
