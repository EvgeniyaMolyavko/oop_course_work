package ua.opnu.labwork2.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.labwork2.dto.BikesStationDto;
import ua.opnu.labwork2.dto.StationDto;
import ua.opnu.labwork2.exceptions.ApiErrorResponseDto;
import ua.opnu.labwork2.service.StationService;

import java.util.List;

@RestController
@Tag(name = "Stations", description = "Bike station endpoints")

public class StationsApi {
    @Autowired private StationService stationService;

    @Operation(summary = "Get all stations", description = "Returns the list of all stations.")
    @ApiResponse(
            responseCode = "200",
            description = "Stations returned",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StationDto.class)))
    )
    @GetMapping("/stations")
    public ResponseEntity<List<StationDto>> getStationType() {
        return stationService.getStationType();
    }

    @Operation(summary = "Get station by id", description = "Returns one station by its identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Station returned", content = @Content(schema = @Schema(implementation = StationDto.class))),
            @ApiResponse(responseCode = "404", description = "Station not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @GetMapping("/stations/{id}")
    public ResponseEntity<StationDto> getStationById(
            @Parameter(description = "Station id", example = "1")
            @PathVariable Long id
    ) {
        return stationService.getStationById(id);
    }

    @Operation(summary = "Get station bikes", description = "Returns a station with bikes assigned to it.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Station bikes returned", content = @Content(schema = @Schema(implementation = BikesStationDto.class))),
            @ApiResponse(responseCode = "404", description = "Station not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @GetMapping("/stations/{id}/bikes")
    public ResponseEntity<BikesStationDto> getBikesByStationId(
            @Parameter(description = "Station id", example = "1")
            @PathVariable Long id
    ) {
        return stationService.getBikesByStationId(id);
    }

    @Operation(summary = "Create station", description = "Creates a new station.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Station created", content = @Content(schema = @Schema(implementation = StationDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid station data", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Station already exists", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Database operation failed", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @PostMapping("/stations")
    public ResponseEntity<StationDto> addStation(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Station data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = StationDto.class))
            )
            @RequestBody StationDto body
    ) {
        return stationService.addStation(body);
    }

    @Operation(summary = "Update station", description = "Updates station fields by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Station updated", content = @Content(schema = @Schema(implementation = StationDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid station data", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Station not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Station already exists", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @PutMapping("/stations/{id}")
    public ResponseEntity<StationDto> putStationById(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Station data to update",
                    required = true,
                    content = @Content(schema = @Schema(implementation = StationDto.class))
            )
            @RequestBody StationDto body,
            @Parameter(description = "Station id", example = "1")
            @PathVariable Long id
    ) {
        return stationService.putStationById(body, id);
    }

    @Operation(summary = "Delete station", description = "Deletes a station by id if it does not contain bikes.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Station deleted", content = @Content),
            @ApiResponse(responseCode = "400", description = "Station cannot be deleted", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Station not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @DeleteMapping("/stations/{id}")
    public ResponseEntity<Void> deleteStationById(
            @Parameter(description = "Station id", example = "1")
            @PathVariable Long id
    ) {
        return stationService.deleteStationById(id);
    }

}
