package ru.vez.springai.api.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(@NotBlank String message) {}
