package com.asercao.repository;

import com.asercao.domain.Statussalaire;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Statussalaire entity.
 */
public interface StatussalaireRepository extends JpaRepository<Statussalaire,Long> {

}
