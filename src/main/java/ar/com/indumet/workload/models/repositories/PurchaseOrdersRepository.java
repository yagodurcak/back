package ar.com.indumet.workload.models.repositories;

import ar.com.indumet.workload.models.entities.PurchaseOrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PurchaseOrdersRepository extends PagingAndSortingRepository<PurchaseOrderEntity, Long> {
    Optional<PurchaseOrderEntity> findById(Long id);
    PurchaseOrderEntity save(PurchaseOrderEntity purchaseOrderEntity);
}