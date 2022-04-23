package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.models.entities.PurchaseOrderItemEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class PurchaseOrderItemVO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("item_number")
    private Long itemNumber;

    @JsonProperty("description")
    private String description;

    @JsonProperty("amount")
    private Integer amount;

    @JsonProperty("unit_of_measurement")
    private String unitOfMeasurement;

    @JsonProperty("aliquot")
    private Float aliquot;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("created")
    private LocalDateTime created;

    public PurchaseOrderItemVO(){}

    public PurchaseOrderItemVO(PurchaseOrderItemEntity purchaseOrderItemEntity){
        this.id =purchaseOrderItemEntity.getId();
        this.itemNumber =purchaseOrderItemEntity.getItemNumber();
        this.amount = purchaseOrderItemEntity.getAmount();
        this.unitOfMeasurement = purchaseOrderItemEntity.getUnitOfMeasurement();
        this.aliquot = purchaseOrderItemEntity.getAliquot();
        this.price = purchaseOrderItemEntity.getPrice();
        this.description = purchaseOrderItemEntity.getDescription();
        this.created = purchaseOrderItemEntity.getCreated().toLocalDateTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseOrderItemVO that = (PurchaseOrderItemVO) o;
        return id.equals(that.id) &&
                Objects.equals(itemNumber, that.itemNumber) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(unitOfMeasurement, that.unitOfMeasurement) &&
                Objects.equals(aliquot, that.aliquot) &&
                Objects.equals(price, that.price) &&
                Objects.equals(description, that.description) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, itemNumber, amount, aliquot, unitOfMeasurement, price, description, created);
    }

    public PurchaseOrderItemEntity toEntity() {
        PurchaseOrderItemEntity purchaseOrderItemEntity = new PurchaseOrderItemEntity();

        purchaseOrderItemEntity.setId(this.id);
        purchaseOrderItemEntity.setItemNumber(this.itemNumber);
        purchaseOrderItemEntity.setAmount(this.amount);
        purchaseOrderItemEntity.setUnitOfMeasurement(this.unitOfMeasurement);
        purchaseOrderItemEntity.setAliquot(this.aliquot);
        purchaseOrderItemEntity.setPrice(this.price);
        purchaseOrderItemEntity.setDescription(this.description);

        return purchaseOrderItemEntity;
    }
}
