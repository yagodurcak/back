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
public class PurchaseSingleEntity {

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "purchase_id")
    private Set<PurchaseStateEntity> states;

    public PurchaseSingleEntity(){
        this.withPurchaseOrder = false;
        this.created = Timestamp.from(Instant.now());
        this.states = new HashSet<>();
    }

}
