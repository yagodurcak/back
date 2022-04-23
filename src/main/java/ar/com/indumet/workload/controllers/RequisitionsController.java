package ar.com.indumet.workload.controllers;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.RequisitionStateException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.vos.RequisitionVO;
import ar.com.indumet.workload.services.RequisitionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requisitions")
@Slf4j
@CrossOrigin
public class RequisitionsController {

    @Autowired
    private RequisitionsService requisitionsService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<RequisitionVO> findAll(
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "orderField") String orderField,
            @RequestParam(name = "orderCriteria") String orderCriteria,
            @RequestParam(name = "state", defaultValue = "") String state
    ) throws RequisitionStateException {
        return requisitionsService.findAll(pageNumber, pageSize, orderField, orderCriteria, state);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RequisitionVO find(@PathVariable(name = "id") Long id) throws NotFoundException {
        return requisitionsService.find(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RequisitionVO add(@RequestBody RequisitionVO requisitionVO) throws SaveErrorException {
        return requisitionsService.save(requisitionVO);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RequisitionVO edit(@RequestBody RequisitionVO requisitionVO) throws SaveErrorException {
        return requisitionsService.save(requisitionVO);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RequisitionVO delete(@PathVariable(name = "id") Long id) throws NotFoundException {
        return requisitionsService.delete(id);
    }

}
