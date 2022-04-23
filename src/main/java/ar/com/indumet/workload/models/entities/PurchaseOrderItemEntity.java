package ar.com.indumet.workload.models.entities;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Data
@Entity
@Table(name = "purchase_order_items", schema = "indumet-workload", catalog = "")
@Where(clause = "deleted IS NULL")
public class PurchaseOrderItemEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "item_number")
    private Long itemNumber;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "amount")
    private Integer amount;

    @Basic
    @Column(name = "unit_of_measurement")
    private String unitOfMeasurement;

    @Basic
    @Column(name = "aliquot")
    private Float aliquot;

    @Basic
    @Column(name = "price")
    private Float price;

    @Basic
    @Column(name = "created")
    private Timestamp created;

    @Basic
    @Column(name = "deleted")
    private Timestamp deleted;

    public PurchaseOrderItemEntity(){
        this.created = Timestamp.from(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseOrderItemEntity that = (PurchaseOrderItemEntity) o;
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
        return Objects.hash(id, itemNumber, amount, unitOfMeasurement, aliquot, price, description, created);
    }
}
