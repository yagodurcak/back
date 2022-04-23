package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.models.entities.WorkerEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Objects;

@Data
public class WorkerVO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    public WorkerVO(){    }

    public WorkerVO(WorkerEntity workerEntity) {
        this.id = workerEntity.getId();
        this.name = workerEntity.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkerVO that = (WorkerVO) o;
        return id.equals(that.id)  && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }

    public WorkerEntity toEntity(){
        WorkerEntity workerEntity = new WorkerEntity();
        workerEntity.setId(this.id);
        workerEntity.setName(this.name);
        return workerEntity;
    }
}
