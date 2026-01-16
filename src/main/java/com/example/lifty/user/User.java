package com.example.lifty.user;

import com.example.lifty.common.BaseEntity;
import com.example.lifty.workout.WorkoutPlan;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "app_user", uniqueConstraints = {
    @UniqueConstraint(name = "uk_user_email", columnNames = "email")
})
public class User extends BaseEntity implements UserDetails {

  @Column(nullable = false, length = 320)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, length = 64)
  private String username;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 16)
  private UserRole role;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "active_plan_id")
  private WorkoutPlan activePlan;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
