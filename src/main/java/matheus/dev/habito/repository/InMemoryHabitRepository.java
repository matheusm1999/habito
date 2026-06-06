package matheus.dev.habito.repository;

import matheus.dev.habito.model.Habit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/* Legacy in-memory store kept for reference. Not a Spring bean. */
class InMemoryHabitRepositoryLegacy {
    private final ConcurrentMap<Long, Habit> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    public Habit save(Habit habit) {
        // legacy placeholder, not used by Spring
        if (habit.getId() == null) {
            // no-op for legacy
        }
        return habit;
    }

    public List<Habit> findByUserId(String userId) {
        return new ArrayList<>();
    }
}