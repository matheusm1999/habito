package matheus.dev.habito.service;

import matheus.dev.habito.dto.HabitRequest;
import matheus.dev.habito.dto.HabitResponse;
import matheus.dev.habito.model.Habit;
import matheus.dev.habito.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HabitService {
    private final HabitRepository repository;

    public HabitService(HabitRepository repository) {
        this.repository = repository;
    }

    public HabitResponse createHabit(HabitRequest req, String userId) {
        if (req.getName() == null || req.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do hábito não pode ser vazio");
        }
        if (req.getTarget() != null && req.getTarget() < 0) {
            throw new IllegalArgumentException("Target deve ser maior ou igual a 0");
        }
        Integer target = req.getTarget() == null ? 0 : req.getTarget();
        Habit h = new Habit(null, userId, req.getName().trim(), req.getDescription(), req.getFrequency(), target);
        Habit saved = repository.save(h);
        return toResponse(saved);
    }

    public List<HabitResponse> listByUser(String userId) {
        return repository.findByUserId(userId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    private HabitResponse toResponse(Habit h) {
        return new HabitResponse(h.getId(), h.getUserId(), h.getName(), h.getDescription(), h.getFrequency(), h.getTarget(), h.getCreatedAt(), h.getUpdatedAt());
    }
}