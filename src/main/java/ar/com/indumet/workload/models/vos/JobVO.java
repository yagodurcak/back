package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.models.entities.JobEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class JobVO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("number")
    private String number;

    @JsonProperty("description")
    private String description;

    @JsonProperty("item")
    private String item;

    @JsonProperty("kind")
    private KindVO kind;

    @JsonProperty("pictures")
    private List<PictureVO> pictures;

    @JsonProperty("planes")
    private Set<PlaneVO> planes;

    @JsonProperty("documents")
    private Set<DocumentVO> documents;

    @JsonProperty("components")
    private Set<JobVO> components;

    public JobVO(){
        this.pictures = new ArrayList<>();
        this.planes = new HashSet<>();
        this.documents = new HashSet<>();
        this.components = new HashSet<>();
    }

    public JobVO(JobEntity jobEntity){
        this.id = jobEntity.getId();
        this.number = jobEntity.getNumber();
        this.description = jobEntity.getDescription();
        this.item = jobEntity.getItem();
        this.kind = new KindVO(jobEntity.getKind());

        this.planes = jobEntity.getPlanes().stream().map(PlaneVO::new).collect(Collectors.toSet());
        this.pictures = jobEntity.getPictures().stream().map(PictureVO::new).sorted(Comparator.comparing(PictureVO::getMain).reversed()).collect(Collectors.toList());
        this.components = jobEntity.getComponents().stream().map(JobVO::new).collect(Collectors.toSet());
        this.documents = jobEntity.getDocuments().stream().map(DocumentVO::new).collect(Collectors.toSet());

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobVO that = (JobVO) o;
        return id.equals(that.id) && Objects.equals(description, that.description) && Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, description, item);
    }

    public JobEntity toEntity(){
        JobEntity jobEntity = new JobEntity();

        jobEntity.setId(this.id);
        jobEntity.setNumber(this.number);
        jobEntity.setDescription(this.description);
        jobEntity.setItem(this.item);
        jobEntity.setKind(this.kind.toEntity());
        jobEntity.setComponents(this.components.stream().map(JobVO::toEntity).collect(Collectors.toSet()));
        jobEntity.setDocuments(this.documents.stream().map(DocumentVO::toEntity).collect(Collectors.toSet()));

        return jobEntity;
    }
}
