package com.example.lifty.training.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class WorkoutSetDto {
  LocalDate date;
  Long exerciseId;
  String exerciseName;
  Integer reps;
  Double weight;
  Double rpe;
}
