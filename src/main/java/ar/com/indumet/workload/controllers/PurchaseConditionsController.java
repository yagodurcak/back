package ar.com.indumet.workload.controllers;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.vos.KindVO;
import ar.com.indumet.workload.models.vos.PurchaseConditionVO;
import ar.com.indumet.workload.services.KindsService;
import ar.com.indumet.workload.services.PurchaseConditionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchaseConditions")
@Slf4j
@CrossOrigin
public class PurchaseConditionsController {

    @Autowired
    private PurchaseConditionsService purchaseConditionsService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<PurchaseConditionVO> findAll(
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "orderField") String orderField,
            @RequestParam(name = "orderCriteria") String orderCriteria
    ) {
        return purchaseConditionsService.findAll(pageNumber, pageSize, orderField, orderCriteria)    ;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PurchaseConditionVO find(@PathVariable(name = "id") Long id) throws NotFoundException {
        return purchaseConditionsService.find(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PurchaseConditionVO add(@RequestBody PurchaseConditionVO purchaseConditionVO) throws SaveErrorException {
        return purchaseConditionsService.save(purchaseConditionVO);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PurchaseConditionVO edit(@RequestBody PurchaseConditionVO purchaseConditionVO) throws NotFoundException, SaveErrorException {
        return purchaseConditionsService.save(purchaseConditionVO);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PurchaseConditionVO delete(@PathVariable(name = "id") Long id) throws NotFoundException {
        return purchaseConditionsService.delete(id);
    }



}
