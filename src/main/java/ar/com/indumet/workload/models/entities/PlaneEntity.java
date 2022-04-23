package ar.com.indumet.workload.models.entities;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Data
@Entity
@Table(name = "planes", schema = "indumet-workload", catalog = "")
@Where(clause = "deleted IS NULL")
public class PlaneEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "source")
    private String source;

    @Basic
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kind_id")
    private KindEntity kind;

    @Basic
    @Column(name = "job_id")
    private Long jobId;

    @Basic
    @Column(name = "created")
    private Timestamp created;

    @Basic
    @Column(name = "modified")
    private Timestamp modified;

    @Basic
    @Column(name = "deleted")
    private Timestamp deleted;

    public PlaneEntity(){
        this.created = Timestamp.from(Instant.now());
        this.modified = Timestamp.from(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaneEntity that = (PlaneEntity) o;
        return id.equals(that.id) &&
                Objects.equals(source, that.source) &&
                Objects.equals(created, that.created) &&
                Objects.equals(modified, that.modified);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, source, created, modified);
    }
}
