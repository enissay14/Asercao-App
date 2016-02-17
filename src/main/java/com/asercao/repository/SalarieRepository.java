package com.asercao.repository;

import com.asercao.domain.Salarie;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Salarie entity.
 */
public interface SalarieRepository extends JpaRepository<Salarie,Long> {

}
