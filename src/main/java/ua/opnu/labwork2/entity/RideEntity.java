package ua.opnu.labwork2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ls_ride")
public class RideEntity {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Getter
    @Setter
    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Getter
    @Setter
    @Column(name = "distance_km")
    private Double distanceKm;

    @Getter
    @Setter
    @Column(name = "id_user")
    private Integer idUser;

    @Getter
    @Setter
    @Column(name = "id_station")
    private Integer idStation;

    @Getter
    @Setter
    @Column(name = "id_bike")
    private Integer idBike;

}
