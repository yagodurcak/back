package ar.com.indumet.workload.services;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.entities.PurchaseConditionEntity;
import ar.com.indumet.workload.models.repositories.PurchaseConditionsRepository;
import ar.com.indumet.workload.models.vos.PurchaseConditionVO;
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
public class PurchaseConditionsService {

    @Autowired
    private PurchaseConditionsRepository purchaseConditionsRepository;

    public Page<PurchaseConditionVO> findAll(Integer pageNumber, Integer pageSize, String orderField, String orderCriteria) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.fromString(orderCriteria), orderField);
        return purchaseConditionsRepository.findAll(pageable).map(PurchaseConditionVO::new);
    }

    public PurchaseConditionVO find(Long id) throws NotFoundException {
        Optional<PurchaseConditionEntity> purchaseConditionEntity = purchaseConditionsRepository.findById(id);
        if(purchaseConditionEntity.isPresent())
            return new PurchaseConditionVO(purchaseConditionEntity.get());
        else
            throw new NotFoundException("Register not found.");
    }

    public PurchaseConditionVO save(PurchaseConditionVO kindVO) throws SaveErrorException {
        PurchaseConditionEntity purchaseConditionEntity = kindVO.toEntity();

        purchaseConditionEntity = purchaseConditionsRepository.save(purchaseConditionEntity);
        if (Objects.nonNull(purchaseConditionEntity)) {
            kindVO.setId(purchaseConditionEntity.getId());
        } else
            throw new SaveErrorException("An error happened when the register was saved");

        return kindVO;

    }

    public PurchaseConditionVO delete(Long id) throws NotFoundException {
        Optional<PurchaseConditionEntity> purchaseConditionEntity = purchaseConditionsRepository.findById(id);
        if (purchaseConditionEntity.isPresent()) {
            purchaseConditionEntity.get().setDeleted(Timestamp.from(Instant.now()));
            purchaseConditionsRepository.save(purchaseConditionEntity.get());
            return new PurchaseConditionVO(purchaseConditionEntity.get());
        } else
            throw new NotFoundException("Register not found.");
    }

}