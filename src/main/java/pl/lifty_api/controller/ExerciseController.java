package pl.lifty_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lifty_api.service.ExerciseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;

}