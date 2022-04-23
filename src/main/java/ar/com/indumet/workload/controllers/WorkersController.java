package ar.com.indumet.workload.controllers;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.vos.WorkerVO;
import ar.com.indumet.workload.services.WorkersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workers")
@Slf4j
@CrossOrigin
public class WorkersController {

    @Autowired
    private WorkersService workersService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<WorkerVO> findAll(
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "orderField") String orderField,
            @RequestParam(name = "orderCriteria") String orderCriteria
    ) {
        return workersService.findAll(pageNumber, pageSize, orderField, orderCriteria);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WorkerVO find(@PathVariable(name = "id") Long id) throws NotFoundException {
        return workersService.find(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WorkerVO add(@RequestBody WorkerVO workerVO) throws SaveErrorException {
        return workersService.save(workerVO);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WorkerVO edit(@RequestBody WorkerVO workerVO) throws NotFoundException, SaveErrorException {
        return workersService.save(workerVO);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WorkerVO delete(@PathVariable(name = "id") Long id) throws NotFoundException {
        return workersService.delete(id);
    }



}
