package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Bike type data")
public class BikeTypeDto {
    @Schema(description = "Bike type id", example = "1")
    private Long id;

    @Schema(description = "Bike type name", example = "City bike")
    private String name;

    @Schema(description = "Bike type description", example = "Comfortable bike for city rides")
    private String description;

    public BikeTypeDto(){
    }

    public BikeTypeDto(Long id, String name, String description){
        this.id=id;
        this.name=name;
        this.description=description;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public void setid(Long id){
        this.id = id;
    }
}
