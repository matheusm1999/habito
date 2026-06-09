CREATE TABLE habit (
    id BIGINT NOT NULL AUTO_INCREMENT,
    owner_username VARCHAR(255) NOT NULL,
    name VARCHAR(120) NOT NULL,
    description TEXT NULL,
    goal INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE habit_frequency (
    habit_id BIGINT NOT NULL,
    day_of_week VARCHAR(20) NOT NULL,
    PRIMARY KEY (habit_id, day_of_week),
    CONSTRAINT fk_habit_frequency_habit
        FOREIGN KEY (habit_id) REFERENCES habit (id)
        ON DELETE CASCADE
);
