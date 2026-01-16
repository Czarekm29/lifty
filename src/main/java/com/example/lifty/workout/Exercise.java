package com.example.lifty.workout;

import com.example.lifty.common.BaseEntity;
import com.example.lifty.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "exercise")
public class Exercise extends BaseEntity {

  @Column(nullable = false, length = 140)
  private String name;

  @Column(nullable = false, length = 80)
  private String muscleGroup;

  @Column(nullable = false)
  private boolean isCustom;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "created_by_user_id")
  private User createdBy;
}
