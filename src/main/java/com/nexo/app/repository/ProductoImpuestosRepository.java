package com.nexo.app.repository;
import com.nexo.app.domain.ProductoImpuestos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductoImpuestos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoImpuestosRepository extends JpaRepository<ProductoImpuestos, Long> {

}
