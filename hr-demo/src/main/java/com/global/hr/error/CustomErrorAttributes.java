package com.global.hr.error;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.webmvc.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.global.hr.dto.response.ApiError;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

	private final ObjectMapper objectMapper;

	public CustomErrorAttributes(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
		Map<String, Object> defaults = super.getErrorAttributes(webRequest, options);

		Object statusObj = defaults.get("status");
		int status = (statusObj instanceof Integer i) ? i : 500;
		String message = (String) defaults.getOrDefault("message", "Unexpected error");
		
		ApiError apiError = new ApiError(message, status, null);
		
		return objectMapper.convertValue(apiError, new TypeReference<Map<String, Object>>() {
		});

	}
}
