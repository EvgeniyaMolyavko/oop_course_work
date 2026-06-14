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
import ua.opnu.labwork2.dto.BikeDto;
import ua.opnu.labwork2.dto.BikesStationDto;
import ua.opnu.labwork2.exceptions.ApiErrorResponseDto;
import ua.opnu.labwork2.service.BikesService;

import java.util.List;

@RestController
@Tag(name = "Bikes", description = "Bike catalog and station assignment endpoints")
public class BikesApi {
    @Autowired private BikesService bikesService;

    @Operation(summary = "Get all bikes", description = "Returns the list of all bikes.")
    @ApiResponse(
            responseCode = "200",
            description = "Bikes returned",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BikeDto.class)))
    )
    @GetMapping("/bikes")
    public ResponseEntity<List<BikeDto>> getBike() {
        return bikesService.getBike();
    }

    @Operation(summary = "Get bike by id", description = "Returns one bike by its identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bike returned", content = @Content(schema = @Schema(implementation = BikeDto.class))),
            @ApiResponse(responseCode = "404", description = "Bike not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @GetMapping("/bikes/{id}")
    public ResponseEntity<BikeDto> getBikeById(
            @Parameter(description = "Bike id", example = "1")
            @PathVariable Long id
    ) {
       return bikesService.getBikeById(id);
    }

    @Operation(summary = "Get bikes by station id", description = "Returns a station with bikes assigned to it.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Station bikes returned", content = @Content(schema = @Schema(implementation = BikesStationDto.class))),
            @ApiResponse(responseCode = "404", description = "Station not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @GetMapping("/bikes/station/{stationId}")
    public ResponseEntity<BikesStationDto> getBikeByStationId(
            @Parameter(description = "Station id", example = "1")
            @PathVariable Long stationId
    ) {
        return bikesService.getBikeByStationId(stationId);
    }

    @Operation(summary = "Create bike", description = "Creates a new bike.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Bike created", content = @Content(schema = @Schema(implementation = BikeDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bike data", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Database operation failed", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @PostMapping("/bikes")
    public ResponseEntity<BikeDto> addBike(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Bike data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = BikeDto.class))
            )
            @RequestBody BikeDto body
    ) {
       return bikesService.addBike(body);
    }

    @Operation(summary = "Update bike", description = "Updates bike fields by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bike updated", content = @Content(schema = @Schema(implementation = BikeDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bike data", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Bike not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @PutMapping("/bikes/{id}")
    public ResponseEntity<BikeDto> updateBike(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Bike data to update",
                    required = true,
                    content = @Content(schema = @Schema(implementation = BikeDto.class))
            )
            @RequestBody BikeDto body,
            @Parameter(description = "Bike id", example = "1")
            @PathVariable Long id
    ) {
       return bikesService.updateBike(body, id);
    }

    @Operation(summary = "Delete bike", description = "Deletes a bike by id if it is not in use.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Bike deleted", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bike cannot be deleted", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Bike not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @DeleteMapping("/bikes/{id}")
    public ResponseEntity<Void> deleteBikeById(
            @Parameter(description = "Bike id", example = "1")
            @PathVariable Long id
    ) {
       return bikesService.deleteBikeById(id);
    }
}
