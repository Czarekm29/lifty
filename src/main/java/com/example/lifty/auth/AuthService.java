package com.example.lifty.auth;

import com.example.lifty.auth.dto.AuthResponse;
import com.example.lifty.auth.dto.LoginRequest;
import com.example.lifty.auth.dto.RegisterRequest;
import com.example.lifty.common.ApiException;
import com.example.lifty.user.User;
import com.example.lifty.user.UserRepository;
import com.example.lifty.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder encoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public void register(RegisterRequest req) {
    if (userRepository.existsByEmail(req.getEmail().toLowerCase())) {
      throw new ApiException("Email already used");
    }
    User user = User.builder()
        .email(req.getEmail().toLowerCase())
        .password(encoder.encode(req.getPassword()))
        .username(req.getUsername())
        .role(UserRole.USER)
        .build();
    userRepository.save(user);
  }

  public AuthResponse login(LoginRequest req) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(req.getEmail().toLowerCase(), req.getPassword())
    );

    var user = userRepository.findByEmail(req.getEmail().toLowerCase())
        .orElseThrow(() -> new ApiException("User not found"));

    String token = jwtService.generateAccessToken(
        user.getEmail(),
        Map.of("role", user.getRole().name(), "uid", user.getId())
    );

    return AuthResponse.builder().accessToken(token).build();
  }
}
