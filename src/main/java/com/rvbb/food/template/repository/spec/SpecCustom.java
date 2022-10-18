package com.rvbb.food.template.repository.spec;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpecCustom {

    public static <T> Specification<T> equals(String attributeName, Object val) {
        return (root, query, builder) -> builder.equal(root.get(attributeName), val);
    }

    public static <T> Specification<T> similar(String attributeName, String val) {
        return (root, query, builder) -> builder.like(root.get(attributeName), val);
    }

}
