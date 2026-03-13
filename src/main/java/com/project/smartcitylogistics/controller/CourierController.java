package com.project.smartcitylogistics.controller;

import com.project.smartcitylogistics.dto.CourierDTO;
import com.project.smartcitylogistics.dto.GeoJsonCollection;
import com.project.smartcitylogistics.dto.StatusUpdateDTO;
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

        var features = couriers.stream()
                .map(geoJsonMapper::toFeature)
                .collect(Collectors.toList());

        return ResponseEntity.ok(GeoJsonCollection.of(features));
    }

    @GetMapping("/available")
    public ResponseEntity<List<CourierDTO>> getAvailableCouriers() {
        List<Courier> available = courierRepository.findByStatus("ACTIVE");

        List<CourierDTO> dtos = available.stream()
                .map(c -> new CourierDTO(
                        c.getId(),
                        c.getName(),
                        c.getCurrentLocation().getY(),
                        c.getCurrentLocation().getX(),
                        c.getStatus()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourierDTO> getCourierById(@PathVariable Long id) {
        return courierRepository.findById(id)
                .map(c -> ResponseEntity.ok(new CourierDTO(
                        c.getId(),
                        c.getName(),
                        c.getCurrentLocation().getY(),
                        c.getCurrentLocation().getX(),
                        c.getStatus()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourierDTO>> getAllCouriers() {
        List<Courier> all = courierRepository.findAll();

        List<CourierDTO> dtos = all.stream()
                .map(c -> new CourierDTO(
                        c.getId(),
                        c.getName(),
                        c.getCurrentLocation().getY(),
                        c.getCurrentLocation().getX(),
                        c.getStatus()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<CourierDTO> createCourier(@RequestBody CourierDTO dto) {

        Point point = factory.createPoint(new Coordinate(dto.longitude(), dto.latitude()));

        Courier courier = new Courier();
        courier.setName(dto.name());
        courier.setStatus(dto.status());
        courier.setCurrentLocation(point);

        Courier saved = courierRepository.save(courier);

        return ResponseEntity.ok(new CourierDTO(
                saved.getId(),
                saved.getName(),
                saved.getCurrentLocation().getY(),
                saved.getCurrentLocation().getX(),
                saved.getStatus()
        ));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CourierDTO> updateStatus(@PathVariable Long id, @RequestBody StatusUpdateDTO statusDto) {
        return courierRepository.findById(id).map(c -> {
            // No more manual quote stripping needed!
            c.setStatus(statusDto.status());
            Courier saved = courierRepository.save(c);

            return ResponseEntity.ok(new CourierDTO(
                    saved.getId(),
                    saved.getName(),
                    saved.getCurrentLocation().getY(),
                    saved.getCurrentLocation().getX(),
                    saved.getStatus()));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourier(@PathVariable Long id) {
        if (courierRepository.existsById(id)) {
            courierRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}