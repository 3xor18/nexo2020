package com.nexo.app.repository;
import com.nexo.app.domain.UnidadMedida;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UnidadMedida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadMedidaRepository extends JpaRepository<UnidadMedida, Long> {

}
