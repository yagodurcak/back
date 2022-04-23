package ar.com.indumet.workload.services;

import com.amazonaws.services.ec2.model.Purchase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ValidationService {

    @Value("${environment.job-orders-states}")
    private String jobOrderStatesLine;

    @Value("${environment.purchases-states}")
    private String purchaseStatesLine;

    @Value("${environment.requisitions-states}")
    private String requisitionsStatesLine;


    private List<String> jobOrderStates;

    private List<String> purchaseStates;

    private List<String> purchaseStatesActives;

    private List<String> requisitionsStates;

    @PostConstruct
    private void init(){
        this.jobOrderStates = Arrays.asList(jobOrderStatesLine.split(","));
        this.purchaseStates = Arrays.asList(purchaseStatesLine.split(","));
        this.purchaseStatesActives = Arrays.asList(purchaseStatesLine.split(",")).stream().filter(state -> !state.equals("Finalizado") && !state.equals("Anulado")).collect(Collectors.toList());
        this.requisitionsStates = Arrays.asList(requisitionsStatesLine.split(","));
    }

    public List<String> validateJobOrderState(String states){
        if (Objects.isNull(states) || states.isEmpty())
            return jobOrderStates;

        return Stream.of(states.split(",")).filter(state -> jobOrderStates.contains(state)).collect(Collectors.toList());
    }

    public Boolean validateRequisitionState(String state){
        if (Objects.isNull(state) || state.isEmpty())
            return false;

        return requisitionsStates.contains(state);
    }


    public Sort validateSorting(String orderCriteria, String orderField) {

        String[] ordersCriteria = orderCriteria.split(",");
        String[] ordersField = orderField.split(",");
        List<Sort.Order> orders = new ArrayList<>();

        for(int i = 0; i < ordersField.length;i++){
            Sort.Direction direction;
            if(ordersCriteria.length > i)
                direction = Sort.Direction.fromString(ordersCriteria[i]);
            else
                direction = Sort.Direction.fromString(ordersCriteria[ordersCriteria.length - 1]);

            if(direction.isAscending())
                orders.add(Sort.Order.asc(ordersField[i]));
            else
                orders.add(Sort.Order.desc(ordersField[i]));
        }

        return Sort.by(orders);
    }

    public List<String> validatePurchaseState(String states) {
        if (Objects.isNull(states) || states.isEmpty())
            return purchaseStates;

        if(states.equals("Activos"))
            return purchaseStatesActives;

        return Stream.of(states.split(",")).filter(state -> purchaseStates.contains(state)).collect(Collectors.toList());
    }
}