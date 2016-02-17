package com.asercao.repository;

import com.asercao.domain.Typeachat;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Typeachat entity.
 */
public interface TypeachatRepository extends JpaRepository<Typeachat,Long> {

}
