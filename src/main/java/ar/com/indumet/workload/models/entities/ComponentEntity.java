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
@Table(name = "job_compositions", schema = "indumet-workload", catalog = "")
public class ComponentEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "job_id")
    private Long jobId;

    @Basic
    @Column(name = "sub_job_id")
    private Long subJobId;

    public ComponentEntity(){}

    public ComponentEntity(Long jobId, Long subJobId){
        this.jobId = jobId;
        this.subJobId = subJobId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentEntity that = (ComponentEntity) o;
        return id.equals(that.id) &&
                Objects.equals(jobId, that.jobId) &&
                Objects.equals(subJobId, that.subJobId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, jobId, subJobId);
    }
}
