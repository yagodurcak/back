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
@Table(name = "jobs", schema = "indumet-workload", catalog = "")
@Where(clause = "deleted IS NULL")
public class JobEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "number")
    private String number;

    @Basic
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Basic
    @Column(name = "item")
    private String item;

    @Basic
    @Column(name = "created")
    private Timestamp created;

    @Basic
    @Column(name = "modified")
    private Timestamp modified;

    @Basic
    @Column(name = "deleted")
    private Timestamp deleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kind_id")
    private KindEntity kind;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "job_id")
    private Set<PictureEntity> pictures;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "job_id")
    private Set<PlaneEntity> planes;

    @ManyToMany
    @JoinTable(name="job_compositions", joinColumns={@JoinColumn(name="job_id")}, inverseJoinColumns={@JoinColumn(name="sub_job_id")})
    private Set<JobEntity> components;

    @ManyToMany
    @JoinTable(name="jobs_documents", joinColumns={@JoinColumn(name="job_id")}, inverseJoinColumns={@JoinColumn(name="document_id")})
    private Set<DocumentEntity> documents;

    public JobEntity(){
        this.created = Timestamp.from(Instant.now());
        this.modified = Timestamp.from(Instant.now());
        this.pictures = new HashSet<>();
        this.planes = new HashSet<>();
        this.documents = new HashSet<>();
        this.components = new HashSet<>();
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobEntity that = (JobEntity) o;
        return id.equals(that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(item, that.item) &&
                Objects.equals(created, that.created) &&
                Objects.equals(modified, that.modified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, item, created, modified);
    }
}
