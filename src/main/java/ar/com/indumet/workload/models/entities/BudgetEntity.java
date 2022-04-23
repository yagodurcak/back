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
@Table(name = "budgets", schema = "indumet-workload", catalog = "")
@Where(clause = "deleted IS NULL")
public class BudgetEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "selected")
    private Boolean selected;

    @Basic
    @Column(name = "sent")
    private Boolean sent;

    @Basic
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Basic
    @Column(name = "created")
    private Timestamp created;

    @Basic
    @Column(name = "deleted")
    private Timestamp deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private ProviderEntity provider;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrderEntity purchaseOrder;

    public BudgetEntity(){
        this.created = Timestamp.from(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BudgetEntity that = (BudgetEntity) o;
        return id.equals(that.id) &&
                Objects.equals(selected, that.selected) &&
                Objects.equals(sent, that.sent) &&
                Objects.equals(purchaseOrder, that.purchaseOrder) &&
                Objects.equals(message, that.message) &&
                Objects.equals(provider, that.provider) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, selected, sent, message, provider, created, purchaseOrder);
    }
}
