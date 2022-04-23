package ar.com.indumet.workload.models.repositories;

import ar.com.indumet.workload.models.entities.DocumentEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DocumentsRepository extends PagingAndSortingRepository<DocumentEntity, Long> {
    DocumentEntity save(DocumentEntity planeEntity);
    void deleteById(Long id);
}