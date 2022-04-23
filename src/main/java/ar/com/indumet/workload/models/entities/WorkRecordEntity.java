package ar.com.indumet.workload.models.entities;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "work_records", schema = "indumet-workload", catalog = "")
@Where(clause = "deleted IS NULL")
public class WorkRecordEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "hours")
    private Integer hours;

    @Basic
    @Column(name = "register_date")
    private LocalDate registerDate;

    @Basic
    @Column(name = "created")
    private Timestamp created;

    @Basic
    @Column(name = "deleted")
    private Timestamp deleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "worker_id")
    private WorkerEntity worker;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_order_id")
    private JobOrderEntity jobOrder;

    public WorkRecordEntity(){
        this.created = Timestamp.from(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkRecordEntity that = (WorkRecordEntity) o;
        return id.equals(that.id) &&
                Objects.equals(hours, that.hours) &&
                Objects.equals(registerDate, that.registerDate) &&
                Objects.equals(created, that.created) &&
                worker.equals(that.worker)  &&
                jobOrder.equals(that.jobOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hours, registerDate, created, worker.hashCode(), jobOrder.hashCode());
    }
}
