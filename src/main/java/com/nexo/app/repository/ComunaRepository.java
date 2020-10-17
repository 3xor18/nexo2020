package com.nexo.app.repository;
import com.nexo.app.domain.Comuna;
import com.nexo.app.domain.Pais;
import com.nexo.app.domain.Region;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Comuna entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long> {

	/**
	 * @param region
	 * @return las comunas de la region
	 */
	@Query("select c from Comuna c where c.region=?1")
	Optional<List<Comuna>> getByRegion(Region region);
}
