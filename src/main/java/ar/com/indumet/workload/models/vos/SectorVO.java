package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.models.entities.SectorEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Objects;

@Data
public class SectorVO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    public SectorVO(){    }

    public SectorVO(SectorEntity sectorEntity) {
        this.id = sectorEntity.getId();
        this.name = sectorEntity.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectorVO that = (SectorVO) o;
        return id.equals(that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }

    public SectorEntity toEntity(){
        SectorEntity sectorEntity = new SectorEntity();
        sectorEntity.setId(this.id);
        sectorEntity.setName(this.name);
        return sectorEntity;
    }
}
