package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.models.entities.PictureEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;

@Data
public class PictureVO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("regular")
    private String regular;

    @JsonProperty("hd")
    private String hd;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("main")
    private Boolean main;

    public PictureVO(){}

    public PictureVO(PictureEntity pictureEntity){
        this.id = pictureEntity.getId();
        this.regular = pictureEntity.getRegular();
        this.hd = pictureEntity.getHd();
        this.thumbnail = pictureEntity.getThumbnail();
        this.main = pictureEntity.getMain();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PictureVO that = (PictureVO) o;
        return id.equals(that.id) &&
                Objects.equals(regular, that.regular) &&
                Objects.equals(hd, that.hd) &&
                Objects.equals(thumbnail, that.thumbnail) &&
                Objects.equals(main, that.main);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, regular, hd, thumbnail, main);
    }

    public PictureEntity toEntity(Long jobId){
        PictureEntity pictureEntity = new PictureEntity();

        pictureEntity.setId(this.id);
        pictureEntity.setHd(this.hd);
        pictureEntity.setRegular(this.regular);
        pictureEntity.setThumbnail(this.thumbnail);
        pictureEntity.setMain(this.main);
        pictureEntity.setJobId(jobId);

        return pictureEntity;

    }
}
