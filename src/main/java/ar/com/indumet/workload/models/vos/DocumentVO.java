package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.models.entities.DocumentEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Objects;

@Data
public class DocumentVO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("source")
    private String source;

    @JsonProperty("description")
    private String description;

    @JsonProperty("kind")
    private KindVO kind;


    public DocumentVO(){}

    public DocumentVO(DocumentEntity documentEntity){
        this.id = documentEntity.getId();
        this.description = documentEntity.getDescription();
        this.source = documentEntity.getSource();
        this.kind = new KindVO(documentEntity.getKind());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentVO that = (DocumentVO) o;
        return id.equals(that.id) &&
                Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, source);
    }

    public DocumentEntity toEntity(){
        DocumentEntity documEntity = new DocumentEntity();

        documEntity.setId(this.id);
        documEntity.setKind(this.kind.toEntity());
        documEntity.setSource(this.source);
        documEntity.setDescription(this.description);

        return documEntity;
    }
}
