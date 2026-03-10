package com.project.smartcitylogistics.repository;

import com.project.smartcitylogistics.entity.Courier;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {

    @Query(value = "SELECT * FROM couriers c WHERE ST_DWithin(c.current_location, :point, :distanceInMeters)", nativeQuery = true)
    List<Courier> findNearbyCouriers(@Param("point") Point point, @Param("distanceInMeters") double distanceInMeters);

    @Query(value = "SELECT * FROM couriers c ORDER BY c.current_location <-> :point LIMIT 5", nativeQuery = true)
    List<Courier> findClosestFiveCouriers(@Param("point") Point point);
}