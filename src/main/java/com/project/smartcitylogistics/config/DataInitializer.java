package com.project.smartcitylogistics.config;

import com.project.smartcitylogistics.entity.Courier;
import com.project.smartcitylogistics.entity.User;
import com.project.smartcitylogistics.repository.CourierRepository;
import com.project.smartcitylogistics.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CourierRepository courierRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    @Override
    public void run(String... args) {
        TenantContext.setTenantId("vendor_delivery_co");
        try {
            initializeData();
        } finally {
            TenantContext.clear();
        }
    }

    @Transactional
    public void initializeData() {
        if (courierRepository.count() == 0) {
            Courier courier = new Courier();
            courier.setName("Flash Thompson");
            courier.setStatus("ACTIVE");
            courier.setCurrentLocation(factory.createPoint(new Coordinate(28.2293, -25.7479)));
            courierRepository.save(courier);
            System.out.println(">>> Initialized Test Courier!");
        } else {
            System.out.println(">>> Skipping Courier!");
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setTenantId("vendor_delivery_co");
            userRepository.save(admin);
            System.out.println(">>> Initialized Admin User!");
        } else {
            System.out.println(">>> Skipping User!");
        }
    }
}