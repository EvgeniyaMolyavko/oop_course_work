package ua.opnu.labwork2.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Bike Rental API",
                version = "1.0.0",
                description = "OpenAPI documentation for bike rental lab endpoints.",
                contact = @Contact(name = "OPNU"),
                license = @License(name = "Educational use")
        )
)
public class OpenApiConfig {
}
