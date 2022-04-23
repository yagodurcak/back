package ar.com.indumet.workload.models.repositories;

import ar.com.indumet.workload.models.entities.PlaneEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PlanesRepository extends PagingAndSortingRepository<PlaneEntity, Long> {
    List<PlaneEntity> findByJobId(Long jobId);
    PlaneEntity save(PlaneEntity planeEntity);
    void deleteById(Long id);
}