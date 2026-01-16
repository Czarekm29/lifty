package com.example.lifty.workout.dto;

import com.example.lifty.workout.Difficulty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class WorkoutPlanDto {
  Long id;
  String name;
  String description;
  Difficulty difficulty;
  Integer durationWeeks;
  Long createdByUserId;
  List<WorkoutDayDto> days;

  @Value
  @Builder
  public static class WorkoutDayDto {
    Long id;
    String name;
    Integer orderIndex;
    List<ExerciseInDayDto> exercises;
  }

  @Value
  @Builder
  public static class ExerciseInDayDto {
    Long id;
    Long exerciseId;
    String exerciseName;
    Integer orderIndex;
  }
}
