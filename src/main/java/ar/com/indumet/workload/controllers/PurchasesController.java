package ar.com.indumet.workload.controllers;

import ar.com.indumet.workload.commons.DocumentSource;
import ar.com.indumet.workload.exceptions.DocumentFormatException;
import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.vos.DocumentVO;
import ar.com.indumet.workload.models.vos.PurchaseDeliveryVO;
import ar.com.indumet.workload.models.vos.PurchaseStateVO;
import ar.com.indumet.workload.models.vos.PurchaseVO;
import ar.com.indumet.workload.services.DocumentsService;
import ar.com.indumet.workload.services.PurchaseDeliveriesService;
import ar.com.indumet.workload.services.PurchasesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/purchases")
@Slf4j
@CrossOrigin
public class PurchasesController {

    @Autowired
    private PurchasesService purchasesService;

    @Autowired
    private PurchaseDeliveriesService purchaseDeliveriesService;

    @Autowired
    private DocumentsService documentsService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<PurchaseVO> findAll(
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "orderField") String orderField,
            @RequestParam(name = "orderCriteria") String orderCriteria,
            @RequestParam(name = "states", defaultValue = "") String states,
            @RequestParam(name = "query", defaultValue = "") String query) {
        return purchasesService.findAll(pageNumber, pageSize, orderField, orderCriteria, states, query);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PurchaseVO find(@PathVariable(name = "id") Long id) throws NotFoundException {
        return purchasesService.find(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PurchaseVO add(@RequestBody PurchaseVO purchaseVO) throws SaveErrorException {
        return purchasesService.save(purchaseVO);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PurchaseVO edit(@RequestBody PurchaseVO purchaseVO) throws SaveErrorException {
        return purchasesService.save(purchaseVO);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PurchaseVO delete(@PathVariable(name = "id") Long id) throws NotFoundException {
        return purchasesService.delete(id);
    }

    @RequestMapping(value = "{id}/states", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<PurchaseStateVO> changePurchaseState(@PathVariable(name = "id") Long id, @RequestBody PurchaseStateVO orderStatesVO) throws NotFoundException {
        return purchasesService.changePurchaseState(id, orderStatesVO);
    }

    /*PurchaseDeliveris*/
    @RequestMapping(value = "{id}/deliveries", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PurchaseDeliveryVO addPurchaseDelivery(@PathVariable(name = "id") Long id, @RequestBody PurchaseDeliveryVO purchaseDeliveryVO) throws SaveErrorException, IOException {
        return purchaseDeliveriesService.save(purchaseDeliveryVO, id);
    }

    @RequestMapping(value = "{id}/deliveries/{planeId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PurchaseDeliveryVO deletePurchaseDelivery(@PathVariable(name = "id") Long id, @PathVariable(name = "planeId") Long planeId) throws NotFoundException {
        return purchaseDeliveriesService.delete(planeId);
    }
    /*PurchaseDeliveris*/

    /*Documents*/
    @RequestMapping(value = "{id}/documents", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DocumentVO addDocument(@PathVariable(name = "id") Long id, @RequestBody DocumentVO documentVO) throws SaveErrorException, IOException, NotFoundException, DocumentFormatException {
        return documentsService.save(documentVO, id, DocumentSource.PURCHASE);
    }

    @RequestMapping(value = "{id}/documents/{documentId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DocumentVO deleteDocument(@PathVariable(name = "id") Long id, @PathVariable(name = "documentId") Long documentId) throws NotFoundException {
        return documentsService.delete(documentId);
    }
    /*Documents*/
}
