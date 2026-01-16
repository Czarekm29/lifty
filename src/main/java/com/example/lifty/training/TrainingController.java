package com.example.lifty.training;

import com.example.lifty.user.User;
import com.example.lifty.training.dto.AddSetRequest;
import com.example.lifty.training.dto.StartSessionResponse;
import com.example.lifty.training.dto.WorkoutSessionDto;
import com.example.lifty.training.dto.WorkoutSetDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training")
@RequiredArgsConstructor
public class TrainingController {

  private final TrainingService trainingService;

  @PostMapping("/sessions/start")
  public StartSessionResponse start(@AuthenticationPrincipal User user) {
    Long id = trainingService.startSession(user);
    return StartSessionResponse.builder().sessionId(id).build();
  }

  @PostMapping("/sessions/{sessionId}/sets")
  public void addSet(
      @AuthenticationPrincipal User user,
      @PathVariable Long sessionId,
      @Valid @RequestBody AddSetRequest req
  ) {
    trainingService.addSet(user, sessionId, req);
  }

  @PostMapping("/sessions/{sessionId}/finish")
  public void finish(@AuthenticationPrincipal User user, @PathVariable Long sessionId) {
    trainingService.finishSession(user, sessionId);
  }

  @GetMapping("/progress/{exerciseId}")
  public List<WorkoutSetDto> progress(@AuthenticationPrincipal User user, @PathVariable Long exerciseId) {
    return trainingService.getProgressPerExercise(user, exerciseId);
  }

  @GetMapping("/history")
  public List<WorkoutSessionDto> history(@AuthenticationPrincipal User user) {
    return trainingService.getHistory(user);
  }
}
