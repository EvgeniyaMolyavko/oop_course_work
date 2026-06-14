package ua.opnu.labwork2.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Bike battery status", allowableValues = {"LOW", "MEDIUM", "HIGH"})
public enum Status {
    LOW, MEDIUM, HIGH
}
