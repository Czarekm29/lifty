package com.example.lifty.workout;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
  List<Exercise> findByIsCustomFalse();
  List<Exercise> findByCreatedBy_Id(Long userId);
}
