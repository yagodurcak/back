package ar.com.indumet.workload.models.repositories;

import ar.com.indumet.workload.models.entities.ComponentEntity;
import ar.com.indumet.workload.models.entities.JobEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComponentsRepository extends CrudRepository<ComponentEntity, Long> {
}