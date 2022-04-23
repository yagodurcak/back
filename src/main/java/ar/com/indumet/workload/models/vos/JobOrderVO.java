package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.models.entities.JobOrderEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class JobOrderVO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("purchase_order_number")
    private String purchaseOrderNumber;

    @JsonProperty("in_date")
    private String inDate;

    @JsonProperty("compromised_date")
    private String compromisedDate;

    @JsonProperty("delivery_date")
    private String deliveryDate;

    @JsonProperty("jobs_amount")
    private Integer jobsAmount;

    @JsonProperty("budgeted_hours")
    private Integer budgetedHours;

    @JsonProperty("percentage_advance")
    private Integer percentageAdvance;

    @JsonProperty("description")
    private String description;

    @JsonProperty("bill_number")
    private String billNumber;

    @JsonProperty("remit_number")
    private String remitNumber;

    @JsonProperty("real_hours_production")
    private Integer realHoursProduction;

    @JsonProperty("observations")
    private String observations;

    @JsonProperty("current_state")
    private String currentState;

    @JsonProperty("created")
    private Timestamp created;

    @JsonProperty("modified")
    private Timestamp modified;

    @JsonProperty("job")
    private JobVO job;


    @JsonProperty("kind")
    private KindVO kind;

    @JsonProperty("states")
    private List<OrderStatesVO> states = new ArrayList<>();

    @JsonProperty("documents")
    private Set<DocumentVO> documents = new HashSet<>();

    public JobOrderVO(){}

    public JobOrderVO(JobOrderEntity jobOrderEntity){


        this.id =jobOrderEntity.getId();
        this.purchaseOrderNumber = jobOrderEntity.getPurchaseOrderNumber();
        if(Objects.nonNull(jobOrderEntity.getInDate()))
            this.inDate = jobOrderEntity.getInDate().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if(Objects.nonNull(jobOrderEntity.getCompromisedDate()))
            this.compromisedDate = jobOrderEntity.getCompromisedDate().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if(Objects.nonNull(jobOrderEntity.getDeliveryDate()))
            this.deliveryDate = jobOrderEntity.getDeliveryDate().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.jobsAmount = jobOrderEntity.getJobsAmount();
        this.budgetedHours = jobOrderEntity.getBudgetedHours();
        this.percentageAdvance = jobOrderEntity.getPercentageAdvance();
        this.description = jobOrderEntity.getDescription();
        this.billNumber = jobOrderEntity.getBillNumber();
        this.remitNumber = jobOrderEntity.getRemitNumber();
        this.realHoursProduction = jobOrderEntity.getRealHoursProduction();
        this.observations = jobOrderEntity.getObservations();
        this.currentState = jobOrderEntity.getState().getName();
        this.created = jobOrderEntity.getCreated();
        this.modified = jobOrderEntity.getModified();
        this.job = new JobVO(jobOrderEntity.getJob());
        this.kind = new KindVO(jobOrderEntity.getKind());
        this.states = jobOrderEntity.getStates().stream().map(OrderStatesVO::new).sorted(Comparator.comparing(OrderStatesVO::getId).reversed()).collect(Collectors.toList());
        this.documents = jobOrderEntity.getDocuments().stream().map(DocumentVO::new).collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobOrderVO that = (JobOrderVO) o;
        return id.equals(that.id) &&
                Objects.equals(purchaseOrderNumber, that.purchaseOrderNumber) &&
                Objects.equals(inDate, that.inDate) &&
                Objects.equals(compromisedDate, that.compromisedDate) &&
                Objects.equals(deliveryDate, that.deliveryDate) &&
                Objects.equals(jobsAmount, that.jobsAmount) &&
                Objects.equals(description, that.description) &&
                Objects.equals(budgetedHours, that.budgetedHours) &&
                Objects.equals(percentageAdvance, that.percentageAdvance) &&
                Objects.equals(billNumber, that.billNumber) &&
                Objects.equals(remitNumber, that.remitNumber) &&
                Objects.equals(observations, that.observations) &&
                Objects.equals(created, that.created) &&
                Objects.equals(modified, that.modified);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, purchaseOrderNumber, inDate, compromisedDate, deliveryDate, jobsAmount, description, budgetedHours, percentageAdvance, billNumber, remitNumber, observations, created, modified);
    }

    public JobOrderEntity toEntity(){
        JobOrderEntity jobOrderEntity = new JobOrderEntity();

        jobOrderEntity.setId(this.id);
        jobOrderEntity.setPurchaseOrderNumber(this.purchaseOrderNumber);
        if(Objects.nonNull(this.inDate)) {
            if (this.inDate.split("T").length == 2)
                jobOrderEntity.setInDate(Date.valueOf(this.inDate.split("T")[0]));
            else {
                if(this.inDate.split("/").length == 3) {
                    String[] dateSplited = this.inDate.split("/");
                    jobOrderEntity.setInDate(Date.valueOf(dateSplited[2] + "-" + dateSplited[1] + "-" + dateSplited[0]));
                } else
                    jobOrderEntity.setInDate(Date.valueOf(this.inDate));
            }
        }

        if(Objects.nonNull(this.compromisedDate)) {
            if (this.compromisedDate.split("T").length == 2)
                jobOrderEntity.setCompromisedDate(Date.valueOf(this.compromisedDate.split("T")[0]));
            else {
                if(this.compromisedDate.split("/").length == 3) {
                    String[] dateSplited = this.compromisedDate.split("/");
                    jobOrderEntity.setCompromisedDate(Date.valueOf(dateSplited[2] + "-" + dateSplited[1] + "-" + dateSplited[0]));
                } else
                    jobOrderEntity.setCompromisedDate(Date.valueOf(this.compromisedDate));
            }
        }

        if(Objects.nonNull(this.deliveryDate)) {
            if (this.deliveryDate.split("T").length == 2)
                jobOrderEntity.setDeliveryDate(Date.valueOf(this.deliveryDate.split("T")[0]));
            else {
                if(this.deliveryDate.split("/").length == 3) {
                    String[] dateSplited = this.deliveryDate.split("/");
                    jobOrderEntity.setDeliveryDate(Date.valueOf(dateSplited[2] + "-" + dateSplited[1] + "-" + dateSplited[0]));
                } else
                    jobOrderEntity.setDeliveryDate(Date.valueOf(this.deliveryDate));
            }
        }

        jobOrderEntity.setJobsAmount(this.jobsAmount);
        jobOrderEntity.setDescription(this.description);
        jobOrderEntity.setBudgetedHours(this.budgetedHours);
        jobOrderEntity.setPercentageAdvance(this.percentageAdvance);
        jobOrderEntity.setBillNumber(this.billNumber);
        jobOrderEntity.setRealHoursProduction(this.realHoursProduction);
        jobOrderEntity.setRemitNumber(this.remitNumber);
        jobOrderEntity.setObservations(this.observations);
        jobOrderEntity.setJob(this.job.toEntity());
        jobOrderEntity.setKind(this.kind.toEntity());
        jobOrderEntity.setStates(this.states.stream().map(OrderStatesVO::toEntity).collect(Collectors.toSet()));
        jobOrderEntity.setDocuments(this.documents.stream().map(DocumentVO::toEntity).collect(Collectors.toSet()));

        return jobOrderEntity;
    }
}
