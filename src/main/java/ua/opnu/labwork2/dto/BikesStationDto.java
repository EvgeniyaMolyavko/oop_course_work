package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import ua.opnu.labwork2.entity.StationEntity;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Station with assigned bikes")
public class BikesStationDto {
    @Schema(description = "Station data", implementation = StationEntity.class)
    private StationEntity station;

    @ArraySchema(schema = @Schema(implementation = BikeDto.class))
    private List<BikeDto> bikeDtos = new ArrayList<>();

    public BikesStationDto(StationEntity station, List<BikeDto> bikeDtos) {
        this.station = station;
        this.bikeDtos = bikeDtos;
    }
    public StationEntity getStation(){
        return station;
    }
    public List<BikeDto> getBikes() {
        return bikeDtos;
    }
}
