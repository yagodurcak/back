package ar.com.indumet.workload.services;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.entities.WorkerEntity;
import ar.com.indumet.workload.models.repositories.WorkersRepository;
import ar.com.indumet.workload.models.vos.WorkerVO;
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
public class WorkersService {

    @Autowired
    private WorkersRepository workersRepository;

    public Page<WorkerVO> findAll(Integer pageNumber, Integer pageSize, String orderField, String orderCriteria) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.fromString(orderCriteria), orderField);

        Page<WorkerEntity> workerEntities;
        workerEntities = workersRepository.findAll(pageable);

        return workerEntities.map(WorkerVO::new);
    }

    public WorkerVO find(Long id) throws NotFoundException {
        Optional<WorkerEntity> workerEntity = workersRepository.findById(id);
        if(workerEntity.isPresent())
            return new WorkerVO(workerEntity.get());
        else
            throw new NotFoundException("Register not found.");
    }

    public WorkerVO save(WorkerVO workerVO) throws SaveErrorException {
        WorkerEntity workerEntity = workerVO.toEntity();

        workerEntity = workersRepository.save(workerEntity);
        if (Objects.nonNull(workerEntity)) {
            workerVO.setId(workerEntity.getId());
        } else
            throw new SaveErrorException("An error happened when the register was saved");

        return workerVO;

    }

    public WorkerVO delete(Long id) throws NotFoundException {
        Optional<WorkerEntity> workerEntity = workersRepository.findById(id);
        if (workerEntity.isPresent()) {
            workerEntity.get().setDeleted(Timestamp.from(Instant.now()));
            workersRepository.save(workerEntity.get());
            return new WorkerVO(workerEntity.get());
        } else
            throw new NotFoundException("Register not found.");
    }
}