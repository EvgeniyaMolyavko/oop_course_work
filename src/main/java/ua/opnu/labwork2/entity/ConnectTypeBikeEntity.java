package ua.opnu.labwork2.entity;

//import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conect_bike_type",
uniqueConstraints = @UniqueConstraint(columnNames = {"id_bike", "id_bike_type"}))
public class ConnectTypeBikeEntity {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "id_bike")
    private Long idBike;

    @Getter
    @Setter
    @Column(name = "id_bike_type")
    private Long idBikeType;

}
