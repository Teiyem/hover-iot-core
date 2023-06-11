package com.hover.iot.entity;

import com.hover.iot.converter.ObjectConverter;
import com.hover.iot.enumeration.ActionType;
import jakarta.persistence.*;

import java.util.Map;

/**
 * An entity class that represents an automation rule action.
 */
@Entity
@Table(name = "TBL_RULE_ACTION")
public class RuleAction {

    /**
     * The action's id.
     */
    @Id
    @SequenceGenerator(
            name = "TBL_RULE_ACTION_ID_SEQ",
            sequenceName = "TBL_RULE_ACTION_ID_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "TBL_RULE_ACTION_ID_SEQ"
    )
    private String id;

    /**
     * The action's name.
     */
    private String name;

    /**
     * The action's description.
     */
    private String description;

    /**
     * The action's type.
     */
    private ActionType type;

    /**
     * The action's parameters.
     */
    @ElementCollection
    @CollectionTable(name = "TBL_RULE_ACTION_PARAM", joinColumns = @JoinColumn(name = "action_id"))
    @MapKeyColumn(name = "param_key")
    @Column(name = "param_value")
    @Convert(converter = ObjectConverter.class)
    private Map<String, Object> parameters;

    /**
     * Gets the action's id.
     *
     * @return The action's id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the action's id.
     *
     * @param id The action's id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the action's name.
     *
     * @return The action's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the action's name.
     *
     * @param name The action's name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the action's description.
     *
     * @return The action's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the action's description.
     *
     * @param description The action's description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the action's parameters.
     *
     * @return The action's parameters.
     */
    public Map<String, Object> getParameters() {
        return parameters;
    }

    /**
     * Sets the action's parameters.
     *
     * @param parameters The action's parameters to set.
     */
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    /**
     * Gets the action's type.
     *
     * @return The action's type.
     */
    public ActionType getType() {
        return type;
    }

    /**
     * Sets the action's type.
     *
     * @param type The action's type to set.
     */
    public void setType(ActionType type) {
        this.type = type;
    }
}
