package ua.opnu.labwork2.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.opnu.labwork2.dto.RideDto;
import ua.opnu.labwork2.dto.UserDto;
import ua.opnu.labwork2.dto.UserRidesDto;
import ua.opnu.labwork2.entity.RideEntity;
import ua.opnu.labwork2.entity.UserEntity;
import ua.opnu.labwork2.exceptions.ConflictOperationException;
import ua.opnu.labwork2.exceptions.DuplicateResourceException;
import ua.opnu.labwork2.exceptions.ResourceNotFoundException;
import ua.opnu.labwork2.repositories.RideRepository;
import ua.opnu.labwork2.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;
    private final RideRepository repoRide;

    public UserService(UserRepository repo, RideRepository repoRide) {
        this.repo = repo;
        this.repoRide = repoRide;
    }

    public ResponseEntity<List<UserDto>>  getUsers() {
        List<UserEntity> listEntity = repo.findAll();
        List<UserDto> listDto = listEntity.stream().map(el ->
            new UserDto(el.getId(), el.getFirstName(), el.getLastName(), el.getEmail(), el.getPhone())).toList();
        return ResponseEntity.ok(listDto);
    }

    public ResponseEntity<UserDto> getUserById(Long id) {
        Optional<UserEntity> opt = repo.findById(id);
        if(opt.isEmpty()){
            throw new ResourceNotFoundException("User ", id);
        }
        return ResponseEntity.ok(new UserDto(opt.get().getId(), opt.get().getFirstName(), opt.get().getLastName(), opt.get().getEmail(), opt.get().getPhone()));
    }

    public ResponseEntity<UserRidesDto> getRidesByUserId(Long id) {
        Optional<UserEntity> opt = repo.findById(id);
        if(opt.isEmpty()){
            throw new ResourceNotFoundException("User ", id);
        }
        UserEntity user = opt.get();
        ArrayList<RideEntity> lstRideEntity = (ArrayList<RideEntity>) repoRide.findByIdUser(user.getId());
         List<RideDto> lstRide = lstRideEntity.stream().map(el ->
                 new RideDto(el.getId(), el.getStartTime(), el.getEndTime(), el.getDistanceKm(), el.getIdUser(), el.getIdBike(), el.getIdStation())).toList();

        return ResponseEntity.ok(new UserRidesDto(
                new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone()), lstRide));

    }

    public ResponseEntity<UserDto> addUser(UserDto body) {
        List<UserEntity> lstEnt = repo.searchAllParams(body.firstName(), body.lastName(), body.email());
        if(lstEnt.size()>0) {
            throw new DuplicateResourceException("User", String.format("%s %s %s", body.firstName(), body.lastName(), body.email()));
        }
        UserEntity entity = new UserEntity();
        entity.setFirstName(body.firstName());
        entity.setLastName(body.lastName());
        entity.setEmail(body.email());
        entity.setPhone(body.phone());

        UserEntity savedUser = repo.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserDto(savedUser.getId(), savedUser.getFirstName(), savedUser.getLastName(), savedUser.getEmail(), savedUser.getPhone()));
    }

    public ResponseEntity<UserDto> updateUser(UserDto body, Long id) {
        Optional<UserEntity> opt = repo.findById(id);

        if(opt.isEmpty()){
            throw new ResourceNotFoundException("User ", id);
        }

        UserEntity user = opt.get();

        if(body.lastName() != null) {
            user.setLastName(body.lastName());
        }
        if(body.firstName() != null) {
            user.setFirstName(body.firstName());
        }
        if(body.email() != null) {
            user.setEmail(body.email());
        }
        if(body.phone() != null) {
            user.setPhone(body.phone());
        }

        List<UserEntity> lstEnt = repo.searchAllParamsById(body.firstName(), body.lastName(), body.email(), id);
        if(lstEnt.size()>0) {
            throw new DuplicateResourceException("User", String.format("%s %s %s", body.firstName(), body.lastName(), body.email()));
        }
        UserEntity updated = repo.save(user);
        return ResponseEntity.ok(new UserDto(updated.getId(), updated.getFirstName(), updated.getLastName(), updated.getEmail(), updated.getPhone()));
    }

    public ResponseEntity<Void> deleteUserById(Long id) {
        Optional<UserEntity> opt = repo.findById(id);

        if(opt.isEmpty()){
            throw new ResourceNotFoundException("User ", id);
        }

        int count = repo.countRidesByUser(id);
        if (count > 0 ) {
            throw new ConflictOperationException("User", id, "are in use");
        }

        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
