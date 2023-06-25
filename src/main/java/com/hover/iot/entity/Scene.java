package com.hover.iot.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * An entity class that represents a scene.
 */
@Entity
@Table(name = "TBL_SCENE")
public class Scene {

    /**
     * The scene's id.
     */
    @Id
    @SequenceGenerator(
            name = "TBL_SCENE_TRIGGER_ID_SEQ",
            sequenceName = "TBL_SCENE_TRIGGER_ID_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "TBL_SCENE_TRIGGER_ID_SEQ"
    )
    private Long id;

    /**
     * The scene's name.
     */
    private String name;

    /**
     * The scene's description.
     */
    private String description;

    /**
     * The scene's action.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "scene_id")
    private List<SceneAction> actions;

    /**
     * Whether scene is running or not.
     */
    private boolean isRunning;

    /**
     * The scene's updated at date.
     */
    @UpdateTimestamp
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedAt;

    /**
     * Initializes a new instance of {@link Scene} class. Default Constructor.
     */
    public Scene() {
    }

    /**
     * Gets the scene's id.
     *
     * @return The scene's id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the scene's id.
     *
     * @param id The scene's id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the scene's name.
     *
     * @return The scene's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the scene's name.
     *
     * @param name The scene's name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the scene's description.
     *
     * @return The scene's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the scene's description.
     *
     * @param description The rule's description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the scene's actions.
     *
     * @return The scene's actions.
     */
    public List<SceneAction> getActions() {
        return actions;
    }

    /**
     * Sets the scene's actions.
     *
     * @param actions The scene's actions to set.
     */
    public void setActions(List<SceneAction> actions) {
        this.actions = actions;
    }

    /**
     * Gets the scene's updated at date.
     *
     * @return The scene's updated at date.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the scene's updated at date.
     *
     * @param updatedAt The scene's updated at date to set.
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Gets whether the scene is running or not.
     *
     * @return Whether the scene is running or not.
     */
    public boolean isIsRunning() {
        return isRunning;
    }

    /**
     * Sets whether the scene is running or not.
     *
     * @param enabled Whether the scene is running or not.
     */
    public void setIsRunning(boolean enabled) {
        this.isRunning = enabled;
    }
}


