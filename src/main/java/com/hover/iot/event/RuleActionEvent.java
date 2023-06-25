package com.hover.iot.event;

import com.hover.iot.entity.RuleAction;
import org.springframework.context.ApplicationEvent;

/**
 * An event representing an execution of a rule action.
 */
public class RuleActionEvent extends ApplicationEvent {

    /**
     * Thw rule that was executed.
     */
    private final RuleAction action;

    /**
     * Indicates whether the execution of the associated rule action was successful.
     */
    private final boolean isExecSuccessful;

    /**
     * Constructs a RuleActionEvent.
     *
     * @param source           The object on which the event initially occurred.
     * @param action           The rule action that was executed.
     * @param isExecSuccessful Indicates whether the execution of the action was successful.
     */
    public RuleActionEvent(Object source, RuleAction action, boolean isExecSuccessful) {
        super(source);
        this.action = action;
        this.isExecSuccessful = isExecSuccessful;
    }

    /**
     * Gets the rule action that was executed.
     *
     * @return The rule action.
     */
    public RuleAction getAction() {
        return action;
    }

    /**
     * Checks if the execution of the action was successful.
     *
     * @return True if the execution was successful, false otherwise.
     */
    public boolean isExecSuccessful() {
        return isExecSuccessful;
    }
}


