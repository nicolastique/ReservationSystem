package com.ReservationSystem.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReservationResponse {

    private Long id;
    private Long userId;
    private Long resourceId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}