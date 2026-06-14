package ua.opnu.labwork2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.opnu.labwork2.entity.ConnectTypeBikeEntity;

import java.util.Optional;

public interface ConnectTypeBikeRepository extends JpaRepository<ConnectTypeBikeEntity, Long> {
    Optional<ConnectTypeBikeEntity> findByIdBikeAndIdBikeType(Long idBike, Long idBikeType);

    @Query("""
                SELECT count(b) FROM ConnectTypeBikeEntity b
                WHERE b.idBikeType = :id
            """)
    Integer countBikeTypeRelation(@Param("id") Long idBikeType);

}
