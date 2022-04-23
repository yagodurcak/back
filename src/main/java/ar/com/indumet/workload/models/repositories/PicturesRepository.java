package ar.com.indumet.workload.models.repositories;

import ar.com.indumet.workload.models.entities.PictureEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PicturesRepository extends PagingAndSortingRepository<PictureEntity, Long> {
    List<PictureEntity> findByJobId(Long jobId);
    PictureEntity save(PictureEntity pictureEntity);
    void deleteById(Long id);
}