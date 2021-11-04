package com.epam.esm.spring.repository.jdbc.querybuilder;

import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.repository.model.CertificateParam;
import com.epam.esm.spring.repository.model.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Set;

import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.ASC;
import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.DESCRIPTION;
import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.EMPTY;
import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.ID;
import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.NAME;
import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.PERCENT;
import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.TAGS;

@Component
public class QueryBuilder {

    private final CriteriaBuilder criteriaBuilder;

    public QueryBuilder(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }

    public CriteriaQuery<Certificate> buildQueryForSearch(CertificateParam param) {
        Set<String> tagSet = param.getTags();
        String search = StringUtils.isNotBlank(param.getSearch()) ? PERCENT + param.getSearch() + PERCENT : EMPTY;
        String sortBy = StringUtils.isNotBlank(param.getSort()) ? param.getSort() : ID;
        String order = StringUtils.isNotBlank(param.getOrder()) ? param.getOrder() : ASC;

        CriteriaQuery<Certificate> query = criteriaBuilder.createQuery(Certificate.class);
        Root<Certificate> root = query.from(Certificate.class);
        query.select(root);

        Predicate tagNamePredicate = buildPredicateByTagName(root, tagSet);
        Predicate searchPredicate = buildPredicateBySearch(root, search);
        Predicate resultingPredicate = criteriaBuilder.and(tagNamePredicate, searchPredicate);
        query.where(resultingPredicate);

        Order sortOrder = buildSortOrder(root, sortBy, order);
        query.orderBy(sortOrder);

        return query;
    }

    private Predicate buildPredicateBySearch(Root<Certificate> root, String search) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (StringUtils.isNotBlank(search)) {
            Predicate nameFieldPredicate = criteriaBuilder.like(root.get(NAME), search);
            Predicate descriptionFieldPredicate = criteriaBuilder.like(root.get(DESCRIPTION), search);
            predicate = criteriaBuilder.or(nameFieldPredicate, descriptionFieldPredicate);
        }

        return predicate;
    }

    private Predicate buildPredicateByTagName(Root<Certificate> root, Set<String> tagSet) {
        if (tagSet.isEmpty()) {
            return criteriaBuilder.conjunction();
        } else {
            Join<Certificate, Tag> tagJoin = root.join(TAGS);

            return tagSet.stream()
                    .map(tagName -> criteriaBuilder.equal(tagJoin.get(NAME), tagName))
                    .reduce(criteriaBuilder.conjunction(), criteriaBuilder::and);
        }
    }

    private Order buildSortOrder(Root<Certificate> root, String sortBy, String order) {
        if (order.equalsIgnoreCase(ASC)) {
            return criteriaBuilder.asc(root.get(sortBy));
        } else {
            return criteriaBuilder.desc(root.get(sortBy));
        }
    }
}