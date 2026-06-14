package ua.opnu.labwork2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.labwork2.entity.BikeTypeEntity;

public interface BikeTypeRepository extends JpaRepository<BikeTypeEntity, Long> {
}
