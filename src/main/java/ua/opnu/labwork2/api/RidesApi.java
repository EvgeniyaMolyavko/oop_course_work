package ua.opnu.labwork2.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.labwork2.dto.RideDto;
import ua.opnu.labwork2.exceptions.ApiErrorResponseDto;
import ua.opnu.labwork2.service.RidesService;

import java.util.List;

@RestController
@Tag(name = "Rides", description = "Ride lifecycle endpoints")
public class RidesApi {
    @Autowired private RidesService ridesService;

    @Operation(summary = "Get all rides", description = "Returns the list of all rides.")
    @ApiResponse(
            responseCode = "200",
            description = "Rides returned",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = RideDto.class)))
    )
    @GetMapping("/rides")
    public ResponseEntity<List<RideDto>> getRide() {
        return ridesService.getRide();
    }



    @Operation(summary = "Get ride by id", description = "Returns one ride by its identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ride returned", content = @Content(schema = @Schema(implementation = RideDto.class))),
            @ApiResponse(responseCode = "404", description = "Ride not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @GetMapping("/rides/{id}")
    public ResponseEntity<RideDto> getRidesById(
            @Parameter(description = "Ride id", example = "1")
            @PathVariable Long id
    ) {
      return ridesService.getRidesById(id);
    }

   @Operation(summary = "Get active rides", description = "Returns rides without an end time.")
   @ApiResponse(
           responseCode = "200",
           description = "Active rides returned",
           content = @Content(array = @ArraySchema(schema = @Schema(implementation = RideDto.class)))
   )
   @GetMapping("/rides/active")
    public ResponseEntity<List<RideDto>> getactiveRide() {
       return ridesService.getactiveRide();
    }


    @Operation(summary = "Create ride", description = "Creates a new ride.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ride created", content = @Content(schema = @Schema(implementation = RideDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ride data", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Database operation failed", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @PostMapping("/rides")
        public ResponseEntity<RideDto> addRide(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Ride data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RideDto.class))
            )
            @Valid @RequestBody RideDto body
    ) {
        return ridesService.addRide(body);
    }


    @Operation(summary = "Finish ride", description = "Marks a ride as finished by setting its end time.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ride finished", content = @Content(schema = @Schema(implementation = RideDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ride data", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Ride not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @PutMapping("/rides/{id}/finish")
    public ResponseEntity<RideDto> putFinishedRidesById(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Ride data for validation",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RideDto.class))
            )
            @Valid @RequestBody RideDto body,
            @Parameter(description = "Ride id", example = "1")
            @PathVariable Long id
    ) {
        return ridesService.putFinishedRidesById(body, id);
    }


    @Operation(summary = "Delete ride", description = "Deletes a ride by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ride deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Ride not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @DeleteMapping("/rides/{id}")
    public ResponseEntity<Void> deleteRideById(
            @Parameter(description = "Ride id", example = "1")
            @PathVariable Long id
    ) {
       return ridesService.deleteRideById(id);
    }
}
