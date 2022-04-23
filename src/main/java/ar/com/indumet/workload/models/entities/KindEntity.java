package ar.com.indumet.workload.models.entities;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Data
@Entity
@Table(name = "kinds", schema = "indumet-workload", catalog = "")
@Where(clause = "deleted IS NULL")
public class KindEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "context")
    private String context;

    @Basic
    @Column(name = "created")
    private Timestamp created;

    @Basic
    @Column(name = "modified")
    private Timestamp modified;

    @Basic
    @Column(name = "deleted")
    private Timestamp deleted;

    public KindEntity(){
        this.created = Timestamp.from(Instant.now());
        this.modified = Timestamp.from(Instant.now());
    }

    public KindEntity(Long id, String name, String context, Timestamp created, Timestamp modified) {
        this.id = id;
        this.name = name;
        this.context = context;
        if(Objects.isNull(created))
            this.created = Timestamp.from(Instant.now());
        else
            this.created = created;
        if(Objects.isNull(modified))
            this.modified = Timestamp.from(Instant.now());
        else
            this.modified = modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KindEntity that = (KindEntity) o;
        return id.equals(that.id)  &&
                Objects.equals(name, that.name) &&
                Objects.equals(created, that.created) &&
                Objects.equals(modified, that.modified);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, created, modified);
    }
}
