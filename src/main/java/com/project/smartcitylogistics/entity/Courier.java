package com.project.smartcitylogistics.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "couriers") // Multi-tenancy will handle the schema prefix
@EntityListeners(org.springframework.data.jpa.domain.support.AuditingEntityListener.class)
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String status;

    // This maps directly to your PostGIS Geography column
    @Column(columnDefinition = "geography(Point, 4326)")
    private Point currentLocation;

    @org.springframework.data.annotation.CreatedDate // Added this
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}