package com.nexo.app.repository;

import com.nexo.app.domain.Persona;
import com.nexo.app.domain.Producto;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Persona entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

	/**
	 * @return los datos del usuario actual
	 */
	@Query("select p from Persona p where p.user.login= ?#{principal.username}")
	Optional<Persona> getUserActual();
}
