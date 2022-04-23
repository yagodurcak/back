package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.models.entities.ComponentEntity;
import ar.com.indumet.workload.models.entities.KindEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Objects;

@Data
public class ComponentVO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("job_id")
    private Long jobId;

    @JsonProperty("sub_job_id")
    private Long subJobId;

    public ComponentVO(){    }

    public ComponentVO(Long jobId, Long subJobId) {
        this.jobId = jobId;
        this.subJobId = subJobId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentVO that = (ComponentVO) o;
        return id.equals(that.id) && Objects.equals(jobId, that.jobId) && Objects.equals(subJobId, that.subJobId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jobId, subJobId);
    }

    public ComponentEntity toEntity(){
        ComponentEntity componentEntity = new ComponentEntity();
        componentEntity.setId(this.id);
        componentEntity.setJobId(this.jobId);
        componentEntity.setSubJobId(this.subJobId);
        return componentEntity;
    }
}
