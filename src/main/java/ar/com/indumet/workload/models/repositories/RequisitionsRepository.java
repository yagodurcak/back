package ar.com.indumet.workload.models.repositories;

import ar.com.indumet.workload.models.entities.RequisitionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RequisitionsRepository extends PagingAndSortingRepository<RequisitionEntity, Long> {
    Set<RequisitionEntity> save(Set<RequisitionEntity> requisitions);
    Page<RequisitionEntity> findAll(Pageable pageable);
    Page<RequisitionEntity> findByPurchaseId(Integer purchaseId, Pageable pageable);

//    @Query(value = "SELECT requisitions.* FROM requisitions requisitions INNER JOIN purchases ON purchases.id = requisitions.purchase_id LEFT JOIN budgets ON purchases.id = budgets.purchase_id WHERE budgets.id IS NULL GROUP BY requisitions.id", nativeQuery = true)
//    Page<RequisitionEntity> findSateCotizando(Pageable pageable);

//    @Query(value = "SELECT requisitions.* FROM requisitions requisitions INNER JOIN purchases ON purchases.id = requisitions.purchase_id LEFT JOIN budgets ON purchases.id = budgets.purchase_id WHERE budgets.id IS NOT NULL AND purchases.current_state = 'Iniciado' GROUP BY requisitions.id", nativeQuery = true)
//    Page<RequisitionEntity> findSateCotizado(Pageable pageable);

    @Query(value = "SELECT requisitions.* FROM requisitions requisitions INNER JOIN purchases ON purchases.id = requisitions.purchase_id WHERE requisitions.deleted IS NULL AND purchases.current_state = :state GROUP BY requisitions.id", nativeQuery = true)
    Page<RequisitionEntity> findPurchaseSate(@Param("state") String state, Pageable pageable);

    @Query(value = "SELECT requisitions.* FROM requisitions requisitions LEFT JOIN purchases ON purchases.id = requisitions.purchase_id WHERE requisitions.deleted IS NULL AND purchases.current_state != 'Anulado' OR purchases.id IS NULL GROUP BY requisitions.id", nativeQuery = true)
    Page<RequisitionEntity> findAllSate(Pageable pageable);

    @Query(value = "SELECT * FROM requisitions WHERE deleted IS NOT NULL  GROUP BY id", nativeQuery = true)
    Page<RequisitionEntity> findRemoved(Pageable pageable);

    Optional<RequisitionEntity> findById(Long id);
    RequisitionEntity save(RequisitionEntity requisitionEntity);
    void deleteById(Long id);
}