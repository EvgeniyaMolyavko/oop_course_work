package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.antlr.v4.runtime.misc.NotNull;
import ua.opnu.labwork2.enums.Status;

@Schema(description = "Bike data")
public record BikeDto(
        @Schema(description = "Bike id", example = "1")
        @NotNull
        Long id,

        @Schema(description = "Bike model", example = "Trek FX 2")
        @NotBlank
        @Size(min=2, max=150)
        String model,

        @Schema(description = "Bike charge status", example = "HIGH")
        @NotNull
        Status status,

        @Schema(description = "Battery level from 0 to 100", example = "85")
        @NotBlank
        @Min(value = 0)
        @Max(value = 100)
        Integer batteryLevel,

        @Schema(description = "Station id where bike is located", example = "1")
        @NotNull
        Integer idStation,

        @Schema(description = "Bike type id", example = "1")
        @NotNull
        Integer idBikeType
) {
}
