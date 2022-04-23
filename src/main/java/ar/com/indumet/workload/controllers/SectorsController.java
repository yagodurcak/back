package ar.com.indumet.workload.controllers;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.vos.SectorVO;
import ar.com.indumet.workload.services.SectorsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sectors")
@Slf4j
@CrossOrigin
public class SectorsController {

    @Autowired
    private SectorsService sectorsService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<SectorVO> findAll(
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "orderField") String orderField,
            @RequestParam(name = "orderCriteria") String orderCriteria
    ) {
        return sectorsService.findAll(pageNumber, pageSize, orderField, orderCriteria);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SectorVO find(@PathVariable(name = "id") Long id) throws NotFoundException {
        return sectorsService.find(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SectorVO add(@RequestBody SectorVO sectorVO) throws SaveErrorException {
        return sectorsService.save(sectorVO);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SectorVO edit(@RequestBody SectorVO sectorVO) throws SaveErrorException {
        return sectorsService.save(sectorVO);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SectorVO delete(@PathVariable(name = "id") Long id) throws NotFoundException {
        return sectorsService.delete(id);
    }
}
