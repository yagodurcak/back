package ar.com.indumet.workload.services;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.entities.PlaneEntity;
import ar.com.indumet.workload.models.repositories.PlanesRepository;
import ar.com.indumet.workload.models.vos.PlaneVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class PlanesService {

    @Autowired
    private PlanesRepository planesRepository;

    @Autowired
    private FilesService filesService;

    public PlaneVO save(PlaneVO planeVO, Long jobId) throws SaveErrorException, IOException  {

        PlaneEntity planeEntity;

        List<String> validFileFormats = Arrays.asList("image/vnd.dwg", "image/x-dwg", "application/pdf", "application/octet-stream");
        planeVO.setSource(filesService.uploadPlane(planeVO.getSource(), validFileFormats));
        planeEntity = planeVO.toEntity(jobId);
        planeEntity = planesRepository.save(planeEntity);


        if (Objects.nonNull(planeEntity)) {
            planeVO.setId(planeEntity.getId());
        } else
            throw new SaveErrorException("An error happened when the register was saved");

        return planeVO;

    }

    public PlaneVO delete(Long id) throws NotFoundException {
        Optional<PlaneEntity> planeEntity = planesRepository.findById(id);
        if (planeEntity.isPresent()) {
            planeEntity.get().setDeleted(Timestamp.from(Instant.now()));
            planesRepository.save(planeEntity.get());
            return new PlaneVO(planeEntity.get());
        } else
            throw new NotFoundException("Register not found.");
    }

}