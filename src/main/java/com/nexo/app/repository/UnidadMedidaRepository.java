package com.nexo.app.repository;
import com.nexo.app.domain.Producto;
import com.nexo.app.domain.UnidadMedida;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UnidadMedida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadMedidaRepository extends JpaRepository<UnidadMedida, Long> {

	/**
	 * @param id
	 * @return la unidad de medida
	 */
	@Query("select u from UnidadMedida u where u.id=?1")
	Optional<UnidadMedida> getById(Long id);
}
