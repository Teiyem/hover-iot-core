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
 * An entity class that represents an automation rule.
 */
@Entity
@Table(name = "TBL_RULE")
public class Rule {

    /**
     * The rule's id.
     */
    @Id
    @SequenceGenerator(
            name = "tbl_rule_id_seq",
            sequenceName = "tbl_rule_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tbl_rule_id_seq"
    )
    private Long id;

    /**
     * The rule's name.
     */
    private String name;

    /**
     * The rule's description.
     */
    private String description;

    /**
     * The rule's actions.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "rule_id")
    private List<RuleAction> actions;

    /**
     * The rule's triggers.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "rule_id")
    private List<RuleTrigger> triggers;

    /**
     * Whether rule is enabled or not.
     */
    private boolean enabled;

    /**
     * The rule's updated at date.
     */
    @UpdateTimestamp
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedAt;

    /**
     * Initializes a new instance of {@link Rule} class. Default Constructor.
     */
    public Rule() {
    }

    /**
     * Gets the rule's id.
     *
     * @return The rule's id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the rule's id.
     *
     * @param id The rule's id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the rule's name.
     *
     * @return The rule's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the rule's name.
     *
     * @param name The rule's name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the rule's description.
     *
     * @return The rule's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the rule's description.
     *
     * @param description The rule's description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the rule's actions.
     *
     * @return The rule's actions.
     */
    public List<RuleAction> getActions() {
        return actions;
    }

    /**
     * Sets the rule's actions.
     *
     * @param actions The rule's actions to set.
     */
    public void setActions(List<RuleAction> actions) {
        this.actions = actions;
    }

    /**
     * Gets the rule's triggers.
     *
     * @return The rule's triggers.
     */
    public List<RuleTrigger> getTriggers() {
        return triggers;
    }

    /**
     * Sets the rule's triggers.
     *
     * @param triggers The rule's triggers to set.
     */
    public void setTriggers(List<RuleTrigger> triggers) {
        this.triggers = triggers;
    }

    /**
     * Gets the rule's updated at date.
     *
     * @return The rule's updated at date.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the rule's updated at date.
     *
     * @param updatedAt The rule's updated at date to set.
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Gets whether the rule is enabled or not.
     *
     * @return Whether the rule is enabled or not.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets whether the rule is enabled or not.
     *
     * @param enabled Whether the rule is enabled or not.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

