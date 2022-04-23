package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.models.entities.KindEntity;
import ar.com.indumet.workload.models.entities.PurchaseConditionEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Objects;

@Data
public class PurchaseConditionVO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    public PurchaseConditionVO(){    }

    public PurchaseConditionVO(PurchaseConditionEntity purchaseConditionEntity) {
        this.id = purchaseConditionEntity.getId();
        this.name = purchaseConditionEntity.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseConditionVO that = (PurchaseConditionVO) o;
        return id.equals(that.id)  && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public PurchaseConditionEntity toEntity(){
        PurchaseConditionEntity purchaseConditionEntity = new PurchaseConditionEntity();
        purchaseConditionEntity.setId(this.id);
        purchaseConditionEntity.setName(this.name);
        return purchaseConditionEntity;
    }
}
