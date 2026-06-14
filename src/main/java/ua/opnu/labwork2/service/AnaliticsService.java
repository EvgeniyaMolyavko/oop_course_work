package ua.opnu.labwork2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.opnu.labwork2.dto.RideDto;
import ua.opnu.labwork2.entity.RideEntity;
import ua.opnu.labwork2.enums.Status;
import ua.opnu.labwork2.repositories.BikeRepository;
import ua.opnu.labwork2.repositories.RideRepository;
import ua.opnu.labwork2.repositories.StationRepository;
import ua.opnu.labwork2.repositories.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class AnaliticsService {
    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Long> getBikesCount() {
        return ResponseEntity.ok(bikeRepository.count());
    }


    public ResponseEntity<Long> getUsersCount() {
        return ResponseEntity.ok(userRepository.count());
    }

    public ResponseEntity<List<RideDto>> getActiveRides() {

        List<RideEntity> activeRides = rideRepository.findByEndTimeIsNull();

        List<RideDto> lstDto = activeRides.stream().map( el ->
                new RideDto(el.getId(), el.getStartTime(), el.getEndTime(),el.getDistanceKm(), el.getIdUser(), el.getIdStation(), el.getIdBike())).toList();
        return ResponseEntity.ok(lstDto);
    }

    public ResponseEntity<Map<Status, Long>> getBikesByType() {

        Map<Status, Long> result = bikeRepository.countByStatus()
                .stream()
                .collect(Collectors.toMap(
                        r -> (Status) r[0],
                        r -> (Long) r[1]
                ));

        return ResponseEntity.ok(result);
    }


    public ResponseEntity<Map<String, Long>> getStationsWorkload() {

        Map<String, Long> workload = stationRepository.getStationWorkload()
                .stream()
                .collect(Collectors.toMap(
                        r -> (String) r[0],
                        r -> (Long) r[1]
                ));

        return ResponseEntity.ok(workload);
    }
}
