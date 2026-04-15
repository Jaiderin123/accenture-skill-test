package com.accenture.assessment.dto;

public record ApiResponse<T>(T data, int status, String message){}