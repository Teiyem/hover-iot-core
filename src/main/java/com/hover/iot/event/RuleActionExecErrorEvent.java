package com.hover.iot.event;

import com.hover.iot.entity.RuleAction;
import org.springframework.context.ApplicationEvent;

/**
 * Custom event representing an error that occurred during the execution of a rule action.
 */
public class RuleActionExecErrorEvent extends ApplicationEvent {

    private final RuleAction action;

    /**
     * Constructs a new RuleActionExecErrorEvent.
     *
     * @param source  the source object that triggered the event
     * @param action  the rule action associated with the error
     */
    public RuleActionExecErrorEvent(Object source, RuleAction action) {
        super(source);
        this.action = action;
    }

    /**
     * Gets the rule action associated with the error.
     *
     * @return the rule action
     */
    public RuleAction getAction() {
        return action;
    }
}

