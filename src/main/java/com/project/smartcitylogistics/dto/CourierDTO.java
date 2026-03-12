package com.project.smartcitylogistics.dto;

public record CourierDTO(
        Long id,
        String name,
        double longitude,
        double latitude,
        String status
) {


}
