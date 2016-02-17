package com.asercao.repository;

import com.asercao.domain.Distance;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Distance entity.
 */
public interface DistanceRepository extends JpaRepository<Distance,Long> {

}
