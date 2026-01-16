package com.example.lifty.training.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddSetRequest {
  @NotNull
  private Long exerciseId;

  @NotNull
  @Min(1)
  private Integer reps;

  @NotNull
  private Double weight;

  @NotNull
  private Double rpe;
}
