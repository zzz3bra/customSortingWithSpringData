package org.example.customSortingWithSpringData;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AuthorSpecifications {
    public static Specification<Author> orderByPseudonymOrFullName() {
        return (user, cq, cb) -> {
            Expression<?> functionBasedAttributePath =
                    cb.lower(cb.coalesce(user.get("pseudonym"), user.get("fullName")));
            Expression<Author> pathToUserId = user.get("id");

            List<Order> orderByList = new ArrayList<>();
            orderByList.add(cb.asc(functionBasedAttributePath));
            // adding second sorting attribute to ensure stable order of users with same pseudonym
            orderByList.add(cb.asc(pathToUserId));

            cq.orderBy(orderByList);

            Specification<Author> emptySpec = Specification.where(null); // empty no-op specification
            return emptySpec.toPredicate(user, cq, cb);
        };
    }
}
