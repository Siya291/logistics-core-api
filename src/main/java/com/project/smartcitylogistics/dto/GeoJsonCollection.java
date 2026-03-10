package com.project.smartcitylogistics.dto;

import java.util.List;
import java.util.Map;

public record GeoJsonCollection(
        String type,
        List<Map<String, Object>> features
) {
    public static GeoJsonCollection of(List<Map<String, Object>> features) {
        return new GeoJsonCollection("FeatureCollection", features);
    }
}