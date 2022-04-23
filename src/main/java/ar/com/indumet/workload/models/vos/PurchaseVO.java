package ar.com.indumet.workload.models.vos;

import ar.com.indumet.workload.commons.Converter;
import ar.com.indumet.workload.models.entities.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class PurchaseVO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("buyer")
    private String buyer;

    @JsonProperty("bill_number")
    private String billNumber;

    @JsonProperty("with_purchase_order")
    private Boolean withPurchaseOrder;

    @JsonProperty("current_state")
    private String currentState;

    @JsonProperty("created")
    private String created;

    @JsonProperty("user")
    private UserDataVO user;

    @JsonProperty("sector")
    private SectorVO sector;

    @JsonProperty("states")
    private List<PurchaseStateVO> states;

    @JsonProperty("requisitions")
    private List<RequisitionVO> requisitions;

    @JsonProperty("budgets")
    private List<BudgetVO> budgets;

    @JsonProperty("deliveries")
    private List<PurchaseDeliveryVO> deliveries;

    @JsonProperty("documents")
    private List<DocumentVO> documents;

    @JsonProperty("purchase_orders_id")
    private String purchaseOrdersId;

    @JsonProperty("providers_name")
    private String providersName;

    public PurchaseVO(){}

    public PurchaseVO(Long id, String buyer, String billNumber, Boolean withPurchaseOrder, String currentState, Timestamp created, String user, SectorEntity sector){
        this.id = id;
        this.buyer = buyer;
        this.billNumber = billNumber;
        this.currentState = currentState;
        this.withPurchaseOrder = withPurchaseOrder;
        this.user = Converter.convertValue(user, UserDataVO.class);
        this.created = created.toLocalDateTime().toString();
        this.sector = new SectorVO(sector);
    }

    public PurchaseVO(PurchaseSingleEntity purchaseSingleEntity){
        this.id =purchaseSingleEntity.getId();
        this.buyer = purchaseSingleEntity.getBuyer();
        this.billNumber = purchaseSingleEntity.getBillNumber();
        this.currentState = purchaseSingleEntity.getCurrentState();
        this.withPurchaseOrder= purchaseSingleEntity.getWithPurchaseOrder();
        this.user = Converter.convertValue(purchaseSingleEntity.getUserData(), UserDataVO.class);
        this.created = purchaseSingleEntity.getCreated().toLocalDateTime().toString();
        this.states = purchaseSingleEntity.getStates().stream().map(PurchaseStateVO::new).sorted(Comparator.comparing(PurchaseStateVO::getId).reversed()).collect(Collectors.toList());
    }

    public PurchaseVO(PurchaseEntity purchaseEntity){
        this.id =purchaseEntity.getId();
        this.buyer = purchaseEntity.getBuyer();
        this.billNumber = purchaseEntity.getBillNumber();
        this.currentState = purchaseEntity.getCurrentState();
        this.withPurchaseOrder= purchaseEntity.getWithPurchaseOrder();
        this.user = Converter.convertValue(purchaseEntity.getUserData(), UserDataVO.class);
        this.created = purchaseEntity.getCreated().toLocalDateTime().toString();
        this.sector = new SectorVO(purchaseEntity.getSector());
        this.states = purchaseEntity.getStates().stream().map(PurchaseStateVO::new).sorted(Comparator.comparing(PurchaseStateVO::getId).reversed()).collect(Collectors.toList());
        this.requisitions = purchaseEntity.getRequisitions().stream().map(RequisitionVO::new).sorted(Comparator.comparing(RequisitionVO::getId).reversed()).collect(Collectors.toList());
        this.deliveries = purchaseEntity.getDeliveries().stream().map(PurchaseDeliveryVO::new).sorted(Comparator.comparing(PurchaseDeliveryVO::getId).reversed()).collect(Collectors.toList());
        this.budgets = purchaseEntity.getBudgets().stream().map(BudgetVO::new).sorted(Comparator.comparing(BudgetVO::getId).reversed()).collect(Collectors.toList());
        this.documents = purchaseEntity.getDocuments().stream().map(DocumentVO::new).sorted(Comparator.comparing(DocumentVO::getId).reversed()).collect(Collectors.toList());
            this.purchaseOrdersId = this.budgets.stream().map(budget -> (Objects.nonNull(budget.getPurchaseOrder()) ? budget.getPurchaseOrder().getId().toString():"")).filter(id -> !id.isEmpty()).collect(Collectors.joining("<br>\n"));
        this.providersName = this.budgets.stream().map(budget -> (Objects.nonNull(budget.getPurchaseOrder()) && Objects.nonNull(budget.getPurchaseOrder().getProvider()) ? budget.getPurchaseOrder().getProvider().getName() : "")).filter(provider -> !provider.isEmpty()).collect(Collectors.joining("<br>\n"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseVO that = (PurchaseVO) o;
        return id.equals(that.id) &&
                Objects.equals(buyer, that.buyer) &&
                Objects.equals(withPurchaseOrder, that.withPurchaseOrder) &&
                Objects.equals(user, that.user) &&
                Objects.equals(sector, that.sector) &&
                Objects.equals(states, that.states) &&
                Objects.equals(requisitions, that.requisitions) &&
                Objects.equals(deliveries, that.deliveries) &&
                Objects.equals(billNumber, that.billNumber) &&
                Objects.equals(currentState, that.currentState) &&
                Objects.equals(budgets, that.budgets) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, buyer, withPurchaseOrder, currentState, user, sector, states, requisitions, deliveries, billNumber, budgets, created);
    }

    public PurchaseEntity toEntity() {
        PurchaseEntity purchaseEntity = new PurchaseEntity();

        purchaseEntity.setId(this.id);
        purchaseEntity.setBuyer(this.buyer);
        purchaseEntity.setBillNumber(this.billNumber);
        purchaseEntity.setWithPurchaseOrder(this.withPurchaseOrder);
        purchaseEntity.setCurrentState(this.currentState);
        purchaseEntity.setUserData(Converter.convertValue(this.user));
        purchaseEntity.setSector(this.sector.toEntity());
        purchaseEntity.setStates(this.states.stream().map(PurchaseStateVO::toEntity).collect(Collectors.toSet()));
        purchaseEntity.setRequisitions(this.requisitions.stream().map(RequisitionVO::toEntity).collect(Collectors.toSet()));
        purchaseEntity.setDeliveries(this.deliveries.stream().map(purchaseDeliveryVO -> purchaseDeliveryVO.toEntity(this.id)).collect(Collectors.toSet()));
        purchaseEntity.setBudgets(this.budgets.stream().map(BudgetVO::toEntity).collect(Collectors.toSet()));
        purchaseEntity.setDocuments(this.documents.stream().map(DocumentVO::toEntity).collect(Collectors.toSet()));

        return purchaseEntity;
    }
}
