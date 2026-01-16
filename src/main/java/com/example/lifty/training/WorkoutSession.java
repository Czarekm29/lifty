package com.example.lifty.training;

import com.example.lifty.common.BaseEntity;
import com.example.lifty.user.User;
import com.example.lifty.workout.WorkoutPlan;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "workout_session")
public class WorkoutSession extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private LocalDate date;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "workout_plan_id")
  private WorkoutPlan workoutPlan;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 16)
  private SessionStatus status;

  @Column(nullable = false)
  private Instant startedAt;

  private Instant finishedAt;

  @OneToMany(mappedBy = "workoutSession", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("id ASC")
  @Builder.Default
  private List<WorkoutSet> sets = new ArrayList<>();
}
