package ar.com.indumet.workload.models.repositories;

import ar.com.indumet.workload.models.entities.KindEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface KindsRepository extends PagingAndSortingRepository<KindEntity, Long> {
    Page<KindEntity> findAll(Pageable pageable);
    Page<KindEntity> findByContext(String context, Pageable pageable);
    Optional<KindEntity> findById(Long id);
    KindEntity save(KindEntity kindEntity);
    void deleteById(Long id);
}