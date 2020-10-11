package com.nexo.app.repository;
import com.nexo.app.domain.Comuna;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Comuna entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long> {

}
