package ar.com.indumet.workload.models.repositories;

import ar.com.indumet.workload.models.entities.WorkRecordEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkRecordsRepository extends PagingAndSortingRepository<WorkRecordEntity, Long> {
    Page<WorkRecordEntity> findAll(Pageable pageable);
    Page<WorkRecordEntity> findByWorkerId(Pageable pageable, Long workerId);
    Page<WorkRecordEntity> findByJobOrderId(Pageable pageable, Long jobOrderId);
    Optional<WorkRecordEntity> findById(Long id);
    WorkRecordEntity save(WorkRecordEntity workRecordEntity);
    List<WorkRecordEntity> findByCreatedBetween(Timestamp startDate, Timestamp endDate);
    void deleteById(Long id);
}