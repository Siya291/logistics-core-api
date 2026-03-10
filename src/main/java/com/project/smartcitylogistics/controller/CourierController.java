package com.project.smartcitylogistics.controller;

import com.project.smartcitylogistics.dto.GeoJsonCollection;
import com.project.smartcitylogistics.entity.Courier;
import com.project.smartcitylogistics.repository.CourierRepository;
import com.project.smartcitylogistics.util.GeoJsonMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/couriers")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CourierController {

    private final CourierRepository courierRepository;
    private final GeoJsonMapper geoJsonMapper;
    private final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    @GetMapping("/nearby")
    public ResponseEntity<GeoJsonCollection> getNearbyCouriers(
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam(defaultValue = "5000") double distance) {

        Point searchPoint = factory.createPoint(new Coordinate(longitude, latitude));

        List<Courier> couriers = courierRepository.findNearbyCouriers(searchPoint, distance);

        // Convert List<Courier> to GeoJSON Features
        var features = couriers.stream()
                .map(geoJsonMapper::toFeature)
                .collect(Collectors.toList());

        return ResponseEntity.ok(GeoJsonCollection.of(features));
    }
}