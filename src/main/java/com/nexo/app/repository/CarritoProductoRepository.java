package com.nexo.app.repository;
import com.nexo.app.domain.CarritoProducto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CarritoProducto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarritoProductoRepository extends JpaRepository<CarritoProducto, Long> {

}
