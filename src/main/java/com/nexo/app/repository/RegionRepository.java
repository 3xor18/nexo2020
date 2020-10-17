package com.nexo.app.repository;
import com.nexo.app.domain.Pais;
import com.nexo.app.domain.Producto;
import com.nexo.app.domain.Region;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Region entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
	
	/**
	 * @param pais buscado
	 * @return un listado de regiones del pais
	 */
	@Query("select r from Region r where r.pais=?1")
	Optional<List<Region>> getByPais(Pais pais);

}
