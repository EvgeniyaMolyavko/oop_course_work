package ua.opnu.labwork2.exceptions;


import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "API error response")
public record ApiErrorResponseDto(
        @Schema(description = "Error timestamp", example = "2026-06-04T10:15:30")
        LocalDateTime timestamp,

        @Schema(description = "HTTP status code", example = "404")
        int status,

        @Schema(description = "HTTP error reason", example = "Not Found")
        String error,

        @Schema(description = "Error message", example = "Resource not found")
        String message,

        @Schema(description = "Request path or method and URI", example = "GET: /bikes/1")
        String path,

        @Schema(description = "Field validation errors", nullable = true, example = "{\"email\": \"Email is invalid\"}")
        Map<String, String> errors
) {}
