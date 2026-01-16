package com.example.lifty.workout;

import com.example.lifty.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "workout_day")
public class WorkoutDay extends BaseEntity {

  @Column(nullable = false, length = 80)
  private String name;

  @Column(nullable = false)
  private Integer orderIndex;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "workout_plan_id", nullable = false)
  private WorkoutPlan workoutPlan;

  @OneToMany(mappedBy = "workoutDay", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("orderIndex ASC")
  @Builder.Default
  private List<ExerciseInWorkout> exercises = new ArrayList<>();
}
