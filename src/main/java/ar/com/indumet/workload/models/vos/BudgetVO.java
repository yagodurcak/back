package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.models.entities.BudgetEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class BudgetVO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("selected")
    private Boolean selected;

    @JsonProperty("sent")
    private Boolean sent;

    @JsonProperty("message")
    private String message;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("provider")
    private ProviderVO provider;

    @JsonProperty("purchase_order")
    private PurchaseOrderVO purchaseOrder;

    public BudgetVO(){}

    public BudgetVO(BudgetEntity budgetEntity){
        this.id = budgetEntity.getId();
        this.selected = budgetEntity.getSelected();
        this.sent = budgetEntity.getSent();
        this.message = budgetEntity.getMessage();
        this.created = budgetEntity.getCreated().toLocalDateTime();
        this.provider = new ProviderVO(budgetEntity.getProvider());
        if(Objects.nonNull(budgetEntity.getPurchaseOrder()))
            this.purchaseOrder = new PurchaseOrderVO(budgetEntity.getPurchaseOrder());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BudgetVO that = (BudgetVO) o;
        return id.equals(that.id) &&
                Objects.equals(selected, that.selected) &&
                Objects.equals(sent, that.sent) &&
                Objects.equals(message, that.message) &&
                Objects.equals(created, that.created) &&
                Objects.equals(purchaseOrder, that.purchaseOrder) &&
                Objects.equals(provider, that.provider);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, selected, sent, message, created, provider, purchaseOrder);
    }

    public BudgetEntity toEntity() {
        BudgetEntity budgetEntity = new BudgetEntity();

        budgetEntity.setId(this.id);
        budgetEntity.setSelected(this.selected);
        budgetEntity.setSent(this.sent);
        budgetEntity.setMessage(this.message);
        budgetEntity.setProvider(this.provider.toEntity());
        if(Objects.nonNull(this.purchaseOrder) && Objects.nonNull(this.purchaseOrder.getId()))
            budgetEntity.setPurchaseOrder(this.purchaseOrder.toEntity());
        else
            budgetEntity.setPurchaseOrder(null);

        return budgetEntity;
    }
}
