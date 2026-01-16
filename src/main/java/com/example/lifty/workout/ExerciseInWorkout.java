package com.example.lifty.workout;

import com.example.lifty.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "exercise_in_workout")
public class ExerciseInWorkout extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "exercise_id", nullable = false)
  private Exercise exercise;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "workout_day_id", nullable = false)
  private WorkoutDay workoutDay;

  @Column(nullable = false)
  private Integer orderIndex;
}
