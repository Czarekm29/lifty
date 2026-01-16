package com.example.lifty.training.dto;

import com.example.lifty.training.SessionStatus;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.time.LocalDate;

@Value
@Builder
public class WorkoutSessionDto {
  Long id;
  LocalDate date;
  Long workoutPlanId;
  SessionStatus status;
  Instant startedAt;
  Instant finishedAt;
}
