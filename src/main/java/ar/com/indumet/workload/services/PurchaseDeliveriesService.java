package ar.com.indumet.workload.services;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.entities.PurchaseDeliveryEntity;
import ar.com.indumet.workload.models.repositories.PlanesRepository;
import ar.com.indumet.workload.models.repositories.PurchaseDeliveriesRepository;
import ar.com.indumet.workload.models.vos.PurchaseDeliveryVO;
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
public class PurchaseDeliveriesService {

    @Autowired
    private PurchaseDeliveriesRepository purchaseDeliveriesRepository;

    @Autowired
    private FilesService filesService;

    public PurchaseDeliveryVO save(PurchaseDeliveryVO purchaseDeliveryVO, Long purchaseId) throws SaveErrorException, IOException  {

        PurchaseDeliveryEntity purchaseDeliveryEntity;

        List<String> validFileFormats = Arrays.asList("image/vnd.dwg", "image/x-dwg", "application/pdf", "application/octet-stream");
        purchaseDeliveryVO.setSource(filesService.uploadPlane(purchaseDeliveryVO.getSource(), validFileFormats));
        purchaseDeliveryEntity = purchaseDeliveryVO.toEntity(purchaseId);
        purchaseDeliveryEntity = purchaseDeliveriesRepository.save(purchaseDeliveryEntity);


        if (Objects.nonNull(purchaseDeliveryEntity)) {
            purchaseDeliveryVO.setId(purchaseDeliveryEntity.getId());
        } else
            throw new SaveErrorException("An error happened when the register was saved");

        return purchaseDeliveryVO;

    }

    public PurchaseDeliveryVO delete(Long id) throws NotFoundException {
        Optional<PurchaseDeliveryEntity> purchaseDeliveryEntity = purchaseDeliveriesRepository.findById(id);
        if (purchaseDeliveryEntity.isPresent()) {
            purchaseDeliveryEntity.get().setDeleted(Timestamp.from(Instant.now()));
            purchaseDeliveriesRepository.save(purchaseDeliveryEntity.get());
            return new PurchaseDeliveryVO(purchaseDeliveryEntity.get());
        } else
            throw new NotFoundException("Register not found.");
    }

}