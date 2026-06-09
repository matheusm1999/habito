package matheus.dev.habito.controller;

import jakarta.validation.Valid;
import matheus.dev.habito.dto.HabitRequestDTO;
import matheus.dev.habito.dto.HabitResponseDTO;
import matheus.dev.habito.service.HabitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/habits")
public class HabitController {
    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @PostMapping
    public ResponseEntity<HabitResponseDTO> createHabit(
            @Valid @RequestBody HabitRequestDTO request, @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(habitService.createHabit(request, user.getUsername()));
    }
}
