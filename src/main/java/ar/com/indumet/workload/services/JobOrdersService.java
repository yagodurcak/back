package ar.com.indumet.workload.services;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.entities.JobEntity;
import ar.com.indumet.workload.models.entities.JobOrderEntity;
import ar.com.indumet.workload.models.repositories.JobOrdersRepository;
import ar.com.indumet.workload.models.repositories.JobsRepository;
import ar.com.indumet.workload.models.specifications.Specifications;
import ar.com.indumet.workload.models.vos.JobOrderVO;
import ar.com.indumet.workload.models.vos.OrderStatesVO;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class JobOrdersService {

    @Autowired
    private JobOrdersRepository jobOrdersRepository;

    @Autowired
    private JobsRepository jobsRepository;

    @Autowired
    private ValidationService validationService;

    public Page<JobOrderVO> findAll(Integer pageNumber, Integer pageSize, String orderField, String orderCriteria, String states, String query) {
        Page<JobOrderEntity> jobOrderEntities;
        List<String> stateList = this.validationService.validateJobOrderState(states);
        Sort sorting = this.validationService.validateSorting(orderCriteria, orderField);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sorting);

        if(Objects.isNull(query) || query.isEmpty())
            jobOrderEntities = jobOrdersRepository.findByStateNameIn(stateList, pageable);
        else {
            jobOrderEntities = jobOrdersRepository.findAll(
                    Specifications.getJobOrderByCurrentSateSpec(stateList)
                    .and(
                            Specifications.searchJobEntity(query)
                    )
                    , pageable);
        }

        return jobOrderEntities.map(JobOrderVO::new);
    }

    public List<JobOrderVO> findByJobId(Long jobId, Long year, Long month){
        List<JobOrderEntity> jobOrders = jobOrdersRepository.findByJobId(jobId);

        if(Objects.nonNull(year))
            jobOrders = jobOrders.stream().filter(jobOrder -> jobOrder.getInDate().toLocalDate().getYear() == year.intValue()).collect(Collectors.toList());

        if(Objects.nonNull(month))
            jobOrders = jobOrders.stream().filter(jobOrder -> jobOrder.getInDate().toLocalDate().getMonthValue() == month.intValue()).collect(Collectors.toList());

        return jobOrders.stream().map(JobOrderVO::new).collect(Collectors.toList());
    }

    public JobOrderVO find(Long id) throws NotFoundException {
        Optional<JobOrderEntity> jobOrderEntity = jobOrdersRepository.findById(id);
        if(jobOrderEntity.isPresent())
            return new JobOrderVO(jobOrderEntity.get());
        else
            throw new NotFoundException("Register not found.");
    }

    public JobOrderVO save(JobOrderVO jobOrderVO) throws SaveErrorException {
        JobOrderEntity jobOrderEntity = jobOrderVO.toEntity();

        if(Objects.isNull(jobOrderVO.getJob().getId())){
            JobEntity jobEntity = jobOrderVO.getJob().toEntity();
            jobEntity = jobsRepository.save(jobEntity);
            jobOrderEntity.setJob(jobEntity);
        }

        jobOrderEntity = jobOrdersRepository.save(jobOrderEntity);
        if (Objects.nonNull(jobOrderEntity)) {
            jobOrderVO.setId(jobOrderEntity.getId());
        } else
            throw new SaveErrorException("An error happened when the register was saved");

        return jobOrderVO;
    }

    public JobOrderVO delete(Long id) throws NotFoundException {
        Optional<JobOrderEntity> jobOrderEntity = jobOrdersRepository.findById(id);
        if (jobOrderEntity.isPresent()) {
            jobOrderEntity.get().setDeleted(Timestamp.from(Instant.now()));
            jobOrdersRepository.save(jobOrderEntity.get());
            return new JobOrderVO(jobOrderEntity.get());
        } else
            throw new NotFoundException("Register not found.");
    }

    public List<OrderStatesVO> changeOrderState(Long id, OrderStatesVO orderStatesVO) throws NotFoundException {
        Optional<JobOrderEntity> jobOrderEntity = jobOrdersRepository.findById(id);
        if (jobOrderEntity.isPresent()) {
            jobOrderEntity.get().getStates().add(orderStatesVO.toEntity());
            jobOrdersRepository.save(jobOrderEntity.get());
            JobOrderVO jobOrderVO = new JobOrderVO(jobOrderEntity.get());
            return new ArrayList<>(jobOrderVO.getStates());
        } else
            throw new NotFoundException("Register not found.");
    }
}