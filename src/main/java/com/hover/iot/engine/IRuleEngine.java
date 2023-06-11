package com.hover.iot.engine;

import com.hover.iot.entity.Device;
import com.hover.iot.entity.Rule;
import org.jetbrains.annotations.NotNull;

public interface IRuleEngine {

    /**
     * Registers a new rule in the rule engine.
     *
     * @param rule The Rule to register.
     */
    void registerRule(@NotNull Rule rule);

    /**
     * Unregisters a rule from the RuleEngine.
     *
     * @param rule The Rule to unregister.
     */
    void unregisterRule(Rule rule);

    /**
     * Evaluates the possible triggers for a given device and executes the associated actions.
     *
     * @param device the Device for which to evaluate the triggers
     */
    void evaluatePossibleTrigger(Device device);
}
