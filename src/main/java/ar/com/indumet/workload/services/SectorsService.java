package ar.com.indumet.workload.services;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.entities.SectorEntity;
import ar.com.indumet.workload.models.repositories.SectorsRepository;
import ar.com.indumet.workload.models.vos.SectorVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class SectorsService {

    @Autowired
    private SectorsRepository sectorsRepository;

    public Page<SectorVO> findAll(Integer pageNumber, Integer pageSize, String orderField, String orderCriteria) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.fromString(orderCriteria), orderField);

        Page<SectorEntity> sectorEntities;
        sectorEntities = sectorsRepository.findAll(pageable);

        return sectorEntities.map(SectorVO::new);
    }

    public SectorVO find(Long id) throws NotFoundException {
        Optional<SectorEntity> sectorEntity = sectorsRepository.findById(id);
        if(sectorEntity.isPresent())
            return new SectorVO(sectorEntity.get());
        else
            throw new NotFoundException("Register not found.");
    }

    public SectorVO save(SectorVO sectorVO) throws SaveErrorException {
        SectorEntity sectorEntity = sectorVO.toEntity();

        sectorEntity = sectorsRepository.save(sectorEntity);
        if (Objects.nonNull(sectorEntity)) {  sectorVO.setId(sectorEntity.getId());
        } else
            throw new SaveErrorException("An error happened when the register was saved");

        return sectorVO;

    }

    public SectorVO delete(Long id) throws NotFoundException {
        Optional<SectorEntity> sectorEntity = sectorsRepository.findById(id);
        if (sectorEntity.isPresent()) {
            sectorEntity.get().setDeleted(Timestamp.from(Instant.now()));
            sectorsRepository.save(sectorEntity.get());
            return new SectorVO(sectorEntity.get());
        } else
            throw new NotFoundException("Register not found.");
    }
}