package com.project.smartcitylogistics.repository;

import com.project.smartcitylogistics.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {
    // Spring Data JPA creates all the Save/Find/Delete methods automatically!
}