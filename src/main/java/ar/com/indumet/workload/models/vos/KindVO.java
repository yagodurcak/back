package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.models.entities.KindEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;

@Data
public class KindVO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("context")
    private String context;

    public KindVO(){    }

    public KindVO(KindEntity kindEntity) {
        this.id = kindEntity.getId();
        this.name = kindEntity.getName();
        this.context = kindEntity.getContext();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KindVO that = (KindVO) o;
        return id.equals(that.id)  && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }

    public KindEntity toEntity(){
        KindEntity kindEntity = new KindEntity();
        kindEntity.setId(this.id);
        kindEntity.setName(this.name);
        kindEntity.setContext(this.context);
        return kindEntity;
    }
}
