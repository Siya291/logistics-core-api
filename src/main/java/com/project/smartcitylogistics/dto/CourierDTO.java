package com.project.smartcitylogistics.dto;

import java.util.UUID;

public record CourierDTO(
        Long id,
        String name,
        double latitude,
        double longitude,
        String status
) {


}
