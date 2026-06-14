package ua.opnu.labwork2.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ua.opnu.labwork2.dto.RideDto;
import ua.opnu.labwork2.entity.RideEntity;
import ua.opnu.labwork2.exceptions.ResourceNotFoundException;
import ua.opnu.labwork2.repositories.RideRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RidesService {
    private final RideRepository repo;

    public RidesService(RideRepository repo) {
        this.repo = repo;
    }

    public ResponseEntity<List<RideDto>> getRide() {
        List<RideEntity> lstEnt = repo.findAll();
        List<RideDto> lstDto = lstEnt.stream().map(el -> new RideDto(el.getId(), el.getStartTime(), el.getEndTime(), el.getDistanceKm(), el.getIdUser(), el.getIdStation(), el.getIdBike())).toList();
        return ResponseEntity.ok(lstDto);
    }

    public ResponseEntity<RideDto> getRidesById(@PathVariable Long id) {
        Optional<RideEntity> opt = repo.findById(id);
        if(opt.isEmpty()){
            throw new ResourceNotFoundException("Rides ", id);
        }
        return ResponseEntity.ok(new RideDto(opt.get().getId(), opt.get().getStartTime(), opt.get().getEndTime(), opt.get().getDistanceKm(), opt.get().getIdUser(), opt.get().getIdStation(), opt.get().getIdBike()));
    }

    public ResponseEntity<List<RideDto>> getactiveRide() {
       List<RideEntity> rides = repo.findAll();
       List<RideEntity> active = new ArrayList<>();

       for (RideEntity r : rides) {
           if (r.getEndTime() == null) {
               active.add(r);
           }
       }

        List<RideDto> activeDto = active.stream().map(el -> new RideDto(el.getId(), el.getStartTime(), el.getEndTime(), el.getDistanceKm(), el.getIdUser(), el.getIdStation(), el.getIdBike())).toList();
       return ResponseEntity.ok(activeDto);
    }


        public ResponseEntity<RideDto> addRide(@RequestBody RideDto body) {

        RideEntity rEntity = new RideEntity();
        rEntity.setStartTime(body.startTime());
        rEntity.setEndTime(body.endTime());
        rEntity.setDistanceKm(body.distanceKm());

        rEntity.setIdUser(body.idUser());
        rEntity.setIdBike(body.idBike());
        rEntity.setIdStation(body.idStation());

        RideEntity savedRide = repo.save(rEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RideDto(savedRide.getId(), savedRide.getStartTime(), savedRide.getEndTime(), savedRide.getDistanceKm(), savedRide.getIdUser(), savedRide.getIdStation(), savedRide.getIdBike()));
    }


    public ResponseEntity<RideDto> putFinishedRidesById(@RequestBody RideDto body, @PathVariable Long id) {
        Optional<RideEntity> opt = repo.findById(id);

        if(opt.isEmpty()){
            throw new ResourceNotFoundException("Ride", id);
        }

        RideEntity rideFin = opt.get();
        rideFin.setEndTime(LocalDateTime.now());

        RideEntity updated = repo.save(rideFin);
        return ResponseEntity.ok(new RideDto(updated.getId(), updated.getStartTime(), updated.getEndTime(), updated.getDistanceKm(), updated.getIdUser(), updated.getIdStation(), updated.getIdBike()));
    }


    public ResponseEntity<Void> deleteRideById(@PathVariable Long id) {
        Optional<RideEntity> opt = repo.findById(id);

        if(opt.isEmpty()){
            throw new ResourceNotFoundException("Ride", id);
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

