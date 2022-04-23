package ar.com.indumet.workload.models.entities;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "purchase_orders", schema = "indumet-workload", catalog = "")
@Where(clause = "deleted IS NULL")
public class PurchaseOrderEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Basic
    @Column(name = "other_tax")
    private Float otherTax;

    @Basic
    @Column(name = "emission_date")
    private Date emissionDate;

    @Basic
    @Column(name = "estimated_delivery_date")
    private Date estimatedDeliveryDate;

    @Basic
    @Column(name = "fiscal_condition")
    private String fiscalCondition;

    @Basic
    @Column(name = "transport")
    private String transport;

    @Basic
    @Column(name = "currency")
    private String currency;

    @Basic
    @Column(name = "created")
    private Timestamp created;

    @Basic
    @Column(name = "deleted")
    private Timestamp deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_condition_id")
    private PurchaseConditionEntity purchaseCondition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private ProviderEntity provider;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "purchase_order_id")
    private Set<PurchaseOrderItemEntity> items;

    public PurchaseOrderEntity(){
        this.created = Timestamp.from(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseOrderEntity that = (PurchaseOrderEntity) o;
        return id.equals(that.id) &&
                Objects.equals(deliveryAddress, that.deliveryAddress) &&
                Objects.equals(emissionDate, that.emissionDate) &&
                Objects.equals(description, that.description) &&
                Objects.equals(fiscalCondition, that.fiscalCondition) &&
                Objects.equals(transport, that.transport) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(otherTax, that.otherTax) &&
                Objects.equals(estimatedDeliveryDate, that.estimatedDeliveryDate) &&
                Objects.equals(purchaseCondition, that.purchaseCondition) &&
                Objects.equals(provider, that.provider) &&
                Objects.equals(items, that.items) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deliveryAddress, emissionDate, otherTax, estimatedDeliveryDate, description, transport, currency, purchaseCondition, fiscalCondition, provider, items, created);
    }
}
