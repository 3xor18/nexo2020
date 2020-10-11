package com.nexo.app.repository;
import com.nexo.app.domain.Persona;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Persona entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    @Query("select persona from Persona persona where persona.user.login = ?#{principal.username}")
    List<Persona> findByUserIsCurrentUser();

}
