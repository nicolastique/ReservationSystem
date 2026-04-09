package com.ReservationSystem.Service;

import com.ReservationSystem.Dto.CreateReservationRequest;
import com.ReservationSystem.Dto.ReservationResponse;
import com.ReservationSystem.Mapper.ReservationMapper;
import com.ReservationSystem.Model.Reservation;
import com.ReservationSystem.Model.Resource;
import com.ReservationSystem.Model.User;
import com.ReservationSystem.Repository.ReservationRepository;
import com.ReservationSystem.Repository.ResourceRepository;
import com.ReservationSystem.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;

    @Transactional
    public ReservationResponse createReservation(CreateReservationRequest request, String email) {

        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new IllegalArgumentException("Invalid date range");
        }

        List<Reservation> conflicts = reservationRepository.findConflicts(
                request.getResourceId(),
                request.getStartDate(),
                request.getEndDate()
        );

        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Resource already booked");
        }

        // 🔐 usuario viene del token, no del body
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Resource resource = resourceRepository.findById(request.getResourceId())
                .orElseThrow(() -> new RuntimeException("Resource not found"));

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setResource(resource);
        reservation.setStartDate(request.getStartDate());
        reservation.setEndDate(request.getEndDate());

        return ReservationMapper.toResponse(
                reservationRepository.save(reservation)
        );
    }
}