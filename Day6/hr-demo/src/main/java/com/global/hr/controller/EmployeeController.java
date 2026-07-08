package com.global.hr.controller;

import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class EmployeeController {

	private MessageSource messageSource;

	private Logger log = LoggerFactory.getLogger(EmployeeController.class);

	public EmployeeController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping("/employee")
	public String getName() {
		log.info("/employee endpoint called");
		return messageSource.getMessage("employee.hello", null, LocaleContextHolder.getLocale());
	}
}
