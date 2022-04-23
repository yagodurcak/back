package ar.com.indumet.workload.models.entities;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Data
@Entity
@Table(name = "sectors", schema = "indumet-workload", catalog = "")
@Where(clause = "deleted IS NULL")
public class SectorEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "created")
    private Timestamp created;

    @Basic
    @Column(name = "deleted")
    private Timestamp deleted;

    public SectorEntity(){
        this.created = Timestamp.from(Instant.now());
    }

    public SectorEntity(Long id, String name, Timestamp created) {
        this.id = id;

        if(Objects.isNull(created))
            this.created = Timestamp.from(Instant.now());
        else
            this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectorEntity that = (SectorEntity) o;
        return id.equals(that.id)  &&
                Objects.equals(name, that.name) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name,created);
    }
}