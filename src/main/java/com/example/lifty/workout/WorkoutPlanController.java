package com.example.lifty.workout;

import com.example.lifty.user.User;
import com.example.lifty.workout.dto.CreatePlanRequest;
import com.example.lifty.workout.dto.WorkoutPlanDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class WorkoutPlanController {

  private final WorkoutPlanService planService;

  @GetMapping
  public List<WorkoutPlanDto> getAllPlans() {
    return planService.getAllPlans();
  }

  @PostMapping
  public WorkoutPlanDto createCustomPlan(
      @AuthenticationPrincipal User user,
      @Valid @RequestBody CreatePlanRequest req
  ) {
    return planService.createCustomPlan(user, req);
  }

  @PostMapping("/{planId}/assign")
  public void assignPlan(@AuthenticationPrincipal User user, @PathVariable Long planId) {
    planService.assignPlanToUser(user, planId);
  }
}
