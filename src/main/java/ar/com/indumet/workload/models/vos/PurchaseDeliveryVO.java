package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.models.entities.PurchaseDeliveryEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class PurchaseDeliveryVO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("source")
    private String source;

    @JsonProperty("description")
    private String description;

    @JsonProperty("remit_number")
    private String remitNumber;

    @JsonProperty("kind")
    private KindVO kind;

    @JsonProperty("created")
    private String created;


    public PurchaseDeliveryVO(){}

    public PurchaseDeliveryVO(PurchaseDeliveryEntity purchaseDeliveryEntity){
        this.id = purchaseDeliveryEntity.getId();
        this.description = purchaseDeliveryEntity.getDescription();
        this.source = purchaseDeliveryEntity.getSource();
        this.remitNumber = purchaseDeliveryEntity.getRemitNumber();
        this.kind = new KindVO(purchaseDeliveryEntity.getKind());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseDeliveryVO that = (PurchaseDeliveryVO) o;
        return id.equals(that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(source, that.source) &&
                Objects.equals(remitNumber, that.remitNumber) &&
                Objects.equals(kind, that.kind);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, source);
    }

    public PurchaseDeliveryEntity toEntity(Long purchaseId){
        PurchaseDeliveryEntity purchaseDeliveryEntity = new PurchaseDeliveryEntity();

        purchaseDeliveryEntity.setId(this.id);
        purchaseDeliveryEntity.setKind(this.kind.toEntity());
        purchaseDeliveryEntity.setSource(this.source);
        purchaseDeliveryEntity.setDescription(this.description);
        purchaseDeliveryEntity.setRemitNumber(this.remitNumber);
        purchaseDeliveryEntity.setPurchaseId(purchaseId);

        return purchaseDeliveryEntity;
    }
}
