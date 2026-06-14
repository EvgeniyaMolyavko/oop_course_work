package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "User with ride history")
public class UserRidesDto {
    @Schema(description = "User data", implementation = UserDto.class)
    private UserDto user;

    @ArraySchema(schema = @Schema(implementation = RideDto.class))
    private List<RideDto> rides = new ArrayList<>();

    public UserRidesDto(UserDto user, List<RideDto> rides) {
        this.user=user;
        this.rides=rides;
    }

    public UserDto getUser(){
        return user;
    }
    public List<RideDto> getRides(){
        return rides;
    }
}
