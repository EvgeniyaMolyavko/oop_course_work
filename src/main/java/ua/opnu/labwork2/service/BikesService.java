package ua.opnu.labwork2.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ua.opnu.labwork2.dto.BikeDto;
import ua.opnu.labwork2.dto.BikesStationDto;
import ua.opnu.labwork2.entity.BikeEntity;
import ua.opnu.labwork2.entity.StationEntity;
import ua.opnu.labwork2.exceptions.ConflictOperationException;
import ua.opnu.labwork2.exceptions.ResourceNotFoundException;
import ua.opnu.labwork2.repositories.BikeRepository;
import ua.opnu.labwork2.repositories.StationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BikesService {
    private final BikeRepository repo;
    private final StationRepository repoSt;

    public BikesService(BikeRepository repo, StationRepository repoSt){
        this.repo = repo;
        this.repoSt = repoSt;
    }

    public ResponseEntity<List<BikeDto>> getBike() {
        List<BikeEntity> lstEnt = repo.findAll();
        List<BikeDto> lstDto = lstEnt.stream().map(el -> new BikeDto(el.getId(), el.getModel(), el.getStatus(), el.getBatteryLevel(), el.getIdStation(), el.getIdBikeType())).toList();
        return ResponseEntity.ok(lstDto);
    }

    public ResponseEntity<BikeDto> getBikeById(@PathVariable Long id) {
        Optional<BikeEntity> opt = repo.findById(id);
        if(opt.isEmpty()){
            throw new ResourceNotFoundException("Station", id);
        }
        return ResponseEntity.ok(
                new BikeDto(opt.get().getId(),
                        opt.get().getModel(), opt.get().getStatus(), opt.get().getBatteryLevel(),
                        opt.get().getIdStation(), opt.get().getIdBikeType()));
    }

    public ResponseEntity<BikesStationDto> getBikeByStationId(@PathVariable Long stationId) {
        Optional<StationEntity> optSt = repoSt.findById(stationId);
        if(optSt.isEmpty()){
            throw new ResourceNotFoundException("Station", stationId);
        }
        StationEntity station = optSt.get();
        ArrayList<BikeEntity> bikeList =  (ArrayList<BikeEntity>) repo.findByIdStation(station.getId());
        List<BikeDto> lstBikeDto = bikeList.stream().map(el ->
                new BikeDto(el.getId(), el.getModel(), el.getStatus(), el.getBatteryLevel(), el.getIdStation(), el.getIdBikeType())).toList();

        return ResponseEntity.ok(new BikesStationDto(
                new StationEntity(station.getId(), station.getName(), station.getCity(), station.getCapacity()), lstBikeDto
        ));
    }

    public ResponseEntity<BikeDto> addBike(@RequestBody BikeDto body) {
        BikeEntity bEntity = new BikeEntity();
        bEntity.setModel(body.model());
        bEntity.setStatus(body.status());
        bEntity.setBatteryLevel(body.batteryLevel());
        bEntity.setIdStation(body.idStation());

        BikeEntity savedEnt = repo.save(bEntity);
        BikeDto bDto = new BikeDto(savedEnt.getId(), savedEnt.getModel(), savedEnt.getStatus(), savedEnt.getBatteryLevel(), savedEnt.getIdStation(), savedEnt.getIdBikeType());
        return ResponseEntity.status(HttpStatus.CREATED).body(bDto);
    }

    public ResponseEntity<BikeDto> updateBike(@RequestBody BikeDto body, @PathVariable Long id) {
        Optional<BikeEntity> opt = repo.findById(id);
        if(opt.isEmpty()){
            throw new ResourceNotFoundException("Station", id);
        }
        BikeEntity bike = opt.get();
        if(body.model() != null){
            bike.setModel(body.model());
        }
        if(body.status() != null){
            bike.setStatus(body.status());
        }
        if(body.batteryLevel() != null){
            bike.setBatteryLevel(body.batteryLevel());
        }
        if(body.idStation() != null){
            bike.setIdStation(body.idStation());
        }
        if(body.idBikeType() != null){
            bike.setIdBikeType(body.idBikeType());
        }
        BikeEntity savedBike = repo.save(bike);
        return ResponseEntity.ok(new BikeDto(savedBike.getId(), savedBike.getModel(), savedBike.getStatus(), savedBike.getBatteryLevel(), savedBike.getIdStation(), savedBike.getIdBikeType()));
    }

    public ResponseEntity<Void> deleteBikeById(@PathVariable Long id) {
        Optional<BikeEntity> opt = repo.findById(id);
        if(opt.isEmpty()){
            throw new ResourceNotFoundException("Station", id);
        }

        int count = repo.countBikesAtStaion(id);
        if (count > 0 ) {
            throw new ConflictOperationException("Bike", id, "are in use and are placed at the station");
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
