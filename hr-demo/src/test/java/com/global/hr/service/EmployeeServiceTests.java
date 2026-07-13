package com.global.hr.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.global.hr.entity.Employee;
import com.global.hr.error.ResourceNotFoundException;
import com.global.hr.repository.EmployeeRepository;

@SpringBootTest
public class EmployeeServiceTests {

	@MockitoBean
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	@Test
	public void findById() {
		long id = 1L;
		Employee employee = new Employee();
		employee.setId(id);

		Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

		Employee result = employeeService.findById(id);

		assertNotNull(result);
		assertEquals(id, result.getId());
		verify(employeeRepository).findById(id);
	}

	@Test
	public void findByIdThrowOnNotFound() {
		long id = 1L;

		Mockito.when(employeeRepository.findById(id))
				.thenReturn(Optional.empty());

		Assertions.assertThrows(
				ResourceNotFoundException.class,
				() -> employeeService.findById(id));
		verify(employeeRepository).findById(id);
	}
}
