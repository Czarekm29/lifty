package com.example.lifty.training;

import com.example.lifty.common.ApiException;
import com.example.lifty.user.User;
import com.example.lifty.workout.ExerciseRepository;
import com.example.lifty.training.dto.AddSetRequest;
import com.example.lifty.training.dto.WorkoutSessionDto;
import com.example.lifty.training.dto.WorkoutSetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingService {

  private final WorkoutSessionRepository sessionRepository;
  private final WorkoutSetRepository setRepository;
  private final ExerciseRepository exerciseRepository;

  @Transactional
  public Long startSession(User user) {
    var session = WorkoutSession.builder()
        .user(user)
        .date(LocalDate.now())
        .workoutPlan(user.getActivePlan())
        .status(SessionStatus.IN_PROGRESS)
        .startedAt(Instant.now())
        .build();

    return sessionRepository.save(session).getId();
  }

  @Transactional
  public void addSet(User user, Long sessionId, AddSetRequest req) {
    WorkoutSession session = sessionRepository.findById(sessionId)
        .orElseThrow(() -> new ApiException("WorkoutSession not found: " + sessionId));

    if (!session.getUser().getId().equals(user.getId())) {
      throw new ApiException("Forbidden");
    }
    if (session.getStatus() != SessionStatus.IN_PROGRESS) {
      throw new ApiException("Session is not IN_PROGRESS");
    }

    var exercise = exerciseRepository.findById(req.getExerciseId())
        .orElseThrow(() -> new ApiException("Exercise not found: " + req.getExerciseId()));

    WorkoutSet ws = WorkoutSet.builder()
        .exercise(exercise)
        .reps(req.getReps())
        .weight(req.getWeight())
        .rpe(req.getRpe())
        .workoutSession(session)
        .build();

    setRepository.save(ws);
  }

  @Transactional
  public void finishSession(User user, Long sessionId) {
    WorkoutSession session = sessionRepository.findById(sessionId)
        .orElseThrow(() -> new ApiException("WorkoutSession not found: " + sessionId));

    if (!session.getUser().getId().equals(user.getId())) {
      throw new ApiException("Forbidden");
    }

    session.setStatus(SessionStatus.COMPLETED);
    session.setFinishedAt(Instant.now());
    sessionRepository.save(session);
  }

  @Transactional(readOnly = true)
  public List<WorkoutSetDto> getProgressPerExercise(User user, Long exerciseId) {
    return setRepository.findProgress(user.getId(), exerciseId).stream()
        .map(ws -> WorkoutSetDto.builder()
            .date(ws.getWorkoutSession().getDate())
            .exerciseId(ws.getExercise().getId())
            .exerciseName(ws.getExercise().getName())
            .reps(ws.getReps())
            .weight(ws.getWeight())
            .rpe(ws.getRpe())
            .build()
        ).toList();
  }

  @Transactional(readOnly = true)
  public List<WorkoutSessionDto> getHistory(User user) {
    return sessionRepository.findByUser_IdOrderByDateDesc(user.getId()).stream()
        .map(s -> WorkoutSessionDto.builder()
            .id(s.getId())
            .date(s.getDate())
            .workoutPlanId(s.getWorkoutPlan() != null ? s.getWorkoutPlan().getId() : null)
            .status(s.getStatus())
            .startedAt(s.getStartedAt())
            .finishedAt(s.getFinishedAt())
            .build()
        ).toList();
  }
}
