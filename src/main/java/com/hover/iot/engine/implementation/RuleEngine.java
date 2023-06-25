package com.hover.iot.engine.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hover.iot.constants.RuleParameterConstants;
import com.hover.iot.engine.IRuleEngine;
import com.hover.iot.entity.*;
import com.hover.iot.enumeration.TriggerType;
import com.hover.iot.event.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.StampedLock;

import static com.hover.iot.constants.RuleParameterConstants.*;

/**
 * A Rule Engine for evaluating and executing automation rules. Implements the {@link IRuleEngine} interface.
 */
@Component
public class RuleEngine implements IRuleEngine {

    /**
     * The logger instance used for logging events and messages related to the RuleEngine class.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(RuleEngine.class);

    /**
     * Atomic counter for the number of scheduled tasks.
     */
    private static final int MAX_SCHEDULED_TASKS = 15;

    /**
     * A lock that allows multiple readers and exclusive writers with optimistic read locking.
     */
    private final StampedLock lock = new StampedLock();

    /**
     * A list of registered rules in the rule engine.
     */
    private final List<Rule> rules = new ArrayList<>();

    /**
     * The application event publisher used for publishing events to the application's event system.
     */
    private final ApplicationEventPublisher eventPublisher;

    /**
     * The object mapper used for serialization and deserialization of json data.
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * The scheduled executor service used for scheduling rule executions.
     */
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(15);

    /**
     * An atomic counter for the number of scheduled tasks.
     */
    private final AtomicInteger scheduledTaskCount = new AtomicInteger(0);

    /**
     * A map that stores the scheduled futures for each rule.
     */
    private final Map<Rule, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>();

