package ua.opnu.labwork2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import ua.opnu.labwork2.dto.BikeDto;
import ua.opnu.labwork2.dto.StationDto;
import ua.opnu.labwork2.dto.UserDto;
import ua.opnu.labwork2.entity.BikeEntity;
import ua.opnu.labwork2.entity.StationEntity;
import ua.opnu.labwork2.entity.UserEntity;
import ua.opnu.labwork2.repositories.BikeRepository;
import ua.opnu.labwork2.repositories.StationRepository;
import ua.opnu.labwork2.repositories.UserRepository;

import java.util.List;

@Service

public class SearchService {
    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StationRepository stationRepository;
    public ResponseEntity<List<BikeDto>> searchBikes(@RequestParam String query) {
        List<BikeEntity> result =
                bikeRepository.findByModelContainingIgnoreCase(query);
        return ResponseEntity.ok(result.stream().map(el -> new BikeDto(el.getId(), el.getModel(), el.getStatus(), el.getBatteryLevel(), el.getIdStation(), el.getIdBikeType())).toList());
    }

    public ResponseEntity<List<UserDto>> searchUsers(@RequestParam String query) {
        List<UserEntity> lstEnt = userRepository.search(query);
        List<UserDto> lstUsDto = lstEnt.stream().map(el -> new UserDto(el.getId(), el.getFirstName(), el.getLastName(), el.getEmail(), el.getPhone())).toList();
        return ResponseEntity.ok(lstUsDto);
    }

    public ResponseEntity<List<StationDto>> searchStations(@RequestParam String query) {
        List<StationEntity> lstStEnt = stationRepository.search(query);
        List<StationDto> lstStDto = lstStEnt.stream().map(el ->
                new StationDto(el.getId(), el.getName(), el.getCity(), el.getCapacity())).toList();
        return ResponseEntity.ok(lstStDto);
    }
}
