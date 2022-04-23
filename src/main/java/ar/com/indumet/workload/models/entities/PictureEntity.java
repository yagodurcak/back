package ar.com.indumet.workload.models.entities;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
@Data
@Entity
@Table(name = "pictures", schema = "indumet-workload", catalog = "")
@Where(clause = "deleted IS NULL")
public class PictureEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "regular")
    private String regular;

    @Basic
    @Column(name = "hd")
    private String hd;

    @Basic
    @Column(name = "thumbnail")
    private String thumbnail;

    @Basic
    @Column(name = "main")
    private Boolean main;

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

    public PictureEntity(){
        this.created = Timestamp.from(Instant.now());
        this.modified = Timestamp.from(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PictureEntity that = (PictureEntity) o;
        return id.equals(that.id) &&
                Objects.equals(regular, that.regular) &&
                Objects.equals(hd, that.hd) &&
                Objects.equals(thumbnail, that.thumbnail) &&
                Objects.equals(main, that.main) &&
                Objects.equals(created, that.created) &&
                Objects.equals(modified, that.modified);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, regular, hd, thumbnail, main, created, modified);
    }
}
