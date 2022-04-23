package ar.com.indumet.workload.services;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.RequisitionStateException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.entities.RequisitionEntity;
import ar.com.indumet.workload.models.repositories.RequisitionsRepository;
import ar.com.indumet.workload.models.vos.RequisitionVO;
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
public class RequisitionsService {

    @Autowired
    private RequisitionsRepository requisitionsRepository;

    @Autowired
    private ValidationService validationService;

    public Page<RequisitionVO> findAll(Integer pageNumber, Integer pageSize, String orderField, String orderCriteria, String state) throws RequisitionStateException {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.fromString(orderCriteria), orderField);

        Page<RequisitionEntity> requisitionEntities;

        if(state.isEmpty()) {
            requisitionEntities = requisitionsRepository.findAll(pageable);
            return requisitionEntities.map(RequisitionVO::new);
        } else {
            if(validationService.validateRequisitionState(state)){
                if(state.equals("Pendiente")) {
                    requisitionEntities = requisitionsRepository.findByPurchaseId(null, pageable);
                } else {
                    if(state.equals("Activos")) {
                        requisitionEntities = requisitionsRepository.findAllSate(pageable);
                    } else {
                        if(state.equals("Anulado")){
                            requisitionEntities = requisitionsRepository.findRemoved(pageable);
                        } else {
                            requisitionEntities = requisitionsRepository.findPurchaseSate(state, pageable);
                        }
                    }
                }
                return requisitionEntities.map(RequisitionVO::new);
            } else {
                throw new RequisitionStateException("Wrong requisition exception");
            }
        }
    }

    public RequisitionVO find(Long id) throws NotFoundException {
        Optional<RequisitionEntity> requisitionEntity = requisitionsRepository.findById(id);
        if(requisitionEntity.isPresent())
            return new RequisitionVO(requisitionEntity.get());
        else
            throw new NotFoundException("Register not found.");
    }

    public RequisitionVO save(RequisitionVO requisitionVO) throws SaveErrorException {
        RequisitionEntity requisitionEntity = requisitionVO.toEntity();

        requisitionEntity = requisitionsRepository.save(requisitionEntity);
        if (Objects.nonNull(requisitionEntity)) {
            requisitionVO.setId(requisitionEntity.getId());
        } else
            throw new SaveErrorException("An error happened when the register was saved");

        return requisitionVO;

    }

    public RequisitionVO delete(Long id) throws NotFoundException {
        Optional<RequisitionEntity> requisitionEntity = requisitionsRepository.findById(id);
        if (requisitionEntity.isPresent()) {
            requisitionEntity.get().setDeleted(Timestamp.from(Instant.now()));
            requisitionsRepository.save(requisitionEntity.get());
            return new RequisitionVO(requisitionEntity.get());
        } else
            throw new NotFoundException("Register not found.");
    }

}