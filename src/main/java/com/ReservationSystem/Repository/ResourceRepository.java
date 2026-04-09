package com.ReservationSystem.Repository;

import com.ReservationSystem.Model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
