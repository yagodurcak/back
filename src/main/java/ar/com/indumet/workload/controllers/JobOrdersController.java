package ar.com.indumet.workload.controllers;

import ar.com.indumet.workload.commons.DocumentSource;
import ar.com.indumet.workload.exceptions.DocumentFormatException;
import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.vos.DocumentVO;
import ar.com.indumet.workload.models.vos.JobOrderVO;
import ar.com.indumet.workload.models.vos.OrderStatesVO;
import ar.com.indumet.workload.services.DocumentsService;
import ar.com.indumet.workload.services.JobOrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/jobOrders")
@Slf4j
@CrossOrigin
public class JobOrdersController {

    @Autowired
    private JobOrdersService jobOrdersService;

    @Autowired
    private DocumentsService documentsService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<JobOrderVO > findAll(
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "orderField") String orderField,
            @RequestParam(name = "orderCriteria") String orderCriteria,
            @RequestParam(name = "states", required = false) String states,
            @RequestParam(name = "query", required = false) String query
    ) {
        return jobOrdersService.findAll(pageNumber, pageSize, orderField, orderCriteria, states, query);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JobOrderVO find(@PathVariable(name = "id") Long id) throws NotFoundException {
        return jobOrdersService.find(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JobOrderVO add(@RequestBody JobOrderVO jobOrderVO) throws SaveErrorException {
        return jobOrdersService.save(jobOrderVO);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JobOrderVO edit(@RequestBody JobOrderVO jobOrderVO) throws NotFoundException, SaveErrorException {
        return jobOrdersService.save(jobOrderVO);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JobOrderVO delete(@PathVariable(name = "id") Long id) throws NotFoundException {
        return jobOrdersService.delete(id);
    }

    @RequestMapping(value = "{id}/states", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<OrderStatesVO> changeOrderState(@PathVariable(name = "id") Long id, @RequestBody OrderStatesVO orderStatesVO) throws NotFoundException {
        return jobOrdersService.changeOrderState(id, orderStatesVO);
    }

    /*Documents*/
    @RequestMapping(value = "{id}/documents", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DocumentVO addDocument(@PathVariable(name = "id") Long id, @RequestBody DocumentVO documentVO) throws SaveErrorException, IOException, NotFoundException, DocumentFormatException {
        return documentsService.save(documentVO, id, DocumentSource.JOB_ORDER);
    }

    @RequestMapping(value = "{id}/documents/{documentId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DocumentVO deleteDocument(@PathVariable(name = "id") Long id, @PathVariable(name = "documentId") Long documentId) throws NotFoundException {
        return documentsService.delete(documentId);
    }
    /*Documents*/

}
