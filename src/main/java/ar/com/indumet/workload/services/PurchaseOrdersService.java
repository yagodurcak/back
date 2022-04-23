package ar.com.indumet.workload.services;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.entities.PurchaseEntity;
import ar.com.indumet.workload.models.entities.PurchaseOrderEntity;
import ar.com.indumet.workload.models.repositories.PurchaseOrdersRepository;
import ar.com.indumet.workload.models.vos.PurchaseOrderVO;
import ar.com.indumet.workload.models.vos.PurchaseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class PurchaseOrdersService {

    @Autowired
    private PurchaseOrdersRepository purchaseOrdersRepository;

    public PurchaseOrderVO find(Long id) throws NotFoundException {
        Optional<PurchaseOrderEntity> purchaseOrderEntity = purchaseOrdersRepository.findById(id);
        if(purchaseOrderEntity.isPresent())
            return new PurchaseOrderVO(purchaseOrderEntity.get());
        else
            throw new NotFoundException("Register not found.");
    }

    public PurchaseOrderVO save(PurchaseOrderVO purchaseOrderVO) throws SaveErrorException {
        PurchaseOrderEntity purchaseOrderEntity = purchaseOrderVO.toEntity();

        purchaseOrderEntity = purchaseOrdersRepository.save(purchaseOrderEntity);
        if (Objects.nonNull(purchaseOrderEntity)) {
            purchaseOrderVO = new PurchaseOrderVO(purchaseOrdersRepository.findById(purchaseOrderEntity.getId()).get());
        } else
            throw new SaveErrorException("An error happened when the register was saved");

        return purchaseOrderVO;
    }
}