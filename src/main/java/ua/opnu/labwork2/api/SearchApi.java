package ua.opnu.labwork2.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.opnu.labwork2.dto.BikeDto;
import ua.opnu.labwork2.dto.StationDto;
import ua.opnu.labwork2.dto.UserDto;
import ua.opnu.labwork2.repositories.BikeRepository;
import ua.opnu.labwork2.repositories.StationRepository;
import ua.opnu.labwork2.repositories.UserRepository;
import ua.opnu.labwork2.service.SearchService;

import java.util.List;

@RestController
@Tag(name = "Search", description = "Search endpoints for bikes, users and stations")

public class SearchApi {
    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired private SearchService searchService;

    @Operation(summary = "Search bikes", description = "Searches bikes by model fragment.")
    @ApiResponse(
            responseCode = "200",
            description = "Bike search results returned",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BikeDto.class)))
    )
    @GetMapping("/search/bikes")
    public ResponseEntity<List<BikeDto>> searchBikes(
            @Parameter(description = "Search text", example = "trek")
            @RequestParam String query
    ) {
        return searchService.searchBikes(query);
    }

    @Operation(summary = "Search users", description = "Searches users by supported text fields.")
    @ApiResponse(
            responseCode = "200",
            description = "User search results returned",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))
    )
    @GetMapping("/search/users")
    public ResponseEntity<List<UserDto>> searchUsers(
            @Parameter(description = "Search text", example = "john")
            @RequestParam String query
    ) {
        return searchService.searchUsers(query);
    }

    @Operation(summary = "Search stations", description = "Searches stations by supported text fields.")
    @ApiResponse(
            responseCode = "200",
            description = "Station search results returned",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StationDto.class)))
    )
    @GetMapping("/search/stations")
    public ResponseEntity<List<StationDto>> searchStations(
            @Parameter(description = "Search text", example = "center")
            @RequestParam String query
    ) {
        return searchService.searchStations(query);
    }
}
