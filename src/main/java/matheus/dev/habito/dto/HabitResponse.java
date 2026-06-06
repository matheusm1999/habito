package matheus.dev.habito.dto;

import java.time.Instant;

public class HabitResponse {
    private String id;
    private String userId;
    private String name;
    private String description;
    private String frequency;
    private Integer target;
    private Instant createdAt;

    public HabitResponse() {}

    public HabitResponse(String id, String userId, String name, String description, String frequency, Integer target, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.target = target;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    public Integer getTarget() { return target; }
    public void setTarget(Integer target) { this.target = target; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}