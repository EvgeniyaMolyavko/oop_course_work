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
import ua.opnu.labwork2.dto.UserDto;
import ua.opnu.labwork2.dto.UserRidesDto;
import ua.opnu.labwork2.exceptions.ApiErrorResponseDto;
import ua.opnu.labwork2.service.UserService;

import java.util.List;

@RestController
@Tag(name = "Users", description = "User management and ride history endpoints")

public class UserApi {
    @Autowired private UserService userService;

    @Operation(summary = "Get all users", description = "Returns the list of all users.")
    @ApiResponse(
            responseCode = "200",
            description = "Users returned",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))
    )
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>>  getUsers() {
        return userService.getUsers();
    }

    @Operation(summary = "Get user by id", description = "Returns one user by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User returned", content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(
            @Parameter(description = "User id", example = "1")
            @PathVariable Long id
    ) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Get user rides", description = "Returns a user with all their rides.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User rides returned", content = @Content(schema = @Schema(implementation = UserRidesDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @GetMapping("/users/{id}/rides")
    public ResponseEntity<UserRidesDto> getRidesByUserId(
            @Parameter(description = "User id", example = "1")
            @PathVariable Long id
    ) {
        return userService.getRidesByUserId(id);
    }

    @Operation(summary = "Create user", description = "Creates a new user.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created", content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Database operation failed", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @PostMapping("/users")
    public ResponseEntity<UserDto> addUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            )
            @Valid  @RequestBody UserDto body
    ) {
        return userService.addUser(body);
    }

    @Operation(summary = "Update user", description = "Updates user fields by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated", content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User data to update",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            )
            @Valid @RequestBody UserDto body,
            @Parameter(description = "User id", example = "1")
            @PathVariable Long id
    ) {
        return userService.updateUser(body, id);
    }

    @Operation(summary = "Delete user", description = "Deletes a user by id if they do not have rides.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted", content = @Content),
            @ApiResponse(responseCode = "400", description = "User cannot be deleted", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiErrorResponseDto.class)))
    })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUserById(
            @Parameter(description = "User id", example = "1")
            @PathVariable Long id
    ) {
        return userService.deleteUserById(id);

    }
}
