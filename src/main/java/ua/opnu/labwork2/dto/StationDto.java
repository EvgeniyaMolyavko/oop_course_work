package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


@Schema(description = "Station data")
public record StationDto (

    @Schema(description = "Station id", example = "1")
    Long id,

    @Schema(description = "Station name", example = "Central Station")
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 150, message = "Name must contain 2-50 characters")
    String name,

    @Schema(description = "Station city", example = "Odesa")
    @NotBlank(message = "City is required")
    @Size(min = 2, max = 150, message = "City name must contain 2-50 characters")
    String city,

    @Schema(description = "Maximum station capacity", example = "25")
    @NotBlank(message = "Capacity is required")
    @Positive(message = "Capacity number must be a positive")
    Integer capacity
){
}
