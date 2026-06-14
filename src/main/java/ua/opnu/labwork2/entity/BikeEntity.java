package ua.opnu.labwork2.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.opnu.labwork2.enums.Status;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ref_bike")
public class BikeEntity {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "model")
    private String model;

    @Getter
    @Setter
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Getter
    @Setter
    @Column(name = "battery_level")
    private Integer batteryLevel;

    @Getter
    @Setter
    @Column(name = "id_station")
    private Integer idStation;

    @Getter
    @Setter
    @Column(name = "id_bike_type")
    Integer idBikeType;
}
