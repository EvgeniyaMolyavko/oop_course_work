package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import ua.opnu.labwork2.dto.validTime.ValidEventTime;

import java.time.LocalDateTime;
@ValidEventTime

@Schema(description = "Ride data")
public record RideDto (
     @Schema(description = "Ride id", example = "1")
     Long id,

     @Schema(description = "Ride start time", example = "2026-06-04T10:15:30")
     @NotNull(message = "Start Time can not be blank")
     LocalDateTime startTime,

     @Schema(description = "Ride end time. Null means the ride is active.", example = "2026-06-04T10:45:30", nullable = true)
     LocalDateTime endTime,

//LocalDateTime endTime,

     @Schema(description = "Ride distance in kilometers", example = "3.5")
     @NotNull(message = "Distance can not be absent")
     @PositiveOrZero(message = "Distance must be positive")
     Double distanceKm,

     @Schema(description = "User id", example = "1")
     @NotNull
     @Positive
     Integer idUser,

     @Schema(description = "Station id", example = "1")
     @NotNull
     @Positive
     Integer idStation,

     @Schema(description = "Bike id", example = "1")
     @NotNull
     @Positive
     Integer idBike

){

}
