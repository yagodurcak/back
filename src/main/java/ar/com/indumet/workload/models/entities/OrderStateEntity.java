package ar.com.indumet.workload.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Data
@Entity
@Table(name = "order_states", schema = "indumet-workload", catalog = "")
public class OrderStateEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "created")
    private Timestamp created;

    @Basic
    @Column(name = "modified")
    private Timestamp modified;

    public OrderStateEntity(){
        this.created = Timestamp.from(Instant.now());
        this.modified = Timestamp.from(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStateEntity that = (OrderStateEntity) o;
        return id.equals(that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(created, that.created) &&
                Objects.equals(modified, that.modified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, created, modified);
    }
}
