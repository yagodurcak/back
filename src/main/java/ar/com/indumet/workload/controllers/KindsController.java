package ar.com.indumet.workload.controllers;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.entities.KindEntity;
import ar.com.indumet.workload.models.vos.KindVO;
import ar.com.indumet.workload.services.KindsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kinds")
@Slf4j
@CrossOrigin
public class KindsController {

    @Autowired
    private KindsService kindsService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<KindVO> findAll(
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "orderField") String orderField,
            @RequestParam(name = "orderCriteria") String orderCriteria,
            @RequestParam(name = "context", required = false, defaultValue = "all")  String context
    ) {
        return kindsService.findAll(pageNumber, pageSize, orderField, orderCriteria, context)    ;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KindVO find(@PathVariable(name = "id") Long id) throws NotFoundException {
        return kindsService.find(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KindVO add(@RequestBody KindVO kindVO) throws SaveErrorException {
        return kindsService.save(kindVO);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KindVO edit(@RequestBody KindVO kindVO) throws NotFoundException, SaveErrorException {
        return kindsService.save(kindVO);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KindVO delete(@PathVariable(name = "id") Long id) throws NotFoundException {
        return kindsService.delete(id);
    }



}
