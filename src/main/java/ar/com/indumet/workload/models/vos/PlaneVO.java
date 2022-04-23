package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.models.entities.PlaneEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;

@Data
public class PlaneVO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("source")
    private String source;

    @JsonProperty("description")
    private String description;

    @JsonProperty("kind")
    private KindVO kind;


    public PlaneVO(){}

    public PlaneVO(PlaneEntity planeEntity){
        this.id = planeEntity.getId();
        this.description = planeEntity.getDescription();
        this.source = planeEntity.getSource();
        this.kind = new KindVO(planeEntity.getKind());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaneVO that = (PlaneVO) o;
        return id.equals(that.id) &&
                Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, source);
    }

    public PlaneEntity toEntity(Long jobId){
        PlaneEntity planeEntity = new PlaneEntity();

        planeEntity.setId(this.id);
        planeEntity.setKind(this.kind.toEntity());
        planeEntity.setSource(this.source);
        planeEntity.setDescription(this.description);
        planeEntity.setJobId(jobId);

        return planeEntity;
    }
}
