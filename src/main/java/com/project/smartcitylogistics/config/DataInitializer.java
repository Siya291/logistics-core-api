package com.project.smartcitylogistics.config;

import com.project.smartcitylogistics.entity.Courier;
import com.project.smartcitylogistics.repository.CourierRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CourierRepository courierRepository;
    // SRID 4326 is the standard for GPS (WGS84)
    private final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    public DataInitializer(CourierRepository repository) {
        this.courierRepository = repository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        TenantContext.setTenantId("vendor_delivery_co");

        Courier courier = new Courier();
        courier.setName("Flash Thompson");
        courier.setStatus("ACTIVE");
        // Coordinates for a test location (Longitude, Latitude)
        courier.setCurrentLocation(factory.createPoint(new Coordinate(28.2293, -25.7479)));

        courierRepository.save(courier);
        System.out.println(">>> Smart City Courier Saved with GPS Coordinates!");

        // Defining a point
        GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
        Point searchPoint = factory.createPoint(new Coordinate(28.22, -25.75));

        // Search within 5km (5000 meters)
        List<Courier> nearby = courierRepository.findNearbyCouriers(searchPoint, 5000);

        System.out.println(">>> Found " + nearby.size() + " couriers nearby!");
        nearby.forEach(c -> System.out.println(" - " + c.getName()));
    }
}