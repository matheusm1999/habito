package matheus.dev.habito.repository;

import matheus.dev.habito.entity.HabitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<HabitEntity, Long> {}
