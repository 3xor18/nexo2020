package com.nexo.app.repository;
import com.nexo.app.domain.Carrito;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Carrito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

}
