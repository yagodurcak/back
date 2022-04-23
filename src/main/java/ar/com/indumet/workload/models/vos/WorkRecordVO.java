package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.models.entities.WorkRecordEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Data
public class WorkRecordVO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("hours")
    private Integer hours;

    @JsonProperty("worker")
    private WorkerVO worker;

    @JsonProperty("jobOrder")
    private JobOrderVO jobOrder;

    @JsonProperty("registerDate")
    private String registerDate;

    @JsonProperty("created")
    private String created;

    public WorkRecordVO(){}

    public WorkRecordVO(WorkRecordEntity workRecordEntity){
        this.id = workRecordEntity.getId();
        this.hours = workRecordEntity.getHours();
        if(Objects.nonNull(workRecordEntity.getRegisterDate()))
            this.registerDate = workRecordEntity.getRegisterDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if(Objects.nonNull(workRecordEntity.getCreated()))
            this.created = workRecordEntity.getCreated().toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.worker = new WorkerVO(workRecordEntity.getWorker());
        this.jobOrder = new JobOrderVO(workRecordEntity.getJobOrder());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkRecordVO that = (WorkRecordVO) o;
        return id.equals(that.id) && Objects.equals(hours, that.hours) && registerDate.equals(that.registerDate) && Objects.equals(created, that.created)
                && worker.equals(that.worker) && jobOrder.equals(that.jobOrder);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, hours, registerDate, created, worker.hashCode(), jobOrder.hashCode());
    }

    public WorkRecordEntity toEntity(){
        WorkRecordEntity workRecordEntity = new WorkRecordEntity();

        workRecordEntity.setId(this.id);
        workRecordEntity.setHours(this.hours);
        if(Objects.nonNull(this.registerDate)) {
            if (this.registerDate.split("T").length == 2)
                workRecordEntity.setRegisterDate(LocalDate.parse(this.registerDate.split("T")[0]));
            else {
                if(this.registerDate.split("/").length == 3) {
                    String[] dateSplited = this.registerDate.split("/");
                    workRecordEntity.setRegisterDate(LocalDate.parse(dateSplited[2] + "-" + dateSplited[1] + "-" + dateSplited[0]));
                } else
                    workRecordEntity.setRegisterDate(LocalDate.parse(this.registerDate));
            }
        }
        if(Objects.nonNull(this.created)) {
            if (this.created.split("T").length == 2)
                workRecordEntity.setCreated(Timestamp.valueOf(this.created.split("T")[0]));
            else {
                if(this.created.split("/").length == 3) {
                    String[] dateSplited = this.created.split("/");
                    workRecordEntity.setCreated(Timestamp.valueOf(dateSplited[2] + "-" + dateSplited[1] + "-" + dateSplited[0]));
                } else
                    workRecordEntity.setCreated(Timestamp.valueOf(this.created));
            }
        }
        workRecordEntity.setWorker(worker.toEntity());
        workRecordEntity.setJobOrder(jobOrder.toEntity());


        return workRecordEntity;
    }
}
