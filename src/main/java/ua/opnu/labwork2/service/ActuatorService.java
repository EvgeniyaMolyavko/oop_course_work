package ua.opnu.labwork2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.opnu.labwork2.repositories.BikeRepository;
import ua.opnu.labwork2.repositories.RideRepository;
import ua.opnu.labwork2.repositories.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Service

public class ActuatorService {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BikeRepository bikeRepo;

    @Autowired
    private RideRepository rideRepo;

    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> status = new HashMap<>();

        try (Connection conn = dataSource.getConnection()) {
            if (conn.isValid(2)) {
                status.put("status", "UP");
            } else {
                status.put("status", "DOWN");
            }
        } catch (Exception e) {
            status.put("status", "DOWN");
        }

        return ResponseEntity.ok(status);
    }


    public ResponseEntity<Map<String, Long>> getMetrics() {

        Map<String, Long> metrics = new HashMap<>();

        metrics.put("users.count", userRepo.count());
        metrics.put("bikes.count", bikeRepo.count());
        metrics.put("rides.count", rideRepo.count());

        return ResponseEntity.ok(metrics);
    }

    public ResponseEntity<String> getPrometheusMetrics() {

        long users = userRepo.count();
        long bikes = bikeRepo.count();
        long rides = rideRepo.count();

        String metrics =
                "users_count " + users + "\n" +
                        "bikes_count " + bikes + "\n" +
                        "rides_count " + rides + "\n";

        return ResponseEntity.ok(metrics);
    }
}
