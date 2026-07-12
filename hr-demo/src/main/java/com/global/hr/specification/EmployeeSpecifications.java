package com.global.hr.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.global.hr.dto.request.EmployeeFilter;
import com.global.hr.entity.Employee;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class EmployeeSpecifications {
	public static Specification<Employee> withFilter(EmployeeFilter filter) {

		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (filter.firstName() != null && !filter.firstName().isEmpty()) {
				predicates.add(cb.like(cb.lower(root.get("firstName")), "%" + filter.firstName().toLowerCase() + "%"));
			}

			if (filter.lastName() != null && !filter.lastName().isEmpty()) {
				predicates.add(cb.like(cb.lower(root.get("lastName")), "%" + filter.lastName().toLowerCase() + "%"));
			}

			if (filter.email() != null && !filter.email().isEmpty()) {
				predicates.add(cb.like(cb.lower(root.get("email")), "%" + filter.email().toLowerCase() + "%"));
			}
			if (filter.departmentId() != null || filter.departmentName() != null) {
				var departmentJoin = root.join("department", JoinType.INNER);
				if (filter.departmentId() != null && !filter.departmentId().isEmpty()) {
					predicates.add(cb.equal(departmentJoin.get("id"), Long.parseLong(filter.departmentId())));
				}
				if (filter.departmentName() != null && !filter.departmentName().isEmpty()) {
					predicates
							.add(cb.like(cb.lower(departmentJoin.get("name")), "%" + filter.departmentName().toLowerCase() + "%"));
				}
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
