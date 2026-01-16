package com.example.lifty.workout.dto;

import com.example.lifty.workout.Difficulty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CreatePlanRequest {

  @NotBlank
  @Size(max = 120)
  private String name;

  @Size(max = 2000)
  private String description;

  @NotNull
  private Difficulty difficulty;

  @NotNull
  @Min(1)
  @Max(52)
  private Integer durationWeeks;

  @NotEmpty
  private List<Day> days;

  @Data
  public static class Day {
    @NotBlank
    @Size(max = 80)
    private String name;

    @NotNull
    @Min(1)
    private Integer orderIndex;

    @NotEmpty
    private List<ExerciseItem> exercises;
  }

  @Data
  public static class ExerciseItem {
    @NotNull
    private Long exerciseId;

    @NotNull
    @Min(1)
    private Integer orderIndex;
  }
}
