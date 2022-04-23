package ar.com.indumet.workload.services;

import ar.com.indumet.workload.commons.DocumentSource;
import ar.com.indumet.workload.exceptions.DocumentFormatException;
import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.entities.DocumentEntity;
import ar.com.indumet.workload.models.entities.JobEntity;
import ar.com.indumet.workload.models.entities.JobOrderEntity;
import ar.com.indumet.workload.models.entities.PurchaseEntity;
import ar.com.indumet.workload.models.repositories.DocumentsRepository;
import ar.com.indumet.workload.models.repositories.JobOrdersRepository;
import ar.com.indumet.workload.models.repositories.JobsRepository;
import ar.com.indumet.workload.models.repositories.PurchasesRepository;
import ar.com.indumet.workload.models.vos.DocumentVO;
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
public class DocumentsService {

    @Autowired
    private DocumentsRepository documentsRepository;

    @Autowired
    private JobsRepository jobsRepository;

    @Autowired
    private JobOrdersRepository jobOrdersRepository;

    @Autowired
    private PurchasesRepository purchasesRepository;

    @Autowired
    private FilesService filesService;

    public DocumentVO save(DocumentVO documentVO, Long id, DocumentSource source) throws SaveErrorException, IOException, NotFoundException, DocumentFormatException {

        DocumentEntity documentEntity;

        if(source.equals(DocumentSource.JOB)){
            Optional<JobEntity> jobEntity = jobsRepository.findById(id);

            if(jobEntity.isPresent()){
                documentVO = create(documentVO);
                jobEntity.get().getDocuments().add(documentVO.toEntity());
                jobsRepository.save(jobEntity.get());
            } else {
                throw new NotFoundException("Job not found");
            }
        }

        if(source.equals(DocumentSource.JOB_ORDER)){
            Optional<JobOrderEntity> jobOrderEntity = jobOrdersRepository.findById(id);

            if(jobOrderEntity.isPresent()){
                documentVO = create(documentVO);
                jobOrderEntity.get().getDocuments().add(documentVO.toEntity());
                jobOrdersRepository.save(jobOrderEntity.get());
            } else {
                throw new NotFoundException("Job Order not found");
            }
        }

        if(source.equals(DocumentSource.PURCHASE)){
            Optional<PurchaseEntity> purchaseEntity = purchasesRepository.findById(id);

            if(purchaseEntity.isPresent()){
                documentVO = create(documentVO);
                purchaseEntity.get().getDocuments().add(documentVO.toEntity());
                purchasesRepository.save(purchaseEntity.get());
            } else {
                throw new NotFoundException("Job Order not found");
            }
        }

        return documentVO;
    }

    private DocumentVO create(DocumentVO documentVO) throws IOException, DocumentFormatException, SaveErrorException {
        List<String> validFileFormats = Arrays.asList("application/pdf");

        DocumentEntity documentEntity;

        documentVO.setSource(filesService.uploadDocument(documentVO.getSource(), validFileFormats));
        documentEntity = documentVO.toEntity();
        documentEntity = documentsRepository.save(documentEntity);
        if (Objects.nonNull(documentEntity)) {
            documentVO.setId(documentEntity.getId());
        } else
            throw new SaveErrorException("An error happened when the register was saved");

        return documentVO;
    }

    public DocumentVO delete(Long id) throws NotFoundException {
        Optional<DocumentEntity> documentEntity = documentsRepository.findById(id);
        if (documentEntity.isPresent()) {
            documentEntity.get().setDeleted(Timestamp.from(Instant.now()));
            documentsRepository.save(documentEntity.get());
            return new DocumentVO(documentEntity.get());
        } else
            throw new NotFoundException("Register not found.");
    }

}