package ar.com.indumet.workload.controllers;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.vos.*;
import ar.com.indumet.workload.services.PicturesService;
import ar.com.indumet.workload.services.PlanesService;
import ar.com.indumet.workload.services.WorkRecordsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/workRecords")
@Slf4j
@CrossOrigin
public class WorkRecordsController {

    @Autowired
    private WorkRecordsService workRecordsService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<WorkRecordVO> findPage(
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "orderField") String orderField,
            @RequestParam(name = "orderCriteria") String orderCriteria,
            @RequestParam(name = "parent", required = false) String parent,
            @RequestParam(name = "foreignId", required = false) Long foreignId
    ) {
        return workRecordsService.findPage(pageNumber, pageSize, orderField, orderCriteria, parent, foreignId);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<WorkRecordVO> findAll(@RequestParam(name = "startDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                               @RequestParam(name = "endDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return workRecordsService.findBetweenDates(startDate, endDate);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WorkRecordVO add(@RequestBody WorkRecordVO workRecordVO) throws SaveErrorException, NotFoundException {
        return workRecordsService.save(workRecordVO);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WorkRecordVO edit(@RequestBody WorkRecordVO workRecordVO) throws NotFoundException, SaveErrorException {
        return workRecordsService.save(workRecordVO);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WorkRecordVO delete(@PathVariable(name = "id") Long id) throws NotFoundException {
        return workRecordsService.delete(id);
    }
}