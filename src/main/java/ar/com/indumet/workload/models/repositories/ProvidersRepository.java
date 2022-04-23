package ar.com.indumet.workload.models.repositories;

import ar.com.indumet.workload.models.entities.ProviderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProvidersRepository extends PagingAndSortingRepository<ProviderEntity, Long> {
    Page<ProviderEntity> findAll(Pageable pageable);
    Optional<ProviderEntity> findById(Long id);
    ProviderEntity save(ProviderEntity providerEntity);
    void deleteById(Long id);
}