package com.global.hr.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.global.hr.dto.request.EmployeeFilter;
import com.global.hr.entity.Employee;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class EmployeeSpecifications {
	public static Specification<Employee> withFilter(EmployeeFilter filter) {

		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (StringUtils.hasText(filter.firstName())) {
				predicates.add(cb.like(cb.lower(root.get("firstName")), "%" + filter.firstName().toLowerCase() + "%"));
			}

			if (StringUtils.hasText(filter.lastName())) {
				predicates.add(cb.like(cb.lower(root.get("lastName")), "%" + filter.lastName().toLowerCase() + "%"));
			}

			if (StringUtils.hasText(filter.email())) {
				predicates.add(cb.like(cb.lower(root.get("email")), "%" + filter.email().toLowerCase() + "%"));
			}

			if (filter.departmentId() != null || filter.departmentName() != null) {
				var departmentJoin = root.join("department", JoinType.INNER);

				if (filter.departmentId() != null) {
					predicates.add(cb.equal(departmentJoin.get("id"), filter.departmentId()));
				}

				if (StringUtils.hasText(filter.departmentName())) {
					predicates
							.add(cb.like(cb.lower(departmentJoin.get("name")), "%" + filter.departmentName().toLowerCase() + "%"));
				}
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
