package ar.com.indumet.workload.controllers;

import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.vos.PurchaseOrderVO;
import ar.com.indumet.workload.services.PurchaseOrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchaseOrders")
@Slf4j
@CrossOrigin
public class PurchaseOrdersController {

    @Autowired
    private PurchaseOrdersService purchaseOrdersService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PurchaseOrderVO find(@PathVariable(name = "id") Long id) throws NotFoundException {
        return purchaseOrdersService.find(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PurchaseOrderVO save(@RequestBody PurchaseOrderVO purchaseOrderVO) throws NotFoundException, SaveErrorException {
        return purchaseOrdersService.save(purchaseOrderVO);
    }

}
