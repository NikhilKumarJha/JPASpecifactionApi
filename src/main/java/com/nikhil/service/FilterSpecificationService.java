package com.nikhil.service;

import com.nikhil.dto.PageRequestDto;
import com.nikhil.dto.RequestDto;
import com.nikhil.dto.SearchRequestDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FilterSpecificationService<T> {

    public Specification<T> getSearchSpecification(SearchRequestDto searchRequestDto) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(searchRequestDto.getColumn()), searchRequestDto.getValue());
            }
        };
    }

    public Specification<T> getSearchSpecification(List<SearchRequestDto> searchRequestDtoList, RequestDto.GlobalOperator globalOperator) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate where = null;
                for (SearchRequestDto searchRequestDto : searchRequestDtoList) {
                    Predicate currentPredicate = null;
                    switch (searchRequestDto.getOperation()) {
                        case EQUAL:
                            currentPredicate = criteriaBuilder.equal(root.get(searchRequestDto.getColumn()), searchRequestDto.getValue());
                            break;
                        case LIKE:
                            currentPredicate = criteriaBuilder.like(root.get(searchRequestDto.getColumn()), "%" + searchRequestDto.getValue() + "%");
                            break;
                        case IN:
                            String[] split = searchRequestDto.getValue().split(",");
                            currentPredicate = root.get(searchRequestDto.getColumn()).in(Arrays.asList(split));
                            break;
                        case GREATER_THAN:
                            currentPredicate = criteriaBuilder.greaterThan(root.get(searchRequestDto.getColumn()), searchRequestDto.getValue());
                            break;
                        case LESS_THAN:
                            currentPredicate = criteriaBuilder.lessThan(root.get(searchRequestDto.getColumn()), searchRequestDto.getValue());
                            break;
                        case BETWEEN:
                            String[] split1 = searchRequestDto.getValue().split(",");
                            currentPredicate = criteriaBuilder.between(root.get(searchRequestDto.getColumn()), split1[0], split1[1]);
                            break;
                        case JOIN:
                            currentPredicate = criteriaBuilder.equal(root.join(searchRequestDto.getJoinTable() ).get(searchRequestDto.getColumn()), searchRequestDto.getValue());
                            break;
                    }
                    if (where == null) {
                        where = currentPredicate;
                    } else {
                        if (globalOperator.equals(RequestDto.GlobalOperator.OR)) {
                            where = criteriaBuilder.or(where, currentPredicate);
                        } else {
                            where = criteriaBuilder.and(where, currentPredicate);
                        }
                    }
                }
                return where;
            }
        };
    }
}
