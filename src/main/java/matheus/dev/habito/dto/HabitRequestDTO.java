package matheus.dev.habito.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Set;
import matheus.dev.habito.entity.DayOfWeekEnum;

public class HabitRequestDTO {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    @Positive
    private Integer goal;

    @NotEmpty
    private Set<DayOfWeekEnum> frequencyDays;

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
}
