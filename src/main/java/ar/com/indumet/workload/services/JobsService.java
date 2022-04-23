package ar.com.indumet.workload.services;

import ar.com.indumet.workload.exceptions.ItemJobDuplicatedException;
import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.entities.ComponentEntity;
import ar.com.indumet.workload.models.entities.JobEntity;
import ar.com.indumet.workload.models.repositories.ComponentsRepository;
import ar.com.indumet.workload.models.repositories.JobsRepository;
import ar.com.indumet.workload.models.vos.ComponentVO;
import ar.com.indumet.workload.models.vos.JobOrderVO;
import ar.com.indumet.workload.models.vos.JobVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JobsService {

    @Autowired
    private JobsRepository jobsRepository;

    @Autowired
    private ComponentsRepository componentsRepository;

    @Autowired
    private JobOrdersService jobOrdersService;

    private List<JobVO> jobs;

    public Page<JobVO> findPage(Integer pageNumber, Integer pageSize, String orderField, String orderCriteria, String query) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.fromString(orderCriteria), orderField);

        Page<JobEntity> jobEntities;
        if(Objects.isNull(query))
            jobEntities = jobsRepository.findAll(pageable);
        else
            jobEntities = jobsRepository.search(query, pageable);

        return jobEntities.map(JobVO::new);
    }

    public List<JobVO> findAll(String query) {
        if(Objects.isNull(this.jobs))
            this.jobs = jobsRepository.findAll().stream().map(JobVO::new).collect(Collectors.toList());
        return this.jobs
                .stream()
                .filter(jobVO -> jobVO.getItem().contains(query) || jobVO.getDescription().contains(query) || jobVO.getNumber().contains(query))
                .collect(Collectors.toList());
    }

    public JobVO find(Long id) throws NotFoundException {
        Optional<JobEntity> jobEntity = jobsRepository.findById(id);
        if(jobEntity.isPresent())
            return new JobVO(jobEntity.get());
        else
            throw new NotFoundException("Register not found.");
    }

    public JobVO save(JobVO jobVO) throws SaveErrorException, NotFoundException, ItemJobDuplicatedException {
        JobEntity jobEntity;
        if(Objects.nonNull(jobVO.getId())){
            Optional<JobEntity> optionalJobEntity = jobsRepository.findById(jobVO.getId());

            if(!optionalJobEntity.isPresent())
                throw new NotFoundException("Register not found.");

            jobEntity = optionalJobEntity.get();

            jobEntity.setItem(jobVO.getItem());
            jobEntity.setDescription(jobVO.getDescription());
            jobEntity.setNumber(jobVO.getNumber());
            jobEntity.setKind(jobVO.getKind().toEntity());
            jobEntity.setModified(Timestamp.from(Instant.now()));

        } else {
            if(jobsRepository.findByItemOrNumber(jobVO.getItem(), jobVO.getNumber()).isPresent())
                throw new ItemJobDuplicatedException("Duplicate item or number field.");
            jobEntity = jobVO.toEntity();
        }

        jobEntity = jobsRepository.save(jobEntity);
        if (Objects.nonNull(jobEntity)) {
            jobVO.setId(jobEntity.getId());
        } else
            throw new SaveErrorException("An error happened when the register was saved");

        this.jobs = jobsRepository.findAll().stream().map(JobVO::new).collect(Collectors.toList());
        return jobVO;

    }

    public JobVO delete(Long id) throws NotFoundException {
        Optional<JobEntity> jobEntity = jobsRepository.findById(id);
        if (jobEntity.isPresent()) {
            jobEntity.get().setDeleted(Timestamp.from(Instant.now()));
            jobsRepository.save(jobEntity.get());
            this.jobs = jobsRepository.findAll().stream().map(JobVO::new).collect(Collectors.toList());
            return new JobVO(jobEntity.get());
        } else
            throw new NotFoundException("Register not found.");
    }

    public JobVO saveComponent(JobVO jobVO, Long parentId) throws SaveErrorException, NotFoundException, ItemJobDuplicatedException {
        JobVO component = this.save(jobVO);
        ComponentEntity componentEntity = new ComponentEntity(parentId, component.getId());
        componentsRepository.save(componentEntity);
        return component;
    }

    public ComponentVO linkComponent(ComponentVO componentVO) {
        ComponentEntity componentEntity = componentVO.toEntity();
        componentsRepository.save(componentEntity);
        return componentVO;
    }

    public List<JobOrderVO> findJobOrders(Long id, Long year, Long month) {
        return this.jobOrdersService.findByJobId(id, year, month);
    }
}