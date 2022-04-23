package ar.com.indumet.workload.services;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.entities.PurchaseEntity;
import ar.com.indumet.workload.models.repositories.PurchasesRepository;
import ar.com.indumet.workload.models.repositories.RequisitionsRepository;
import ar.com.indumet.workload.models.vos.PurchaseVO;
import ar.com.indumet.workload.models.vos.PurchaseStateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
@Slf4j
public class PurchasesService {

    @Autowired
    private PurchasesRepository purchasesRepository;

    @Autowired
    private RequisitionsRepository requisitionsRepository;

    @Autowired
    private ValidationService validationService;

    public Page<PurchaseVO> findAll(Integer pageNumber, Integer pageSize, String orderField, String orderCriteria, String states, String query) {
        Page<PurchaseEntity> purchaseEntities;
        List<String> stateList = this.validationService.validatePurchaseState(states);
        Sort sorting = this.validationService.validateSorting(orderCriteria, orderField);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sorting);

        if(Objects.isNull(query) || query.isEmpty())
            purchaseEntities = purchasesRepository.findByCurrentStateIn(stateList, pageable);
        else
            purchaseEntities = purchasesRepository.search(query, stateList, pageable);

        return purchaseEntities.map(PurchaseVO::new);
    }

    public PurchaseVO find(Long id)  throws NotFoundException {
        Optional<PurchaseEntity> purchaseEntity = purchasesRepository.findById(id);
        if(purchaseEntity.isPresent())
            return new PurchaseVO(purchaseEntity.get());
        else
            throw new NotFoundException("Register not found.");
    }

    public PurchaseVO save(PurchaseVO purchaseVO) throws SaveErrorException {
        PurchaseEntity purchaseEntity = purchaseVO.toEntity();

        purchaseEntity = purchasesRepository.save(purchaseEntity);
        if (Objects.nonNull(purchaseEntity)) {
            purchaseVO = new PurchaseVO(purchasesRepository.findById(purchaseEntity.getId()).get());
        } else
            throw new SaveErrorException("An error happened when the register was saved");

        return purchaseVO;
    }

    public PurchaseVO delete(Long id) throws NotFoundException {
        Optional<PurchaseEntity> purchaseEntity = purchasesRepository.findById(id);
        if (purchaseEntity.isPresent()) {
            purchaseEntity.get().setDeleted(Timestamp.from(Instant.now()));
            purchasesRepository.save(purchaseEntity.get());
            return new PurchaseVO(purchaseEntity.get());
        } else
            throw new NotFoundException("Register not found.");

    }

    public List<PurchaseStateVO> changePurchaseState(Long id, PurchaseStateVO orderStatesVO) throws NotFoundException {
        Optional<PurchaseEntity> purchaseEntity = purchasesRepository.findById(id);
        if (purchaseEntity.isPresent()) {
            purchaseEntity.get().getStates().add(orderStatesVO.toEntity());
            purchaseEntity.get().setCurrentState(orderStatesVO.getName());
            purchasesRepository.save(purchaseEntity.get());
            PurchaseVO purchaseVO = new PurchaseVO(purchaseEntity.get());
            return new ArrayList<>(purchaseVO.getStates());
        } else
            throw new NotFoundException("Register not found.");
    }
}