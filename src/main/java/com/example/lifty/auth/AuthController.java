package com.example.lifty.auth;

import com.example.lifty.auth.dto.AuthResponse;
import com.example.lifty.auth.dto.LoginRequest;
import com.example.lifty.auth.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest req) {
    authService.register(req);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
    return ResponseEntity.ok(authService.login(req));
  }
}
