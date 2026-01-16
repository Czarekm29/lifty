package com.example.lifty.user.dto;

import com.example.lifty.user.UserRole;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserProfileDto {
  Long id;
  String email;
  String username;
  UserRole role;
  Long activePlanId;
}
