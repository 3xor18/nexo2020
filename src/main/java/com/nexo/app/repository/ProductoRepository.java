package com.nexo.app.repository;

import com.nexo.app.domain.Persona;
import com.nexo.app.domain.Producto;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Producto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

	/**
	 * @param pageable
	 * @return pageable de los productos del usuario Actual
	 */
	@Query("select p from Producto p where p.vendedor.user.login=?#{principal.username}")
	Optional<Page<Producto>> getAllMyProducts(Pageable pageable);
	
	/**
	 * busca pro id
	 */
	@Query("select p from Producto p where p.id=?1")
	Optional<Producto> findById(Long idProducto);
}
