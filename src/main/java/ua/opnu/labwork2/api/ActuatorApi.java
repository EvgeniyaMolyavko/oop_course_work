package ua.opnu.labwork2.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.opnu.labwork2.repositories.BikeRepository;
import ua.opnu.labwork2.repositories.RideRepository;
import ua.opnu.labwork2.repositories.UserRepository;
import ua.opnu.labwork2.service.ActuatorService;

import javax.sql.DataSource;
import java.util.Map;

@RestController
@Tag(name = "Actuator", description = "Application health and metrics endpoints")

public class ActuatorApi {
    @Autowired
    private ActuatorService actuatorService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BikeRepository bikeRepo;

    @Autowired
    private RideRepository rideRepo;


    @Operation(summary = "Get application health", description = "Checks database connectivity and returns application health status.")
    @ApiResponse(
            responseCode = "200",
            description = "Health status returned",
            content = @Content(schema = @Schema(implementation = Map.class, example = "{\"status\": \"UP\"}"))
    )
    @GetMapping("/actuator/health")
    public ResponseEntity<Map<String, String>> health() {
       return actuatorService.health();
    }

    @Operation(summary = "Get application metrics", description = "Returns basic counters for users, bikes and rides.")
    @ApiResponse(
            responseCode = "200",
            description = "Metrics returned",
            content = @Content(schema = @Schema(implementation = Map.class, example = "{\"users.count\": 10, \"bikes.count\": 25, \"rides.count\": 60}"))
    )
    @GetMapping("/actuator/metrics")
    public ResponseEntity<Map<String, Long>> getMetrics() {
        return actuatorService.getMetrics();
    }

    @Operation(summary = "Get Prometheus metrics", description = "Returns basic counters in Prometheus text format.")
    @ApiResponse(
            responseCode = "200",
            description = "Prometheus metrics returned",
            content = @Content(mediaType = "text/plain", schema = @Schema(type = "string", example = "users_count 10\nbikes_count 25\nrides_count 60\n"))
    )
    @GetMapping(value = "/actuator/prometheus", produces = "text/plain")
    public ResponseEntity<String> getPrometheusMetrics() {
        return actuatorService.getPrometheusMetrics();
    }
}
