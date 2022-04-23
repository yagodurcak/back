package ar.com.indumet.workload.models.repositories;

import ar.com.indumet.workload.models.entities.PurchaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchasesRepository extends PagingAndSortingRepository<PurchaseEntity, Long> {
    Page<PurchaseEntity> findAll(Pageable pageable);
    Page<PurchaseEntity> findByCurrentStateIn(List<String> state, Pageable pageable);

    @Query(value = "" +
            "SELECT * FROM purchases AS purchases " +
            "LEFT JOIN requisitions AS requisition ON requisition.purchase_id = purchases.id " +
            "LEFT JOIN budgets AS budget ON budget.purchase_id = purchases.id " +
            "LEFT JOIN providers AS provider ON provider.id = budget.provider_id " +
            "LEFT JOIN purchase_orders AS purchase_order ON purchase_order.id = budget.purchase_order_id " +
            "WHERE " +
            "   purchases.current_state IN :states AND " +
            "       (" +
            "           UPPER(purchases.id) LIKE CONCAT('%',UPPER(:query),'%') OR " +
            "           UPPER(purchases.buyer) LIKE CONCAT('%',UPPER(:query),'%') OR " +
            "           UPPER(purchases.bill_number) LIKE CONCAT('%',UPPER(:query),'%') OR " +
            "           UPPER(requisition.description) LIKE CONCAT('%',UPPER(:query),'%') OR " +
            "           UPPER(provider.name) LIKE CONCAT('%',UPPER(:query),'%') OR " +
            "           UPPER(purchase_order.id) LIKE CONCAT('%',UPPER(:query),'%')" +
            "       ) " +
            "GROUP BY purchases.id"
            , nativeQuery = true)
    Page<PurchaseEntity> search(@Param("query")String query, @Param("states")List<String> states, Pageable pageable);
    Optional<PurchaseEntity> findById(Long id);
    PurchaseEntity save(PurchaseEntity purchaseEntity);
    void deleteById(Long id);
}