package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.models.entities.PurchaseOrderEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class PurchaseOrderVO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("description")
    private String description;

    @JsonProperty("delivery_address")
    private String deliveryAddress;

    @JsonProperty("emission_date")
    private String emissionDate;

    @JsonProperty("estimated_delivery_date")
    private String estimatedDeliveryDate;

    @JsonProperty("fiscal_condition")
    private String fiscalCondition;

    @JsonProperty("transport")
    private String transport;

    @JsonProperty("other_tax")
    private Float otherTax;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("purchase_condition")
    private PurchaseConditionVO purchaseCondition;

    @JsonProperty("provider")
    private ProviderVO provider;

    @JsonProperty("items")
    private List<PurchaseOrderItemVO> items;


    public PurchaseOrderVO(){}

    public PurchaseOrderVO(PurchaseOrderEntity purchaseOrderEntity){

        this.id =purchaseOrderEntity.getId();
        this.description = purchaseOrderEntity.getDescription();
        this.deliveryAddress = purchaseOrderEntity.getDeliveryAddress();
        this.transport = purchaseOrderEntity.getTransport();
        this.currency = purchaseOrderEntity.getCurrency();
        this.otherTax = purchaseOrderEntity.getOtherTax();
        this.fiscalCondition = purchaseOrderEntity.getFiscalCondition();
//        if(Objects.nonNull(purchaseOrderEntity.getEmissionDate()))
            this.emissionDate = purchaseOrderEntity.getEmissionDate().toLocalDate().toString();//.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//        if(Objects.nonNull(purchaseOrderEntity.getEstimatedDeliveryDate()))
            this.estimatedDeliveryDate = purchaseOrderEntity.getEstimatedDeliveryDate().toLocalDate().toString();//.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.created = purchaseOrderEntity.getCreated().toLocalDateTime();
        this.purchaseCondition = new PurchaseConditionVO(purchaseOrderEntity.getPurchaseCondition());
        this.provider = new ProviderVO(purchaseOrderEntity.getProvider());
        this.items = purchaseOrderEntity.getItems().stream().map(PurchaseOrderItemVO::new).sorted(Comparator.comparing(PurchaseOrderItemVO::getItemNumber)).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseOrderVO that = (PurchaseOrderVO) o;
        return id.equals(that.id) &&
                Objects.equals(deliveryAddress, that.deliveryAddress) &&
                Objects.equals(emissionDate, that.emissionDate) &&
                Objects.equals(estimatedDeliveryDate, that.estimatedDeliveryDate) &&
                Objects.equals(description, that.description) &&
                Objects.equals(transport, that.transport) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(otherTax, that.otherTax) &&
                Objects.equals(purchaseCondition, that.purchaseCondition) &&
                Objects.equals(provider, that.provider) &&
                Objects.equals(items, that.items) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deliveryAddress, emissionDate, estimatedDeliveryDate, description, otherTax, transport, currency, purchaseCondition, fiscalCondition, provider, items, created);
    }

    public PurchaseOrderEntity toEntity() {
        PurchaseOrderEntity purchaseOrderEntity = new PurchaseOrderEntity();

        purchaseOrderEntity.setId(this.id);
        purchaseOrderEntity.setDeliveryAddress(this.deliveryAddress);
        purchaseOrderEntity.setDescription(this.description);
        purchaseOrderEntity.setTransport(this.transport);
        purchaseOrderEntity.setCurrency(this.currency);
        purchaseOrderEntity.setOtherTax(this.otherTax);
        purchaseOrderEntity.setFiscalCondition(this.fiscalCondition);

        if (Objects.nonNull(this.emissionDate)) {
            if (this.emissionDate.split("T").length == 2)
                purchaseOrderEntity.setEmissionDate(java.sql.Date.valueOf(this.emissionDate.split("T")[0]));
            else {
                if (this.emissionDate.split("/").length == 3) {
                    String[] dateSplited = this.emissionDate.split("/");
                    purchaseOrderEntity.setEmissionDate(java.sql.Date.valueOf(dateSplited[2] + "-" + dateSplited[1] + "-" + dateSplited[0]));
                } else
                    purchaseOrderEntity.setEmissionDate(java.sql.Date.valueOf(this.emissionDate));
            }
        }

        if (Objects.nonNull(this.estimatedDeliveryDate)) {
            if (this.estimatedDeliveryDate.split("T").length == 2)
                purchaseOrderEntity.setEstimatedDeliveryDate(java.sql.Date.valueOf(this.estimatedDeliveryDate.split("T")[0]));
            else {
                if (this.estimatedDeliveryDate.split("/").length == 3) {
                    String[] dateSplited = this.estimatedDeliveryDate.split("/");
                    purchaseOrderEntity.setEstimatedDeliveryDate(java.sql.Date.valueOf(dateSplited[2] + "-" + dateSplited[1] + "-" + dateSplited[0]));
                } else
                    purchaseOrderEntity.setEstimatedDeliveryDate(java.sql.Date.valueOf(this.estimatedDeliveryDate));
            }
        }

        purchaseOrderEntity.setPurchaseCondition(this.purchaseCondition.toEntity());
        purchaseOrderEntity.setProvider(this.provider.toEntity());
        purchaseOrderEntity.setItems(this.items.stream().map(PurchaseOrderItemVO::toEntity).collect(Collectors.toSet()));

        return purchaseOrderEntity;
    }
}
