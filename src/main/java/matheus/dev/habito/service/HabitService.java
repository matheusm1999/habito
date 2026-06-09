package matheus.dev.habito.service;

import java.util.LinkedHashSet;
import matheus.dev.habito.dto.HabitRequestDTO;
import matheus.dev.habito.dto.HabitResponseDTO;
import matheus.dev.habito.entity.HabitEntity;
import matheus.dev.habito.repository.HabitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HabitService {
    private final HabitRepository habitRepository;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    @Transactional
    public HabitResponseDTO createHabit(HabitRequestDTO request, String ownerUsername) {
        final HabitEntity habit = new HabitEntity();
        habit.setOwnerUsername(ownerUsername);
        habit.setName(request.getName().trim());
        habit.setDescription(request.getDescription());
        habit.setGoal(request.getGoal());
        habit.setFrequencyDays(new LinkedHashSet<>(request.getFrequencyDays()));

        final HabitEntity savedHabit = habitRepository.save(habit);
        return toResponse(savedHabit);
    }

    private HabitResponseDTO toResponse(HabitEntity habit) {
        final HabitResponseDTO response = new HabitResponseDTO();
        response.setId(habit.getId());
        response.setName(habit.getName());
        response.setDescription(habit.getDescription());
        response.setGoal(habit.getGoal());
        response.setFrequencyDays(habit.getFrequencyDays());
        response.setCreatedAt(habit.getCreatedAt());
        response.setUpdatedAt(habit.getUpdatedAt());
        return response;
    }
}
