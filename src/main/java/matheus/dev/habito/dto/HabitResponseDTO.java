package matheus.dev.habito.dto;

import java.time.LocalDateTime;
import java.util.Set;
import matheus.dev.habito.entity.DayOfWeekEnum;

public class HabitResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Integer goal;
    private Set<DayOfWeekEnum> frequencyDays;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGoal() {
        return goal;
    }

    public void setGoal(Integer goal) {
        this.goal = goal;
    }

    public Set<DayOfWeekEnum> getFrequencyDays() {
        return frequencyDays;
    }

    public void setFrequencyDays(Set<DayOfWeekEnum> frequencyDays) {
        this.frequencyDays = frequencyDays;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
