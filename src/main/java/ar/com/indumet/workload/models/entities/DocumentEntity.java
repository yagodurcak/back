package ar.com.indumet.workload.models.entities;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Data
@Entity
@Table(name = "documents", schema = "indumet-workload", catalog = "")
@Where(clause = "deleted IS NULL")
public class DocumentEntity {

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

    @Basic
    @Column(name = "created")
    private Timestamp created;

    @Basic
    @Column(name = "modified")
    private Timestamp modified;

    @Basic
    @Column(name = "deleted")
    private Timestamp deleted;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kind_id")
    private KindEntity kind;

    public DocumentEntity(){
        this.created = Timestamp.from(Instant.now());
        this.modified = Timestamp.from(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentEntity that = (DocumentEntity) o;
        return id.equals(that.id) &&
                Objects.equals(source, that.source) &&
                Objects.equals(description, that.description) &&
                kind.equals(that.kind) &&
                Objects.equals(created, that.created) &&
                Objects.equals(modified, that.modified);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, source, description, created, modified);
    }
}
