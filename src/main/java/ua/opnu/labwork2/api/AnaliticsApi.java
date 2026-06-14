package ua.opnu.labwork2.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.opnu.labwork2.dto.RideDto;
import ua.opnu.labwork2.enums.Status;
import ua.opnu.labwork2.repositories.BikeRepository;
import ua.opnu.labwork2.repositories.RideRepository;
import ua.opnu.labwork2.repositories.StationRepository;
import ua.opnu.labwork2.repositories.UserRepository;
import ua.opnu.labwork2.service.AnaliticsService;

import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Analytics", description = "Aggregate metrics and reports")

public class AnaliticsApi {
    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired private AnaliticsService analiticsService;

    @Operation(summary = "Get bikes count", description = "Returns the total number of bikes.")
    @ApiResponse(
            responseCode = "200",
            description = "Bikes count returned",
            content = @Content(schema = @Schema(type = "integer", format = "int64", example = "42"))
    )
    @GetMapping("/analytics/bikes/count")
    public ResponseEntity<Long> getBikesCount() {
        return analiticsService.getBikesCount();
    }


    @Operation(summary = "Get users count", description = "Returns the total number of users.")
    @ApiResponse(
            responseCode = "200",
            description = "Users count returned",
            content = @Content(schema = @Schema(type = "integer", format = "int64", example = "15"))
    )
    @GetMapping("/analytics/users/count")
    public ResponseEntity<Long> getUsersCount() {
        return analiticsService.getUsersCount();
    }

    @Operation(summary = "Get active rides report", description = "Returns active rides for analytics.")
    @ApiResponse(
            responseCode = "200",
            description = "Active rides returned",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = RideDto.class)))
    )
    @GetMapping("/analytics/rides/active")
    public ResponseEntity<List<RideDto>> getActiveRides() {
        return analiticsService.getActiveRides();
    }

    @Operation(summary = "Get bikes by status", description = "Returns bike count grouped by status.")
    @ApiResponse(
            responseCode = "200",
            description = "Bike status statistics returned",
            content = @Content(schema = @Schema(implementation = Map.class, example = "{\"HIGH\": 10, \"MEDIUM\": 7, \"LOW\": 3}"))
    )
    @GetMapping("/analytics/bikes/by-type")
    public ResponseEntity<Map<Status, Long>> getBikesByType() {
        return analiticsService.getBikesByType();
    }

    @Operation(summary = "Get station workload", description = "Returns bike workload grouped by station.")
    @ApiResponse(
            responseCode = "200",
            description = "Station workload returned",
            content = @Content(schema = @Schema(implementation = Map.class, example = "{\"Central Station\": 8, \"North Station\": 3}"))
    )
    @GetMapping("/analytics/stations/workload")
    public ResponseEntity<Map<String, Long>> getStationsWorkload() {
        return analiticsService.getStationsWorkload();
    }
}
