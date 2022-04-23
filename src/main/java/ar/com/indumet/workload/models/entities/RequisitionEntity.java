package ar.com.indumet.workload.models.entities;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "requisitions", schema = "indumet-workload", catalog = "")
@Where(clause = "deleted IS NULL")
public class RequisitionEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "amount")
    private Integer amount;

    @Basic
    @Column(name = "reason")
    private String reason;

    @Basic
    @Column(name = "requesting_date")
    private Date requestingDate;

    @Basic
    @Column(name = "priority")
    private Integer priority;

    @Basic
    @Column(name = "unit_of_measurement")
    private String unitOfMeasurement;

    @Basic
    @Column(name = "user_data", columnDefinition = "TEXT")
    private String userData;

    @Basic
    @Column(name = "created")
    private Timestamp created;

    @Basic
    @Column(name = "deleted")
    private Timestamp deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requesting_sector_id")
    private SectorEntity requestingSector;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_sector_id")
    private SectorEntity destinationSector;

    @Column(name = "purchase_id")
    private Long purchaseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", insertable = false, updatable = false)
    private PurchaseSingleEntity purchase;

    public RequisitionEntity(){
        this.created = Timestamp.from(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequisitionEntity that = (RequisitionEntity) o;
        return id.equals(that.id) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(reason, that.reason) &&
                Objects.equals(requestingSector, that.requestingSector) &&
                Objects.equals(destinationSector, that.destinationSector) &&
                Objects.equals(purchase, that.purchase) &&
                Objects.equals(requestingDate, that.requestingDate) &&
                Objects.equals(description, that.description) &&
                Objects.equals(priority, that.priority) &&
                Objects.equals(unitOfMeasurement, that.unitOfMeasurement) &&
                Objects.equals(purchaseId, that.purchaseId) &&
                Objects.equals(userData, that.userData) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, reason, purchase, requestingSector, destinationSector, purchaseId, requestingDate, description,priority, unitOfMeasurement, created);
    }
}
