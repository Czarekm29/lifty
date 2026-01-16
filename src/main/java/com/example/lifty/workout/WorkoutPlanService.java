package com.example.lifty.workout;

import com.example.lifty.common.ApiException;
import com.example.lifty.user.User;
import com.example.lifty.user.UserRepository;
import com.example.lifty.workout.dto.CreatePlanRequest;
import com.example.lifty.workout.dto.WorkoutPlanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutPlanService {

  private final WorkoutPlanRepository planRepository;
  private final ExerciseRepository exerciseRepository;
  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public List<WorkoutPlanDto> getAllPlans() {
    return planRepository.findAll().stream()
        .map(this::toDto)
        .toList();
  }

  @Transactional
  public WorkoutPlanDto createCustomPlan(User creator, CreatePlanRequest req) {
    WorkoutPlan plan = WorkoutPlan.builder()
        .name(req.getName())
        .description(req.getDescription())
        .difficulty(req.getDifficulty())
        .durationWeeks(req.getDurationWeeks())
        .createdBy(creator)
        .build();

    var daysSorted = req.getDays().stream()
        .sorted(Comparator.comparingInt(CreatePlanRequest.Day::getOrderIndex))
        .toList();

    for (var dayReq : daysSorted) {
      WorkoutDay day = WorkoutDay.builder()
          .name(dayReq.getName())
          .orderIndex(dayReq.getOrderIndex())
          .workoutPlan(plan)
          .build();

      var exSorted = dayReq.getExercises().stream()
          .sorted(Comparator.comparingInt(CreatePlanRequest.ExerciseItem::getOrderIndex))
          .toList();

      for (var exReq : exSorted) {
        Exercise ex = exerciseRepository.findById(exReq.getExerciseId())
            .orElseThrow(() -> new ApiException("Exercise not found: " + exReq.getExerciseId()));

        ExerciseInWorkout li = ExerciseInWorkout.builder()
            .exercise(ex)
            .workoutDay(day)
            .orderIndex(exReq.getOrderIndex())
            .build();

        day.getExercises().add(li);
      }

      plan.getDays().add(day);
    }

    WorkoutPlan saved = planRepository.save(plan);
    return toDto(saved);
  }

  @Transactional
  public void assignPlanToUser(User user, Long planId) {
    WorkoutPlan plan = planRepository.findById(planId)
        .orElseThrow(() -> new ApiException("WorkoutPlan not found: " + planId));
    user.setActivePlan(plan);
    userRepository.save(user);
  }

  private WorkoutPlanDto toDto(WorkoutPlan plan) {
    return WorkoutPlanDto.builder()
        .id(plan.getId())
        .name(plan.getName())
        .description(plan.getDescription())
        .difficulty(plan.getDifficulty())
        .durationWeeks(plan.getDurationWeeks())
        .createdByUserId(plan.getCreatedBy().getId())
        .days(plan.getDays().stream().map(day ->
            WorkoutPlanDto.WorkoutDayDto.builder()
                .id(day.getId())
                .name(day.getName())
                .orderIndex(day.getOrderIndex())
                .exercises(day.getExercises().stream().map(li ->
                    WorkoutPlanDto.ExerciseInDayDto.builder()
                        .id(li.getId())
                        .exerciseId(li.getExercise().getId())
                        .exerciseName(li.getExercise().getName())
                        .orderIndex(li.getOrderIndex())
                        .build()
                ).toList())
                .build()
        ).toList())
        .build();
  }
}
