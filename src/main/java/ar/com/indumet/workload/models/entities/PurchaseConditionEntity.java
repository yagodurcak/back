package ar.com.indumet.workload.models.entities;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Data
@Entity
@Table(name = "purchase_conditions", schema = "indumet-workload", catalog = "")
@Where(clause = "deleted IS NULL")
public class PurchaseConditionEntity {
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
    @Column(name = "deleted")
    private Timestamp deleted;

    public PurchaseConditionEntity(){
        this.created = Timestamp.from(Instant.now());
    }

    public PurchaseConditionEntity(Long id, String name, Timestamp created, Timestamp modified) {
        this.id = id;
        this.name = name;
        if(Objects.isNull(created))
            this.created = Timestamp.from(Instant.now());
        else
            this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseConditionEntity that = (PurchaseConditionEntity) o;
        return id.equals(that.id)  &&
                Objects.equals(name, that.name) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, created);
    }
}
