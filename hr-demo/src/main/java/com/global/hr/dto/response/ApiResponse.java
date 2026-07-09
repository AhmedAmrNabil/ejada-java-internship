package com.global.hr.dto.response;

import java.util.List;

public record ApiResponse<T>(List<T> data, Meta meta) {
}