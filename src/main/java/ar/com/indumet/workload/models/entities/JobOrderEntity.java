package ar.com.indumet.workload.models.entities;

import lombok.Data;
import org.hibernate.annotations.JoinFormula;
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
@Table(name = "job_orders", schema = "indumet-workload", catalog = "")
@Where(clause = "deleted IS NULL")
public class JobOrderEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "purchase_order_number")
    private String purchaseOrderNumber;

    @Basic
    @Column(name = "in_date")
    private Date inDate;

    @Basic
    @Column(name = "compromised_date")
    private Date compromisedDate;

    @Basic
    @Column(name = "delivery_date")
    private Date deliveryDate;

    @Basic
    @Column(name = "jobs_amount")
    private Integer jobsAmount;

    @Basic
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Basic
    @Column(name = "budgeted_hours")
    private Integer budgetedHours;

    @Basic
    @Column(name = "percentage_advance")
    private Integer percentageAdvance;

    @Basic
    @Column(name = "bill_number")
    private String billNumber;

    @Basic
    @Column(name = "remit_number")
    private String remitNumber;

    @Basic
    @Column(name = "real_hours_production")
    private Integer realHoursProduction;

    @Basic
    @Column(name = "observations", columnDefinition = "TEXT")
    private String observations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinFormula("(SELECT oe.id FROM order_states oe WHERE oe.job_order_id = id ORDER BY oe.id DESC LIMIT 1)")
    private OrderStateEntity state;

    @Basic
    @Column(name = "created")
    private Timestamp created;

    @Basic
    @Column(name = "modified")
    private Timestamp modified;

    @Basic
    @Column(name = "deleted")
    private Timestamp deleted;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private JobEntity job;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kind_id")
    private KindEntity kind;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "job_order_id")
    private Set<OrderStateEntity> states;

    @ManyToMany
    @JoinTable(name="job_orders_documents", joinColumns={@JoinColumn(name="job_order_id")}, inverseJoinColumns={@JoinColumn(name="document_id")})
    private Set<DocumentEntity> documents;

    public JobOrderEntity(){
        this.created = Timestamp.from(Instant.now());
        this.modified = Timestamp.from(Instant.now());
        this.states = new HashSet<>();
        this.documents = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobOrderEntity that = (JobOrderEntity) o;
        return id.equals(that.id) &&
                Objects.equals(purchaseOrderNumber, that.purchaseOrderNumber) &&
                Objects.equals(inDate, that.inDate) &&
                Objects.equals(compromisedDate, that.compromisedDate) &&
                Objects.equals(deliveryDate, that.deliveryDate) &&
                Objects.equals(jobsAmount, that.jobsAmount) &&
                Objects.equals(description, that.description) &&
                Objects.equals(budgetedHours, that.budgetedHours) &&
                Objects.equals(percentageAdvance, that.percentageAdvance) &&
                Objects.equals(billNumber, that.billNumber) &&
                Objects.equals(remitNumber, that.remitNumber) &&
                Objects.equals(observations, that.observations) &&
                Objects.equals(created, that.created) &&
                Objects.equals(modified, that.modified);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, purchaseOrderNumber, inDate, compromisedDate, deliveryDate, jobsAmount, description,budgetedHours, percentageAdvance, billNumber, remitNumber, observations, created, modified);
    }
}
