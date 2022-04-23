package ar.com.indumet.workload.controllers;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.vos.ProviderVO;
import ar.com.indumet.workload.services.ProvidersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/providers")
@Slf4j
@CrossOrigin
public class ProvidersController {

    @Autowired
    private ProvidersService providersService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<ProviderVO> findAll(
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "orderField") String orderField,
            @RequestParam(name = "orderCriteria") String orderCriteria
    ) {
        return providersService.findAll(pageNumber, pageSize, orderField, orderCriteria);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ProviderVO find(@PathVariable(name = "id") Long id) throws NotFoundException {
        return providersService.find(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ProviderVO add(@RequestBody ProviderVO providerVO) throws SaveErrorException {
        return providersService.save(providerVO);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ProviderVO edit(@RequestBody ProviderVO providerVO) throws NotFoundException, SaveErrorException {
        return providersService.save(providerVO);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ProviderVO delete(@PathVariable(name = "id") Long id) throws NotFoundException {
        return providersService.delete(id);
    }
}
