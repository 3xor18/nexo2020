package com.nexo.app.repository;
import com.nexo.app.domain.ProductoImagenes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductoImagenes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoImagenesRepository extends JpaRepository<ProductoImagenes, Long> {

}
