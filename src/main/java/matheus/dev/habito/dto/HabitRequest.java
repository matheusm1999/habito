package matheus.dev.habito.dto;

public class HabitRequest {
    private String name;
    private String description;
    private String frequency;
    private Integer target;
    private Integer version;

    public HabitRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    public Integer getTarget() { return target; }
    public void setTarget(Integer target) { this.target = target; }
    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
}