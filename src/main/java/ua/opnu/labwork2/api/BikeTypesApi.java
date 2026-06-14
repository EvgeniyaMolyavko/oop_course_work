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
import ua.opnu.labwork2.dto.BikeTypeDto;
import ua.opnu.labwork2.entity.ConnectTypeBikeEntity;
import ua.opnu.labwork2.exceptions.ApiErrorResponseDto;
import ua.opnu.labwork2.service.BikeTypesService;

import java.util.List;

@RestController
@Tag(name = "Bike types", description = "Bike type catalog and bike-type relation endpoints")

public class BikeTypesApi {
    @Autowired private BikeTypesService bikeTypesService;


    @Operation(summary = "Get all bike types", description = "Returns the list of bike types.")
    @ApiResponse(
            responseCode = "200",
            description = "Bike types returned",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BikeTypeDto.class)))
    )
    @GetMapping("/bike-types")
    public ResponseEntity<List<BikeTypeDto>> getBikeType() {
        return bikeTypesService.getBikeType();
    }

    @Operation(summary = "Get bike type by id", description = "Returns one bike type by its identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bike type returned", content = @Content(schema = @Schema(implementation = BikeTypeDto.class))),
            @ApiResponse(responseCode = "404", description = "Bike type not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @GetMapping("/bike-types/{id}")
    public ResponseEntity<BikeTypeDto> getBikeTypeById(
            @Parameter(description = "Bike type id", example = "1")
            @PathVariable Long id
    ) {
        return bikeTypesService.getBikeTypeById(id);
    }

    @Operation(summary = "Create bike type", description = "Creates a new bike type.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Bike type created", content = @Content(schema = @Schema(implementation = BikeTypeDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bike type data", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Database operation failed", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @PostMapping("/bike-types")
    public ResponseEntity<BikeTypeDto> addBikeType(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Bike type data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = BikeTypeDto.class))
            )
            @RequestBody BikeTypeDto body
    ) {
        return bikeTypesService.addBikeType(body);
    }

    @Operation(summary = "Assign type to bike", description = "Creates a relation between bike and bike type.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Bike type assigned", content = @Content(schema = @Schema(implementation = ConnectTypeBikeEntity.class))),
            @ApiResponse(responseCode = "404", description = "Bike or bike type not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Relation already exists or cannot be created", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/bikes/{id}/types/{typeId}")
    public ResponseEntity<?> putBikeTypeById(
            @Parameter(description = "Bike id", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Bike type id", example = "1")
            @PathVariable Long typeId
    ) {
        return bikeTypesService.putBikeTypeById(id, typeId);
    }

    @Operation(summary = "Delete bike type", description = "Deletes a bike type by id if it is not in use.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Bike type deleted", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bike type cannot be deleted", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Bike type not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @DeleteMapping("/bike-types/{id}")
    public ResponseEntity<Void> deleteBikePropsById(
            @Parameter(description = "Bike type id", example = "1")
            @PathVariable Long id
    ) {
       return bikeTypesService.deleteBikePropsById(id);
    }

    @Operation(summary = "Remove type from bike", description = "Deletes a relation between bike and bike type.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Bike type relation deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bike type relation not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @DeleteMapping("/bikes/{id}/types/{typeId}")
    public ResponseEntity<?> deleteBikePropsByIdByTypeId(
            @Parameter(description = "Bike id", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Bike type id", example = "1")
            @PathVariable Long typeId
    ) {
        return bikeTypesService.deleteBikePropsByIdByTypeId(id, typeId);
    }



}
