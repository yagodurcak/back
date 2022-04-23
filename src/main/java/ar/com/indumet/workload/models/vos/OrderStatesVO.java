package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.models.entities.OrderStateEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Data
public class OrderStatesVO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("created")
    private Timestamp created;

    @JsonProperty("modified")
    private Timestamp modified;

    @JsonProperty("hoursActive")
    private Long hoursActive;

    public OrderStatesVO(){}

    public OrderStatesVO(OrderStateEntity orderStateEntity){
        this.id = orderStateEntity.getId();
        this.name = orderStateEntity.getName();
        this.created = orderStateEntity.getCreated();
        this.modified = orderStateEntity.getModified();
        this.hoursActive = (Timestamp.from(Instant.now()).getTime() - this.created.getTime()) / (60L * 60L * 1000L);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatesVO that = (OrderStatesVO) o;
        return id.equals(that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(created, that.created) &&
                Objects.equals(modified, that.modified);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, created, modified);
    }

    public OrderStateEntity toEntity(){
        OrderStateEntity orderStateEntity = new OrderStateEntity();
        orderStateEntity.setId(this.id);
        orderStateEntity.setName(this.name);
        return orderStateEntity;
    }
}
