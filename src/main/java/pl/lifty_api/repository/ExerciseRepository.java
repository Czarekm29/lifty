package pl.lifty_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lifty_api.model.Exercise;

import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
}