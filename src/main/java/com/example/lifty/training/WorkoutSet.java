package com.example.lifty.training;

import com.example.lifty.common.BaseEntity;
import com.example.lifty.workout.Exercise;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "workout_set")
public class WorkoutSet extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "exercise_id", nullable = false)
  private Exercise exercise;

  @Column(nullable = false)
  private Integer reps;

  @Column(nullable = false)
  private Double weight;

  @Column(nullable = false)
  private Double rpe;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "workout_session_id", nullable = false)
  private WorkoutSession workoutSession;
}
