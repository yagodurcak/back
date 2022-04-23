package ar.com.indumet.workload.services;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.entities.KindEntity;
import ar.com.indumet.workload.models.repositories.KindsRepository;
import ar.com.indumet.workload.models.vos.KindVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class KindsService {

    @Autowired
    private KindsRepository kindsRepository;

    public Page<KindVO> findAll(Integer pageNumber, Integer pageSize, String orderField, String orderCriteria, String context) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.fromString(orderCriteria), orderField);

        Page<KindEntity> kindEntities;
        if(context.equals("all"))
            kindEntities = kindsRepository.findAll(pageable);
        else
            kindEntities = kindsRepository.findByContext(context, pageable);

        return kindEntities.map(KindVO::new);
    }

    public KindVO find(Long id) throws NotFoundException {
        Optional<KindEntity> kindEntity = kindsRepository.findById(id);
        if(kindEntity.isPresent())
            return new KindVO(kindEntity.get());
        else
            throw new NotFoundException("Register not found.");
    }

    public KindVO save(KindVO kindVO) throws SaveErrorException {
        KindEntity kindEntity = kindVO.toEntity();

        kindEntity = kindsRepository.save(kindEntity);
        if (Objects.nonNull(kindEntity)) {
            kindVO.setId(kindEntity.getId());
        } else
            throw new SaveErrorException("An error happened when the register was saved");

        return kindVO;

    }

    public KindVO delete(Long id) throws NotFoundException {
        Optional<KindEntity> kindEntity = kindsRepository.findById(id);
        if (kindEntity.isPresent()) {
            kindEntity.get().setDeleted(Timestamp.from(Instant.now()));
            kindsRepository.save(kindEntity.get());
            return new KindVO(kindEntity.get());
        } else
            throw new NotFoundException("Register not found.");
    }

}