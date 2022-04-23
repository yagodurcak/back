package ar.com.indumet.workload.models.repositories;

import ar.com.indumet.workload.models.entities.ProviderEntity;
import ar.com.indumet.workload.models.entities.SectorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectorsRepository extends PagingAndSortingRepository<SectorEntity, Long> {
    Page<SectorEntity> findAll(Pageable pageable);
    Optional<SectorEntity> findById(Long id);
    SectorEntity save(SectorEntity sectorEntity);
    void deleteById(Long id);
}