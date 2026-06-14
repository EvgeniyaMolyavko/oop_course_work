package ua.opnu.labwork2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.opnu.labwork2.entity.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("""
                SELECT u FROM UserEntity u
                WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :q, '%'))
                   OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :q, '%'))
                   OR LOWER(u.email) LIKE LOWER(CONCAT('%', :q, '%'))
            """)
    List<UserEntity> search(@Param("q") String query);

    @Query("""
                SELECT u FROM UserEntity u
                WHERE LOWER(u.firstName) = LOWER(:f)
                   And LOWER(u.lastName) = LOWER(:l)
                   And LOWER(u.email) = LOWER(:e)
            """)
    List<UserEntity> searchAllParams(@Param("f") String firstName,@Param("l") String lastName, @Param("e") String email);


    @Query("""
                SELECT u FROM UserEntity u
                WHERE LOWER(u.firstName) = LOWER(:f)
                   And LOWER(u.lastName) = LOWER(:l)
                   And LOWER(u.email) = LOWER(:e)
                   And u.id <> :id
            """)
    List<UserEntity> searchAllParamsById(@Param("f") String firstName,@Param("l") String lastName, @Param("e") String email, @Param("id") Long id);


    @Query("""
                SELECT count(r) FROM RideEntity r
                WHERE r.idUser = :id
            """)
    Integer countRidesByUser(@Param("id") Long idUser );
}
