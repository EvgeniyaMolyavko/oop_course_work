package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "User data")
public record UserDto(

        @Schema(description = "User id", example = "1")
        Long id,

        @Schema(description = "User first name", example = "John")
        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 50, message = "First name must contain 2-50 characters")
        String firstName,

        @Schema(description = "User last name", example = "Smith")
        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 50, message = "Last name must contain 2-50 characters")
        String lastName,

        @Schema(description = "User email", example = "john.smith@example.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Email is invalid")
        String email,

        @Schema(description = "User phone number", example = "+380501234567")
        @NotBlank(message = "Phone is required")
        @Pattern(
                regexp = "^\\+?[0-9]{10,20}$",
                message = "Phone number is invalid"
        )
        String phone

) {
}
