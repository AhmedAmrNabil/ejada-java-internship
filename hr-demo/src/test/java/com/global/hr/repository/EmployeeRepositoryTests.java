package com.global.hr.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import com.global.hr.entity.Department;
import com.global.hr.entity.Employee;

@DataJpaTest
public class EmployeeRepositoryTests {
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testFindByFirstName() {
		Department department = new Department();
		department.setName("IT");
		entityManager.persist(department);

		Employee employee = new Employee();
		employee.setFirstName("John");
		employee.setLastName("Doe");
		employee.setEmail("test@gmail.com");
		employee.setHireDate(java.time.LocalDate.now());
		employee.setDepartment(department);

		employeeRepository.save(employee);
		var result = employeeRepository.findByFirstName("John");
		assertNotNull(result);
		assertEquals("John", result.get(0).getFirstName());
	}
}
