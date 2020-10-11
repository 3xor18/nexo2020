package com.nexo.app.repository;
import com.nexo.app.domain.Producto;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Producto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
	
	/**
	 * @param pageable
	 * @return pageable de los productos del usuario Actual
	 */
	 @Query("select p from Producto p where p.vendedor.user=?1 ORDER BY p.id DESC")
	Optional<Page<Producto>> getAllMyProducts(Long idUsuario,Pageable pageable);

}
