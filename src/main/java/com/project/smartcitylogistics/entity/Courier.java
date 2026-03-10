package com.project.smartcitylogistics.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "couriers")
@EntityListeners(org.springframework.data.jpa.domain.support.AuditingEntityListener.class)
public class Courier extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(columnDefinition = "geography(Point, 4326)")
    private Point currentLocation;

    @Column(name = "status")
    private String status;

    @LastModifiedDate
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @org.springframework.data.annotation.CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}