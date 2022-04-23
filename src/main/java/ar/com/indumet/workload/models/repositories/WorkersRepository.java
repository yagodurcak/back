package ar.com.indumet.workload.models.repositories;

import ar.com.indumet.workload.models.entities.WorkerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkersRepository extends PagingAndSortingRepository<WorkerEntity, Long> {
    Page<WorkerEntity> findAll(Pageable pageable);
    Optional<WorkerEntity> findById(Long id);
    WorkerEntity save(WorkerEntity kindEntity);
    void deleteById(Long id);
}