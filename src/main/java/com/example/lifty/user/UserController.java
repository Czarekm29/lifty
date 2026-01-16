package com.example.lifty.user;

import com.example.lifty.user.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

  @GetMapping("/me")
  public UserProfileDto me(@AuthenticationPrincipal User user) {
    return UserProfileDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .username(user.getUsername())
        .role(user.getRole())
        .activePlanId(user.getActivePlan() != null ? user.getActivePlan().getId() : null)
        .build();
  }
}