    /**
     * Initializes a new instance of {@link RuleEngine} class.
     *
     * @param eventPublisher the ApplicationEventPublisher used for publishing events
     */
    public RuleEngine(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerRule(@NotNull Rule rule) {
        var stamp = lock.writeLock();
        try {
            rules.add(rule);

            for (var trigger : rule.getTriggers()) {
                if (trigger.getType() != TriggerType.TIME_OF_DAY)
                    continue;

                if (scheduledTaskCount.get() >= MAX_SCHEDULED_TASKS)
                    throw new IllegalStateException("Maximum scheduled rule execution limit reached. " +
                            "Cannot register rule with time-based trigger.");

                scheduleRuleExecution(rule);
            }
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unregisterRule(@NotNull Rule rule) {
        LOGGER.debug("Cancelling rule with id -> {}", rule.getId());

        var stamp = lock.writeLock();
        try {
            rules.remove(rule);

            var schedule = scheduledFutures.get(rule);

            if (schedule != null) {
                if (!schedule.isCancelled()) {
                    LOGGER.debug("Cancelling running schedule");
                    schedule.cancel(true);
                }
                scheduledFutures.remove(rule);
            }
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void evaluatePossibleTrigger(@NotNull Device device) {
        LOGGER.debug("Evaluating possible rule from device -> {}", device.getId());
        var stamp = lock.readLock();
        try {
            for (var rule : rules) {

                var trigger = checkDeviceMatchesTrigger(rule, device.getId());

                if (trigger == null)
                    continue;

                if (trigger.getType() == TriggerType.ATTRIBUTE_THRESHOLD) {
                    evaluateAttributeThreshold(rule, device, trigger);
                    continue;
                }

                for (var action : rule.getActions()) {
                    performAction(action);
                }
            }

        } finally {
            lock.unlockRead(stamp);
        }
    }

    /**
     * Checks if a device matches the trigger conditions of a rule.
     *
     * @param rule the Rule to be checked
     * @param id   the ID of the device to be checked
     * @return the matching RuleTrigger, or null if no match is found
     */
    private RuleTrigger checkDeviceMatchesTrigger(@NotNull Rule rule, Long id) {
        return rule.getTriggers().stream()
                .filter(trigger -> trigger.getType() == TriggerType.ATTRIBUTE_CHANGE)
                .filter(trigger -> {
                    var parameters = trigger.getParameters();
                    var entityId = (Long) parameters.get(RULE_PARAMETER_ENTITY_ID_KEY);
                    return entityId != null && entityId.equals(id);
                })
                .findFirst()
                .orElse(null);
    }

    /**
     * Evaluates an attribute threshold for a specific device and performs associated actions if the attribute value
     * is within the threshold range with the specified tolerance.
     *
     * @param rule    The rule containing the desired trigger.
     * @param device  The device to evaluate the trigger against.
     * @param trigger The rule trigger to evaluate.
     */
    private void evaluateAttributeThreshold(Rule rule, @NotNull Device device, @NotNull RuleTrigger trigger) {
        var attributeKey = (String) trigger.getParameters().get(RuleParameterConstants.RULE_PARAMETER_ATTRIBUTE_KEY);
        var attributeValue = device.getAttributeValue(attributeKey, Integer.class);
        var threshold = (Double) trigger.getParameters().get(RULE_PARAMETER_ATTRIBUTE_THRESHOLD_KEY);
        var tolerance = 2.0;

        if (attributeValue != null && Math.abs(attributeValue - threshold) <= tolerance) {
            for (var action : rule.getActions()) {
                performAction(action);
            }
        }
    }

    /**
     * Performs the action o of the rule.
     *
     * @param action The action to be performed.
     */
    private void performAction(@NotNull RuleAction action) {
        switch (action.getType()) {
            case ATTRIBUTE -> executeAttributeAction(action);
            case SCENE -> executeSceneAction(action);
            case NOTIFICATION -> executeNotificationAction(action);
        }
    }

    /**
     * Schedules the execution of a time-based rule.
     *
     * @param rule The Rule to be scheduled.
     */
    private void scheduleRuleExecution(@NotNull Rule rule) {
        for (var trigger : rule.getTriggers()) {
            if (trigger.getType() == TriggerType.TIME_OF_DAY) {
                var parameters = trigger.getParameters();
                var timeOfDay = (LocalTime) parameters.get(RuleParameterConstants.RULE_PARAMETER_TIME_OF_DAY_KEY);

                long initialDelay = calculateInitialDelay(timeOfDay);

                ScheduledFuture<?> future = executorService.schedule(() -> {
                    for (var action : rule.getActions()) {
                        try {
                            performAction(action);
                        } catch (Exception e) {
                            handleActionExecutionError(action, e);
                        }
                    }
                }, initialDelay, TimeUnit.MILLISECONDS);

                storeScheduledFuture(rule, future);
            }
        }
    }

    /**
     * Executes an attribute-based rule action.
     *
     * @param action the RuleAction to be executed
     */
    private void executeAttributeAction(@NotNull RuleAction action) {
        var parameters = action.getParameters();

        var entityId = (Long) parameters.get(RULE_PARAMETER_ENTITY_ID_KEY);

        var obj = (String) parameters.get(RuleParameterConstants.RULE_PARAMETER_ATTRIBUTES_KEY);

        try {
            var attribute = objectMapper.readValue(obj, Attribute.class);
            eventPublisher.publishEvent(new AttributeActionEvent(this, entityId, attribute));

        } catch (JsonProcessingException e) {
            handleActionExecutionError(action, e);
        }
    }

    /**
     * Executes a scene-based rule action.
     *
     * @param action the RuleAction to be executed
     */
    private void executeSceneAction(@NotNull RuleAction action) {
        var parameters = action.getParameters();
        var entityId = (Long) parameters.get(RULE_PARAMETER_ENTITY_ID_KEY);

        eventPublisher.publishEvent(new SceneActionEvent(this, entityId));
    }

    /**
     * Executes a notification-based rule action.
     *
     * @param action the RuleAction to be executed
     */
    private void executeNotificationAction(@NotNull RuleAction action) {
        var parameters = action.getParameters();
        var id = (Long) parameters.get(RULE_PARAMETER_ENTITY_ID_KEY);
        var message = (String) parameters.get(RULE_PARAMETER_NOTIFICATION_MESSAGE_KEY);

        eventPublisher.publishEvent(new NotifyActionEvent(this, id, message));
    }

    /**
     * Handles the error that occurs during the execution of a rule action.
     *
     * @param action The rule action that encountered an error during execution.
     * @param e      The exception that occurred during the execution of the rule action.
     */
    private void handleActionExecutionError(RuleAction action, Exception e) {
        eventPublisher.publishEvent(new RuleActionEvent(this, action, false));
        LOGGER.error("An error occurred while attempting to execute the rule action -> {}", action, e);
    }

    /**
     * Calculates the initial delay in milliseconds from the current time to the specified time of day.
     *
     * @param time The time to calculate the initial delay to.
     * @return The initial delay in milliseconds.
     */
    private long calculateInitialDelay(LocalTime time) {
        LocalTime currentTime = LocalTime.now();
        return currentTime.until(time, ChronoUnit.MILLIS);
    }

    /**
     * Stores the scheduled future associated with a rule for later reference.
     *
     * @param rule   the rule for which to store the scheduled future
     * @param future the scheduled future to be stored
     */
    private void storeScheduledFuture(Rule rule, ScheduledFuture<?> future) {
        scheduledFutures.put(rule, future);
    }
}

