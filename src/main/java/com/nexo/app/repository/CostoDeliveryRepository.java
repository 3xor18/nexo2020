package com.nexo.app.repository;
import com.nexo.app.domain.CostoDelivery;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CostoDelivery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CostoDeliveryRepository extends JpaRepository<CostoDelivery, Long> {

}
