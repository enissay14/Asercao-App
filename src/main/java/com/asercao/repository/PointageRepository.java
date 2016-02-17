package com.asercao.repository;

import com.asercao.domain.Pointage;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pointage entity.
 */
public interface PointageRepository extends JpaRepository<Pointage,Long> {

    List<Pointage> findBySalarieId(Long id);

}
