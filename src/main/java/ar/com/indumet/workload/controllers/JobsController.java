package ar.com.indumet.workload.controllers;

import ar.com.indumet.workload.commons.DocumentSource;
import ar.com.indumet.workload.exceptions.DocumentFormatException;
import ar.com.indumet.workload.exceptions.ItemJobDuplicatedException;
import ar.com.indumet.workload.exceptions.NotFoundException;
import ar.com.indumet.workload.exceptions.SaveErrorException;
import ar.com.indumet.workload.models.vos.*;
import ar.com.indumet.workload.services.DocumentsService;
import ar.com.indumet.workload.services.JobsService;
import ar.com.indumet.workload.services.PicturesService;
import ar.com.indumet.workload.services.PlanesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/jobs")
@Slf4j
@CrossOrigin
public class JobsController {

    @Autowired
    private JobsService jobsService;

    @Autowired
    private PlanesService planesService;

    @Autowired
    private DocumentsService documentsService;

    @Autowired
    private PicturesService picturesService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<JobVO> findPage(
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "orderField") String orderField,
            @RequestParam(name = "orderCriteria") String orderCriteria,
            @RequestParam(name = "query", required = false) String query
    ) {
        return jobsService.findPage(pageNumber, pageSize, orderField, orderCriteria, query)    ;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<JobVO> findAll(@RequestParam(name = "query") String query) {
        return jobsService.findAll(query);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JobVO find(@PathVariable(name = "id") Long id) throws NotFoundException {
        return jobsService.find(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JobVO add(@RequestBody JobVO jobVO) throws SaveErrorException, NotFoundException, ItemJobDuplicatedException {
        return jobsService.save(jobVO);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JobVO edit(@RequestBody JobVO jobVO) throws NotFoundException, SaveErrorException, ItemJobDuplicatedException {
        return jobsService.save(jobVO);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JobVO delete(@PathVariable(name = "id") Long id) throws NotFoundException {
        return jobsService.delete(id);
    }

    /*Planes*/
    @RequestMapping(value = "{id}/planes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PlaneVO addPlane(@PathVariable(name = "id") Long id, @RequestBody PlaneVO planeVO) throws SaveErrorException, IOException {
        return planesService.save(planeVO, id);
    }

    @RequestMapping(value = "{id}/planes/{planeId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PlaneVO deletePlane(@PathVariable(name = "id") Long id, @PathVariable(name = "planeId") Long planeId) throws NotFoundException {
        return planesService.delete(planeId);
    }
    /*Planes*/

    /*Pictures*/
    @RequestMapping(value = "{id}/pictures", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PictureVO addPicture(@PathVariable(name = "id") Long id, @RequestBody String source) throws SaveErrorException, IOException {
        return picturesService.save(source, id);
    }

    @RequestMapping(value = "{id}/pictures/{pictureId}/main", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<PictureVO> mainPicture(@PathVariable(name = "id") Long id, @PathVariable(name = "pictureId") Long pictureId) throws SaveErrorException, IOException, NotFoundException {
        return picturesService.main(id, pictureId);
    }

    @RequestMapping(value = "{id}/pictures/{pictureId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PictureVO deletePicture(@PathVariable(name = "id") Long id, @PathVariable(name = "pictureId") Long pictureId) throws NotFoundException {
        return picturesService.delete(pictureId);
    }
    /*Pictures*/

    /*Documents*/
    @RequestMapping(value = "{id}/documents", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DocumentVO addDocument(@PathVariable(name = "id") Long id, @RequestBody DocumentVO documentVO) throws SaveErrorException, IOException, NotFoundException, DocumentFormatException {
        return documentsService.save(documentVO, id, DocumentSource.JOB);
    }

    @RequestMapping(value = "{id}/documents/{documentId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DocumentVO deleteDocument(@PathVariable(name = "id") Long id, @PathVariable(name = "documentId") Long documentId) throws NotFoundException {
        return documentsService.delete(documentId);
    }
    /*Documents*/

    @RequestMapping(value = "{id}/components", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JobVO addComponent(@PathVariable(name = "id") Long id, @RequestBody JobVO jobVO) throws NotFoundException, SaveErrorException, ItemJobDuplicatedException {
        return jobsService.saveComponent(jobVO, id);
    }

    @RequestMapping(value = "{id}/components", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ComponentVO linkComponent(@PathVariable(name = "id") Long id, @RequestBody ComponentVO componentVO) throws NotFoundException, SaveErrorException {
        return jobsService.linkComponent(componentVO);
    }

    @RequestMapping(value = "{id}/jobOrders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<JobOrderVO> linkComponent(@PathVariable(name = "id") Long id, @RequestParam(name = "year", required = false) Long year, @RequestParam(name = "month", required = false) Long month) throws NotFoundException, SaveErrorException {
        return jobsService.findJobOrders(id, year, month);
    }
}
