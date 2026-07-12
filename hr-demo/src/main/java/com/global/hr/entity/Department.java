package com.global.hr.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "Department", description = "Represents a department in the organization")
@Table(name = "DEPARTMENTS", uniqueConstraints = {
		@UniqueConstraint(columnNames = "name", name = "uq_department_name")
})
@Entity
@JsonPropertyOrder({ "id", "name" })
@Getter
@Setter
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DEPARTMENT_ID")
	private long id;
	private String name;

	@OneToMany(mappedBy = "department")
	private List<Employee> employees = new ArrayList<>();
}
