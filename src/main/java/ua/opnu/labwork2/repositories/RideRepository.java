package ua.opnu.labwork2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.labwork2.entity.RideEntity;

import java.util.List;

public interface RideRepository extends JpaRepository<RideEntity, Long> {
    List<RideEntity> findByIdUser(Long id_user);
    List<RideEntity> findByEndTimeIsNull();

}
