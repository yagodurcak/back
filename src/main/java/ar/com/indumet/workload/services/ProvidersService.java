package ar.com.indumet.workload.services;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.entities.ProviderEntity;
import ar.com.indumet.workload.models.repositories.ProvidersRepository;
import ar.com.indumet.workload.models.vos.ProviderVO;
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
public class ProvidersService {

    @Autowired
    private ProvidersRepository providersRepository;

    public Page<ProviderVO> findAll(Integer pageNumber, Integer pageSize, String orderField, String orderCriteria) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.fromString(orderCriteria), orderField);

        Page<ProviderEntity> providerEntities;
        providerEntities = providersRepository.findAll(pageable);

        return providerEntities.map(ProviderVO::new);
    }

    public ProviderVO find(Long id) throws NotFoundException {
        Optional<ProviderEntity> providerEntity = providersRepository.findById(id);
        if(providerEntity.isPresent())
            return new ProviderVO(providerEntity.get());
        else
            throw new NotFoundException("Register not found.");
    }

    public ProviderVO save(ProviderVO providerVO) throws SaveErrorException {
        ProviderEntity providerEntity = providerVO.toEntity();

        providerEntity = providersRepository.save(providerEntity);
        if (Objects.nonNull(providerEntity)) {
            providerVO.setId(providerEntity.getId());
        } else
            throw new SaveErrorException("An error happened when the register was saved");

        return providerVO;

    }

    public ProviderVO delete(Long id) throws NotFoundException {
        Optional<ProviderEntity> providerEntity = providersRepository.findById(id);
        if (providerEntity.isPresent()) {
            providerEntity.get().setDeleted(Timestamp.from(Instant.now()));
            providersRepository.save(providerEntity.get());
            return new ProviderVO(providerEntity.get());
        } else
            throw new NotFoundException("Register not found.");
    }
}