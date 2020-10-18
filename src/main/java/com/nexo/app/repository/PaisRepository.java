package com.nexo.app.repository;
import com.nexo.app.domain.Pais;
import com.nexo.app.domain.Producto;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pais entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {

	/**
	 * @param id del pais
	 * @return pais
	 */
	@Query("select p from Pais p where p.id=?1")
	Optional<Pais> getById(Long id);
}
