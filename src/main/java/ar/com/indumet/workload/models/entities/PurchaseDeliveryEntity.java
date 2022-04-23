package ar.com.indumet.workload.models.entities;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Data
@Entity
@Table(name = "purchase_deliveries", schema = "indumet-workload", catalog = "")
@Where(clause = "deleted IS NULL")
public class PurchaseDeliveryEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "remit_number")
    private String remitNumber;

    @Basic
    @Column(name = "source")
    private String source;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kind_id")
    private KindEntity kind;

    @Basic
    @Column(name = "purchase_id")
    private Long purchaseId;

    @Basic
    @Column(name = "created")
    private Timestamp created;

    @Basic
    @Column(name = "deleted")
    private Timestamp deleted;

    public PurchaseDeliveryEntity(){
        this.created = Timestamp.from(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseDeliveryEntity that = (PurchaseDeliveryEntity) o;
        return id.equals(that.id) &&
                Objects.equals(remitNumber, that.remitNumber) &&
                Objects.equals(source, that.source) &&
                Objects.equals(description, that.description) &&
                Objects.equals(kind, that.kind) &&
                Objects.equals(purchaseId, that.purchaseId) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, remitNumber, source, description, kind, purchaseId, created);
    }
}
