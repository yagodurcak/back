package ar.com.indumet.workload.models.repositories;

import ar.com.indumet.workload.models.entities.JobEntity;
import ar.com.indumet.workload.models.entities.JobOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobsRepository extends PagingAndSortingRepository<JobEntity, Long> {
    Page<JobEntity> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM jobs job JOIN kinds kind ON kind.id = job.kind_id WHERE UPPER(kind.name) LIKE CONCAT('%',UPPER(:query),'%') OR UPPER(job.id) LIKE CONCAT('%',UPPER(:query),'%') OR UPPER(job.item) LIKE CONCAT('%',UPPER(:query),'%') OR UPPER(job.number) LIKE CONCAT('%',UPPER(:query),'%') OR UPPER(job.description) LIKE CONCAT('%',UPPER(:query),'%')", nativeQuery = true)
    Page<JobEntity> search(@Param("query")String query, Pageable pageable);
    List<JobEntity> findAll();
    Optional<JobEntity> findById(Long id);
    Optional<JobEntity> findByItemOrNumber(String item, String number);
    JobEntity save(JobEntity jobEntity);
    void deleteById(Long id);
}