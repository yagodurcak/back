package ar.com.indumet.workload.models.repositories;

import ar.com.indumet.workload.models.entities.JobOrderEntity;
import ar.com.indumet.workload.models.entities.JobOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobOrdersRepository extends PagingAndSortingRepository<JobOrderEntity , Long>, JpaSpecificationExecutor<JobOrderEntity> {
    Page<JobOrderEntity> findAll(Pageable pageable);
    Page<JobOrderEntity> findByStateNameIn(List<String> state, Pageable pageable);
    Optional<JobOrderEntity> findById(Long id);
    List<JobOrderEntity> findByJobId(Long jobId);
    JobOrderEntity save(JobOrderEntity jobOrderEntity);
    void deleteById(Long id);
}