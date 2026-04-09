package com.ReservationSystem.Mapper;

import com.ReservationSystem.Dto.ReservationResponse;
import com.ReservationSystem.Model.Reservation;

public class ReservationMapper {

    public static ReservationResponse toResponse(Reservation r) {
        return ReservationResponse.builder()
                .id(r.getId())
                .userId(r.getUser().getId())
                .resourceId(r.getResource().getId())
                .startDate(r.getStartDate())
                .endDate(r.getEndDate())
                .build();
    }
}