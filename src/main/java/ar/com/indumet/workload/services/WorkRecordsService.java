package ar.com.indumet.workload.services;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.entities.WorkRecordEntity;
import ar.com.indumet.workload.models.repositories.WorkRecordsRepository;
import ar.com.indumet.workload.models.vos.WorkRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WorkRecordsService {

    @Autowired
    private WorkRecordsRepository workRecordsRepository;

    public Page<WorkRecordVO> findPage(Integer pageNumber, Integer pageSize, String orderField, String orderCriteria, String parent, Long foreignId) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.fromString(orderCriteria), orderField);

        Page<WorkRecordEntity> workRecordEntities;
        if(Objects.isNull(parent)) {
            workRecordEntities = workRecordsRepository.findAll(pageable);
        } else {
            if(parent.equals("worker")){
                workRecordEntities = workRecordsRepository.findByWorkerId(pageable, foreignId);
            } else {
                if(parent.equals("jobOrder")){
                    workRecordEntities = workRecordsRepository.findByJobOrderId(pageable, foreignId);
                } else {
                    workRecordEntities = Page.empty();
                }
            }
        }

        return workRecordEntities.map(WorkRecordVO::new);
    }

    public List<WorkRecordVO> findBetweenDates(LocalDate startDate, LocalDate endDate) {

        List<WorkRecordEntity> workRecordEntities = workRecordsRepository.findByCreatedBetween(Timestamp.valueOf(LocalDateTime.from(startDate)), Timestamp.valueOf(LocalDateTime.from(endDate)));

        return workRecordEntities.stream().map(WorkRecordVO::new).collect(Collectors.toList());
    }

    public WorkRecordVO save(WorkRecordVO workRecordVO) throws SaveErrorException, NotFoundException {
        WorkRecordEntity workRecordEntity;
        if(Objects.nonNull(workRecordVO.getId())){
            Optional<WorkRecordEntity> optionalWorkRecordEntity = workRecordsRepository.findById(workRecordVO.getId());

            if(!optionalWorkRecordEntity.isPresent())
                throw new NotFoundException("Register not found.");

            workRecordEntity = optionalWorkRecordEntity.get();

            workRecordEntity.setHours(workRecordVO.getHours());
            if(Objects.nonNull(workRecordVO.getCreated())) {
                if (workRecordVO.getCreated().split("T").length == 2)
                    workRecordEntity.setCreated(Timestamp.valueOf(workRecordVO.getCreated().split("T")[0]));
                else {
                    if(workRecordVO.getCreated().split("/").length == 3) {
                        String[] dateSplited = workRecordVO.getCreated().split("/");
                        workRecordEntity.setCreated(Timestamp.valueOf(dateSplited[2] + "-" + dateSplited[1] + "-" + dateSplited[0]));
                    } else
                        workRecordEntity.setCreated(Timestamp.valueOf(workRecordVO.getCreated()));
                }
            }
            workRecordEntity.setWorker(workRecordVO.getWorker().toEntity());
            workRecordEntity.setJobOrder(workRecordVO.getJobOrder().toEntity());
        } else {
            workRecordEntity = workRecordVO.toEntity();
        }

        workRecordEntity = workRecordsRepository.save(workRecordEntity);
        if (Objects.nonNull(workRecordEntity)) {
            workRecordVO.setId(workRecordEntity.getId());
        } else
            throw new SaveErrorException("An error happened when the register was saved");

        return workRecordVO;

    }

    public WorkRecordVO delete(Long id) throws NotFoundException {
        Optional<WorkRecordEntity> workRecordEntity = workRecordsRepository.findById(id);
        if (workRecordEntity.isPresent()) {
            workRecordEntity.get().setDeleted(Timestamp.from(Instant.now()));
            workRecordsRepository.save(workRecordEntity.get());
            return new WorkRecordVO(workRecordEntity.get());
        } else
            throw new NotFoundException("Register not found.");
    }
}