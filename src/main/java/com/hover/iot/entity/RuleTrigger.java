package com.hover.iot.entity;

import com.hover.iot.converter.ObjectConverter;
import com.hover.iot.enumeration.TriggerType;
import jakarta.persistence.*;

import java.util.Map;

/**
 * An entity class that represents an automation rule trigger.
 */
@Entity
@Table(name = "TBL_RULE_TRIGGER")
public class RuleTrigger {

    /**
     * The trigger's id.
     */
    @Id
    @SequenceGenerator(
            name = "TBL_RULE_TRIGGER_ID_SEQ",
            sequenceName = "TBL_RULE_TRIGGER_ID_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "TBL_RULE_TRIGGER_ID_SEQ"
    )
    private String id;

    /**
     * The trigger's name.
     */
    private String name;

    /**
     * The trigger's description.
     */
    private String description;

    /**
     * The trigger's type.
     */
    @Enumerated(EnumType.STRING)
    private TriggerType type;

    /**
     * The trigger's parameters
     */
    @ElementCollection
    @CollectionTable(name = "TBL_RULE_TRIGGER_PARAM", joinColumns = @JoinColumn(name = "trigger_id"))
    @MapKeyColumn(name = "param_key")
    @Column(name = "param_value")
    @Convert(converter = ObjectConverter.class)
    private Map<String, Object> parameters;

    /**
     * Initializes a new instance of {@link RuleTrigger} class. Default Constructor.
     */
    public RuleTrigger() {
    }

    /**
     * Gets the trigger's id.
     *
     * @return The trigger's id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the trigger's id.
     *
     * @param id The trigger's id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the trigger's name.
     *
     * @return The trigger's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the trigger's name.
     *
     * @param name The trigger's name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the trigger's description.
     *
     * @return The trigger's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the trigger's id.
     *
     * @param description The trigger's description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the trigger's parameters.
     *
     * @return The trigger's parameters.
     */
    public Map<String, Object> getParameters() {
        return parameters;
    }

    /**
     * Sets the trigger's parameters.
     *
     * @param parameters The trigger's parameters to set.
     */
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    /**
     * Gets the trigger's type.
     *
     * @return The trigger's type.
     */
    public TriggerType getType() {
        return type;
    }

    /**
     * Sets the trigger's type.
     *
     * @param type The trigger's type to set.
     */
    public void setType(TriggerType type) {
        this.type = type;
    }
}
