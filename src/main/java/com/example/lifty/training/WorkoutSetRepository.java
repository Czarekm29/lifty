package com.example.lifty.training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutSetRepository extends JpaRepository<WorkoutSet, Long> {

  @Query("""
      select ws
      from WorkoutSet ws
      join ws.workoutSession s
      where s.user.id = :userId
        and ws.exercise.id = :exerciseId
      order by s.date asc, ws.id asc
      """)
  List<WorkoutSet> findProgress(Long userId, Long exerciseId);

  @Query("""
      select s.date
      from WorkoutSet ws
      join ws.workoutSession s
      where s.user.id = :userId
      group by s.date
      order by s.date desc
      """)
  List<LocalDate> findTrainingDates(Long userId);
}
