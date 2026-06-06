package matheus.dev.habito.repository;

import matheus.dev.habito.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitRepository extends JpaRepository<Habit, String> {
    List<Habit> findByUserId(String userId);
}