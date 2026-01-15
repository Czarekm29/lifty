package pl.lifty_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lifty_api.repository.ExerciseRepository;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
}