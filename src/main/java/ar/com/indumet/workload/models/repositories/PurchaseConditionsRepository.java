package ar.com.indumet.workload.models.repositories;

import ar.com.indumet.workload.models.entities.PurchaseConditionEntity;
import ar.com.indumet.workload.models.entities.PurchaseConditionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseConditionsRepository extends PagingAndSortingRepository<PurchaseConditionEntity, Long> {
    Page<PurchaseConditionEntity> findAll(Pageable pageable);
    Optional<PurchaseConditionEntity> findById(Long id);
    PurchaseConditionEntity save(PurchaseConditionEntity kindEntity);
    void deleteById(Long id);
}