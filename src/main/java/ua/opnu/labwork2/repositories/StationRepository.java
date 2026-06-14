package ua.opnu.labwork2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.opnu.labwork2.entity.StationEntity;

import java.util.List;

public interface StationRepository extends JpaRepository<StationEntity, Long> {
    @Query(value = "SELECT s.name, COUNT(b.id) FROM ref_station s " +
            "JOIN ref_bike b ON b.id_station = s.id GROUP BY s.name",
            nativeQuery = true)
    List<Object[]> getStationWorkload();

    @Query("""
                SELECT s FROM StationEntity s
                WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :q, '%'))
                   OR LOWER(s.city) LIKE LOWER(CONCAT('%', :q, '%'))
            """)
    List<StationEntity> search(@Param("q") String query);


    @Query("""
                SELECT s FROM StationEntity s
                WHERE LOWER(s.name) = LOWER(:n)
                   AND LOWER(s.city) = LOWER(:c)
            """)
    List<StationEntity> searchAllParams(@Param("n") String name, @Param("c") String city);


    @Query("""
                SELECT s FROM StationEntity s
                WHERE LOWER(s.name) = LOWER(:n)
                   AND LOWER(s.city) = LOWER(:c)
                   AND s.id <> :id
            """)
    List<StationEntity> searchAllParamsById(@Param("n") String name, @Param("c") String city, @Param("id") Long id);


    @Query("""
                SELECT count(b) FROM BikeEntity b
                WHERE b.idStation = :id
            """)
    Integer countStaionBikes(@Param("id") Long idStation );
}