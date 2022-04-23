package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.models.entities.PurchaseStateEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Data
public class PurchaseStateVO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("created")
    private Timestamp created;

    @JsonProperty("hoursActive")
    private Long hoursActive;

    public PurchaseStateVO(){}

    public PurchaseStateVO(PurchaseStateEntity orderStateEntity){
        this.id = orderStateEntity.getId();
        this.name = orderStateEntity.getName();
        this.created = orderStateEntity.getCreated();
        this.hoursActive = (Timestamp.from(Instant.now()).getTime() - this.created.getTime()) / (60L * 60L * 1000L);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseStateVO that = (PurchaseStateVO) o;
        return id.equals(that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, created);
    }

    public PurchaseStateEntity toEntity(){
        PurchaseStateEntity orderStateEntity = new PurchaseStateEntity();
        orderStateEntity.setId(this.id);
        orderStateEntity.setName(this.name);
        return orderStateEntity;
    }
}
