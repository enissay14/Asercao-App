package com.asercao.repository;

import com.asercao.domain.Statusaffaire;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Statusaffaire entity.
 */
public interface StatusaffaireRepository extends JpaRepository<Statusaffaire,Long> {

}
