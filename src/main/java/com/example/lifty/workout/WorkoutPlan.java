package com.example.lifty.workout;

import com.example.lifty.common.BaseEntity;
import com.example.lifty.user.User;
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
@Table(name = "workout_plan")
public class WorkoutPlan extends BaseEntity {

  @Column(nullable = false, length = 120)
  private String name;

  @Column(length = 2000)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 16)
  private Difficulty difficulty;

  @Column(nullable = false)
  private Integer durationWeeks;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "created_by_user_id", nullable = false)
  private User createdBy;

  @OneToMany(mappedBy = "workoutPlan", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("orderIndex ASC")
  @Builder.Default
  private List<WorkoutDay> days = new ArrayList<>();
}
