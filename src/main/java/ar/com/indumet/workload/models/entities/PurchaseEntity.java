package ar.com.indumet.workload.models.entities;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "purchases", schema = "indumet-workload", catalog = "")
@Where(clause = "deleted IS NULL")
public class PurchaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "buyer")
    private String buyer;

    @Basic
    @Column(name = "bill_number")
    private String billNumber;

    @Basic
    @Column(name = "with_purchase_order")
    private Boolean withPurchaseOrder;

    @Basic
    @Column(name = "current_state")
    private String currentState;

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
    @JoinColumn(name = "sector_id")
    private SectorEntity sector;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "purchase_id")
    private Set<PurchaseStateEntity> states;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "purchase_id")
    private Set<RequisitionEntity> requisitions;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "purchase_id")
    private Set<PurchaseDeliveryEntity> deliveries;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "purchase_id")
    private Set<BudgetEntity> budgets;

    @ManyToMany
    @JoinTable(name="purchases_documents", joinColumns={@JoinColumn(name="purchase_id")}, inverseJoinColumns={@JoinColumn(name="document_id")})
    private Set<DocumentEntity> documents;

    public PurchaseEntity(){
        this.withPurchaseOrder = false;
        this.created = Timestamp.from(Instant.now());
        this.states = new HashSet<>();
        this.requisitions = new HashSet<>();
        this.deliveries = new HashSet<>();
        this.budgets = new HashSet<>();
        this.documents = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseEntity that = (PurchaseEntity) o;
        return id.equals(that.id) &&
                Objects.equals(buyer, that.buyer) &&
                Objects.equals(billNumber, that.billNumber) &&
                Objects.equals(withPurchaseOrder, that.withPurchaseOrder) &&
                Objects.equals(description, that.description) &&
                Objects.equals(currentState, that.currentState) &&
                Objects.equals(userData, that.userData) &&
                Objects.equals(sector, that.sector) &&
                Objects.equals(states, that.states) &&
                Objects.equals(requisitions, that.requisitions) &&
                Objects.equals(deliveries, that.deliveries) &&
                Objects.equals(budgets, that.budgets) &&
                Objects.equals(documents, that.documents) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buyer,billNumber,withPurchaseOrder,description,currentState, userData,sector, states,requisitions,deliveries,budgets,documents,created);
    }
}
