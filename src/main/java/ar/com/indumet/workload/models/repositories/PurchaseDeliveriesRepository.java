package ar.com.indumet.workload.models.repositories;

import ar.com.indumet.workload.models.entities.PurchaseDeliveryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PurchaseDeliveriesRepository extends PagingAndSortingRepository<PurchaseDeliveryEntity, Long> {
    List<PurchaseDeliveryEntity> findByPurchaseId(Long purchaseId);
    PurchaseDeliveryEntity save(PurchaseDeliveryEntity puechaseDeliveryEntity);
    void deleteById(Long id);
}