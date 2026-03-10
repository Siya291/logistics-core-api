package com.project.smartcitylogistics.dto;

public record CourierDTO(
        Long id,
        String name,
        double latitude,
        double longitude,
        String status
) {


}
