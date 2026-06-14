package ua.opnu.labwork2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.opnu.labwork2.entity.BikeEntity;

import java.util.List;

public interface BikeRepository extends JpaRepository<BikeEntity, Long> {
    List<BikeEntity> findByIdStation(Long id_station);

    @Query("SELECT b.status, COUNT(b) FROM BikeEntity b GROUP BY b.status")
    List<Object[]> countByStatus();

    List<BikeEntity> findByModelContainingIgnoreCase(String model);

    @Query("""
                SELECT count(b) FROM BikeEntity b
                WHERE b.idStation = :id
            """)
    Integer countBikesAtStaion(@Param("id") Long idStation );

}
