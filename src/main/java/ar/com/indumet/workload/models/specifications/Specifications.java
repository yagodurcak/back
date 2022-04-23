package ar.com.indumet.workload.models.specifications;

import ar.com.indumet.workload.models.entities.JobEntity_;
import ar.com.indumet.workload.models.entities.JobOrderEntity;
import ar.com.indumet.workload.models.entities.JobOrderEntity_;
import ar.com.indumet.workload.models.entities.OrderStateEntity_;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class Specifications {
    public static Specification<JobOrderEntity> getJobOrderByCurrentSateSpec(List<String> states) {
        return (Root<JobOrderEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
                -> {

            CriteriaBuilder.In<String> inClause = criteriaBuilder.in(root.join(JobOrderEntity_.state.getName()).get(OrderStateEntity_.name.getName()));
            for (String state : states) {
                inClause.value(state);
            }

            return inClause;
        };
    }


    public static Specification<JobOrderEntity> searchJobEntity(String input) {
        return (Root<JobOrderEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
                ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get(JobOrderEntity_.ID).as(String.class), "%" + input + "%"),
                        criteriaBuilder.like(root.get(JobOrderEntity_.PURCHASE_ORDER_NUMBER).as(String.class), "%" + input + "%"),
                        criteriaBuilder.like(root.join(JobOrderEntity_.job).get(JobEntity_.ID).as(String.class), input),
                        criteriaBuilder.like(criteriaBuilder.lower(root.join(JobOrderEntity_.job).get(JobEntity_.DESCRIPTION)), "%" + input.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.join(JobOrderEntity_.job).get(JobEntity_.NUMBER)), "%" + input.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.join(JobOrderEntity_.job).get(JobEntity_.ITEM)), "%" + input.toLowerCase() + "%")
                );
    }

}
