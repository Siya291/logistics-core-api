package com.project.smartcitylogistics.util;

import com.project.smartcitylogistics.entity.Courier;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class GeoJsonMapper {

    public Map<String, Object> toFeature(Courier courier) {
        return Map.of(
                "type", "Feature",
                "geometry", Map.of(
                        "type", "Point",
                        "coordinates", new double[] {
                                courier.getCurrentLocation().getX(),
                                courier.getCurrentLocation().getY()
                        }
                ),
                "properties", Map.of(
                        "name", courier.getName(),
                        "status", courier.getStatus(),
                        "id", courier.getId()
                )
        );
    }
}