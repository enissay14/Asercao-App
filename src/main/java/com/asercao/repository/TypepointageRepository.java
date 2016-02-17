package com.asercao.repository;

import com.asercao.domain.Typepointage;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Typepointage entity.
 */
public interface TypepointageRepository extends JpaRepository<Typepointage,Long> {

}
