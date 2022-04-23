package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.commons.Converter;
import ar.com.indumet.workload.models.entities.PurchaseStateEntity;
import ar.com.indumet.workload.models.entities.RequisitionEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Data
public class RequisitionVO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("description")
    private String description;

    @JsonProperty("amount")
    private Integer amount;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("requesting_date")
    private String requestingDate;

    @JsonProperty("priority")
    private Integer priority;

    @JsonProperty("unit_of_measurement")
    private String  unitOfMeasurement;

    @JsonProperty("user")
    private UserDataVO user;

    @JsonProperty("created")
    private String created;

    @JsonProperty("requesting_sector")
    private SectorVO requestingSector;

    @JsonProperty("destination_sector")
    private SectorVO destinationSector;

    @JsonProperty("purchaseId")
    private Long purchaseId;

    @JsonProperty("state")
    private String state;

    public RequisitionVO(){}

    public RequisitionVO(RequisitionEntity requisitionEntity){


        this.id = requisitionEntity.getId();
        this.description = requisitionEntity.getDescription();
        if(Objects.nonNull(requisitionEntity.getRequestingDate()))
            this.requestingDate = requisitionEntity.getRequestingDate().toString();
        this.amount = requisitionEntity.getAmount();
        this.reason = requisitionEntity.getReason();
        this.priority = requisitionEntity.getPriority();
        this.unitOfMeasurement = requisitionEntity.getUnitOfMeasurement();
        this.user = Converter.convertValue(requisitionEntity.getUserData(), UserDataVO.class);
        this.requestingSector = new SectorVO(requisitionEntity.getRequestingSector());
        this.destinationSector = new SectorVO(requisitionEntity.getDestinationSector());
        this.purchaseId = requisitionEntity.getPurchaseId();
        this.created = requisitionEntity.getCreated().toLocalDateTime().toString();
        if(Objects.nonNull(requisitionEntity.getPurchase()))
            this.state = requisitionEntity.getPurchase().getStates().stream().max(Comparator.comparing(PurchaseStateEntity::getId)).orElse(new PurchaseStateEntity("Anulado")).getName();
        else
            this.state = "Pendiente";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequisitionVO that = (RequisitionVO) o;
        return id.equals(that.id) &&
                Objects.equals(requestingDate, that.requestingDate) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(reason, that.reason) &&
                Objects.equals(priority, that.priority) &&
                Objects.equals(unitOfMeasurement, that.unitOfMeasurement) &&
                Objects.equals(requestingSector, that.requestingSector) &&
                Objects.equals(description, that.description) &&
                Objects.equals(destinationSector, that.destinationSector) &&
                Objects.equals(purchaseId, that.purchaseId) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, requestingDate, amount, reason, priority, requestingSector, unitOfMeasurement, description, destinationSector, created);
    }

    public RequisitionEntity toEntity(){
        RequisitionEntity requisitionEntity = new RequisitionEntity();

        requisitionEntity.setId(this.id);

        if(Objects.nonNull(this.requestingDate)) {
            if (this.requestingDate.split("T").length == 2)
                requisitionEntity.setRequestingDate(Date.valueOf(this.requestingDate.split("T")[0]));
            else {
                if(this.requestingDate.split("/").length == 3) {
                    String[] dateSplited = this.requestingDate.split("/");
                    requisitionEntity.setRequestingDate(Date.valueOf(dateSplited[2] + "-" + dateSplited[1] + "-" + dateSplited[0]));
                } else
                    requisitionEntity.setRequestingDate(Date.valueOf(this.requestingDate));
            }
        }

        requisitionEntity.setAmount(this.amount);
        requisitionEntity.setDescription(this.description);
        requisitionEntity.setReason(this.reason);
        requisitionEntity.setPriority(this.priority);
        requisitionEntity.setUnitOfMeasurement(this.unitOfMeasurement);
        requisitionEntity.setUserData(Converter.convertValue(this.user));
        requisitionEntity.setRequestingSector(this.requestingSector.toEntity());
        requisitionEntity.setDestinationSector(this.destinationSector.toEntity());
        requisitionEntity.setPurchaseId(this.purchaseId);

        return requisitionEntity;
    }
}
