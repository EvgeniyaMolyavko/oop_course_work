package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Bike and bike type pair")
public class BikePropsDto {

    @Schema(description = "Bike data", implementation = BikeDto.class)
    private BikeDto bikeDto;

    @Schema(description = "Bike type data", implementation = BikeTypeDto.class)
    private BikeTypeDto bikeTypeDto;

    public BikePropsDto(BikeDto bikeDto, BikeTypeDto bikeTypeDto) {
        this.bikeDto = bikeDto;
        this.bikeTypeDto = bikeTypeDto;
    }
    public BikeDto getBike(){
        return bikeDto;
    }
    public BikeTypeDto getBikeType(){
        return bikeTypeDto;
    }
}
