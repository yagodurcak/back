package ar.com.indumet.workload.services;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.entities.PictureEntity;
import ar.com.indumet.workload.models.repositories.PicturesRepository;
import ar.com.indumet.workload.models.vos.PictureVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PicturesService {

    @Autowired
    private PicturesRepository picturesRepository;

    @Autowired
    private FilesService filesService;

    public PictureVO save(String source, Long jobId) throws SaveErrorException, IOException  {

        PictureEntity pictureEntity;

        List<String> validFileFormats = Arrays.asList("image/jpeg", "image/png", "application/pdf");

        PictureVO pictureVO = filesService.uploadPicture(source, validFileFormats);

        pictureEntity = pictureVO.toEntity(jobId);
        pictureEntity = picturesRepository.save(pictureEntity);


        if (Objects.nonNull(pictureEntity)) {
            pictureVO.setId(pictureEntity.getId());
        } else
            throw new SaveErrorException("An error happened when the register was saved");

        return pictureVO;

    }

    public PictureVO delete(Long id) throws NotFoundException {
        Optional<PictureEntity> pictureEntity = picturesRepository.findById(id);
        if (pictureEntity.isPresent()) {
            pictureEntity.get().setDeleted(Timestamp.from(Instant.now()));
            picturesRepository.save(pictureEntity.get());
            return new PictureVO(pictureEntity.get());
        } else
            throw new NotFoundException("Register not found.");
    }

    public List<PictureVO> main(Long id, Long pictureId) throws NotFoundException {

        List<PictureEntity> picturesByJob = picturesRepository.findByJobId(id);

        if(picturesByJob.size() == 0 || !picturesByJob.stream().anyMatch(picture -> picture.getId().equals(pictureId)))
            throw new NotFoundException("Register not found");

        for (PictureEntity pictureEntity : picturesByJob){
            if(pictureEntity.getId().equals(pictureId))
                pictureEntity.setMain(true);
            else
                pictureEntity.setMain(false);
        }

        picturesRepository.saveAll(picturesByJob);

        return picturesByJob.stream().map(PictureVO::new).sorted(Comparator.comparing(PictureVO::getMain).reversed()).collect(Collectors.toList());
    }
}