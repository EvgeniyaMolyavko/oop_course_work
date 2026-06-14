package ua.opnu.labwork2.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ua.opnu.labwork2.dto.BikeTypeDto;
import ua.opnu.labwork2.entity.BikeEntity;
import ua.opnu.labwork2.entity.BikeTypeEntity;
import ua.opnu.labwork2.entity.ConnectTypeBikeEntity;
import ua.opnu.labwork2.exceptions.ConflictOperationException;
import ua.opnu.labwork2.exceptions.ResourceNotFoundException;
import ua.opnu.labwork2.repositories.BikeRepository;
import ua.opnu.labwork2.repositories.BikeTypeRepository;
import ua.opnu.labwork2.repositories.ConnectTypeBikeRepository;

import java.util.List;
import java.util.Optional;

@Service

public class BikeTypesService {
    private final BikeTypeRepository repo;
    private final BikeRepository repoBike;
    private final ConnectTypeBikeRepository repoCon;

    public BikeTypesService(BikeTypeRepository repo, BikeRepository repoBike, ConnectTypeBikeRepository repoCon) {
        this.repo = repo;
        this.repoBike = repoBike;
        this.repoCon = repoCon;
    }

    public ResponseEntity<List<BikeTypeDto>> getBikeType() {
        List<BikeTypeEntity> bTypeLst = repo.findAll();
        List<BikeTypeDto> dtoList = bTypeLst.stream().map(el -> new BikeTypeDto(el.getId(), el.getName(), el.getDescription())).toList();
        return ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<BikeTypeDto> getBikeTypeById(@PathVariable Long id) {
        Optional<BikeTypeEntity> opt = repo.findById(id);
        if(opt.isEmpty()){
            throw new ResourceNotFoundException("Bike type", id);
        }
        return ResponseEntity.ok(new BikeTypeDto(opt.get().getId(), opt.get().getName(), opt.get().getDescription()));
    }

    public ResponseEntity<BikeTypeDto> addBikeType(@RequestBody BikeTypeDto body) {
        BikeTypeEntity bikeTypeEnt = new BikeTypeEntity();
        bikeTypeEnt.setName(body.getName());
        bikeTypeEnt.setDescription(body.getDescription());
        BikeTypeEntity savedBikeType = repo.save(bikeTypeEnt);
        BikeTypeDto savedDto = new BikeTypeDto(savedBikeType.getId(), savedBikeType.getName(), savedBikeType.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    public ResponseEntity<?> putBikeTypeById(@PathVariable Long id, @PathVariable Long typeId) {
        Optional<BikeEntity> optB = repoBike.findById(id);
        if(optB.isEmpty()){
            throw new ResourceNotFoundException("Bike", id);
        }

        Optional<BikeTypeEntity> optT = repo.findById(typeId);
        if(optT.isEmpty()){
            throw new ResourceNotFoundException("Bike type", id);
        }
        ConnectTypeBikeEntity foundBike = new ConnectTypeBikeEntity();
        foundBike.setIdBike(id);
        foundBike.setIdBikeType(typeId);
        try {
            repoCon.save(foundBike);
            return ResponseEntity.status(HttpStatus.CREATED).body(foundBike);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Error: relationship already exists or cannot be created");
        }

    }

    public ResponseEntity<Void> deleteBikePropsById(@PathVariable Long id) {
        Optional<BikeTypeEntity> opt = repo.findById(id);
        if(opt.isEmpty()){
            throw new ResourceNotFoundException("Bike type", id);
        }

        int count = repoCon.countBikeTypeRelation(id);
        if (count > 0 ) {
            throw new ConflictOperationException("Bike type", id, "are in use");
        }

        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> deleteBikePropsByIdByTypeId(@PathVariable Long id, @PathVariable Long typeId) {

        Optional<ConnectTypeBikeEntity> opt = repoCon.findByIdBikeAndIdBikeType(id, typeId);

        if(opt.isEmpty()){
            throw new ResourceNotFoundException("Bike", id, "Byke type", typeId);
        }


        repoCon.delete(opt.get());

        return ResponseEntity.noContent().build();
    }



}
