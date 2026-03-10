package com.project.smartcitylogistics.controller;

import com.project.smartcitylogistics.dto.CourierDTO;
import com.project.smartcitylogistics.repository.CourierRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/couriers")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CourierController {

    private final CourierRepository courierRepository;
    private final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    @GetMapping("/nearby")
    public List<CourierDTO> getNearby(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "5000") double radius) {

        Point searchPoint = factory.createPoint(new Coordinate(lng, lat));

        return courierRepository.findNearbyCouriers(searchPoint, radius)
                .stream()
                .map(c -> new CourierDTO(
                        c.getId(),
                        c.getName(),
                        c.getCurrentLocation().getY(),
                        c.getCurrentLocation().getX(),
                        c.getStatus()))
                .toList();
    }
}

