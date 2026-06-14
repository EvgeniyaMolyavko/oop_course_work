package ua.opnu.labwork2.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.opnu.labwork2.dto.BikeDto;
import ua.opnu.labwork2.dto.BikesStationDto;
import ua.opnu.labwork2.dto.StationDto;
import ua.opnu.labwork2.entity.BikeEntity;
import ua.opnu.labwork2.entity.StationEntity;
import ua.opnu.labwork2.exceptions.ConflictOperationException;
import ua.opnu.labwork2.exceptions.DuplicateResourceException;
import ua.opnu.labwork2.exceptions.ResourceNotFoundException;
import ua.opnu.labwork2.repositories.BikeRepository;
import ua.opnu.labwork2.repositories.StationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StationService {
    private final BikeRepository repoBike;
    private final StationRepository repo;

    public StationService(BikeRepository repoBike, StationRepository repo) {
        this.repoBike = repoBike;
        this.repo = repo;
    }

    public ResponseEntity<BikesStationDto> getBikesByStationId(Long id) {
        Optional<StationEntity> opt = repo.findById(id);
        if(opt.isEmpty()){
            throw new ResourceNotFoundException("Station", id);
        }
        StationEntity station = opt.get();
        ArrayList<BikeEntity> bikeList =  (ArrayList<BikeEntity>) repoBike.findByIdStation(station.getId());
        List<BikeDto> lstBikeDto = bikeList.stream().map(el ->
                new BikeDto(el.getId(), el.getModel(), el.getStatus(), el.getBatteryLevel(), el.getIdStation(), el.getIdBikeType())).toList();

        return ResponseEntity.ok(new BikesStationDto(
                new StationEntity(station.getId(), station.getName(), station.getCity(), station.getCapacity()), lstBikeDto
        ));
    }

    public ResponseEntity<List<StationDto>> getStationType() {
        List<StationEntity> lstEnt = repo.findAll();
        List<StationDto> lstDto = lstEnt.stream().map(el -> new StationDto(el.getId(), el.getName(), el.getCity(), el.getCapacity())).toList();
        return ResponseEntity.ok(lstDto);
    }

    public ResponseEntity<StationDto> getStationById(Long id) {
        Optional<StationEntity> opt = repo.findById(id);
        if(opt.isEmpty()){
            throw new ResourceNotFoundException("Station", id);
        }
        return ResponseEntity.ok(new StationDto(opt.get().getId(), opt.get().getName(), opt.get().getCity(), opt.get().getCapacity()));
    }

    public ResponseEntity<StationDto> addStation(StationDto body) {
        StationEntity stEntity = new StationEntity();
        stEntity.setName(body.name());
        stEntity.setCity(body.city());
        stEntity.setCapacity(body.capacity());

        List<StationEntity> lstEnt = repo.searchAllParams(body.name(), body.city()  );
        if(lstEnt.size()>0) {
            throw new DuplicateResourceException("Station", String.format("%s %s", body.name(), body.city()));
        }

        StationEntity savedStation = repo.save(stEntity);
        StationDto stDto = new StationDto(savedStation.getId(), savedStation.getName(), savedStation.getCity(), savedStation.getCapacity());
        return ResponseEntity.status(HttpStatus.CREATED).body(stDto);
    }

    public ResponseEntity<StationDto> putStationById(StationDto body, Long id) {
        Optional<StationEntity> opt = repo.findById(id);
        if(opt.isEmpty()){
            throw new ResourceNotFoundException("Station", id);
        }
        StationEntity station = opt.get();
        if(station.getName() != null){
            station.setName(body.name());
        }
        if(station.getCity() != null) {
            station.setCity(body.city());
        }
        if(station.getCapacity() != null) {
            station.setCapacity(body.capacity());
        }

        List<StationEntity> lstEnt = repo.searchAllParamsById(body.name(), body.city(), id);
        if(lstEnt.size()>0) {
            throw new DuplicateResourceException("Station", String.format("%s %s", body.name(), body.city()));
        }

        StationEntity savedSt = repo.save(station);
        StationDto stDto = new StationDto(savedSt.getId(), savedSt.getName(), savedSt.getCity(), savedSt.getCapacity());

        return ResponseEntity.ok(stDto);
    }

    public ResponseEntity<Void> deleteStationById(Long id) {
        Optional<StationEntity> opt = repo.findById(id);
        if(opt.isEmpty()){
            throw new ResourceNotFoundException("Station", id);
        }

        int count = repo.countStaionBikes(id);
        if (count > 0 ) {
            throw new ConflictOperationException("Bike", id, "are in use and contain bikes");
        }

        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
