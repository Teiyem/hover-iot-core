package com.hover.iot.service.implementation;

import com.hover.iot.engine.IRuleEngine;
import com.hover.iot.entity.Device;
import com.hover.iot.entity.Rule;
import com.hover.iot.event.EntityChangeEvent;
import com.hover.iot.exception.RuleNotFoundException;
import com.hover.iot.repository.RuleRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * A service class that handles operations related to rule management.
 */
@Service
public class RuleService {
    /**
     * The logger for the RuleService class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleService.class);

    /**
     * The RuleEngine used for rule evaluation.
     */
    private final IRuleEngine IRuleEngine;

    /**
     * The RuleRepository used for rule persistence.
     */
    private final RuleRepository ruleRepository;

    /**
     * Constructs a new RuleService with the specified RuleEngine and RuleRepository.
     *
     * @param IRuleEngine      the RuleEngine used for rule evaluation
     * @param ruleRepository  the RuleRepository used for rule persistence
     */
    public RuleService(IRuleEngine IRuleEngine, RuleRepository ruleRepository) {
        this.IRuleEngine = IRuleEngine;
        this.ruleRepository = ruleRepository;
    }

    /**
     * Creates a new rule.
     *
     * @param rule the rule to create
     * @return the created rule
     */
    public Rule create(Rule rule) {
        LOGGER.info("Creating rule: {}", rule);

        Rule _rule;

        try {
            _rule = ruleRepository.save(rule);

            synchronized(IRuleEngine) {
                IRuleEngine.registerRule(rule);
            }
        }
        catch (Exception e) {
            LOGGER.error("An error occurred while creating rule -> {}, {}", rule, e);
            throw new RuntimeException("An error occurred while creating rule");
        }

        LOGGER.info("Creating rule: {}", rule);

        return _rule;
    }

    /**
     * Retrieves a rule by ID.
     *
     * @param id the ID of the rule to retrieve
     * @return the retrieved rule
     * @throws RuleNotFoundException if the rule with the specified ID does not exist
     */
    public Rule get(Long id) {
        return ruleRepository.findById(id)
                .orElseThrow(() -> new RuleNotFoundException(id));
    }

    /**
     * Retrieves a list of all rules.
     *
     * @return the list of rules
     */
    public List<Rule> getList() {
        return ruleRepository.findAll();
    }

    /**
     * Updates a rule with the specified ID.
     *
     * @param newRule the new rule data
     * @param id      the ID of the rule to update
     * @return the updated rule
     * @throws RuleNotFoundException if the rule with the specified ID does not exist
     */
    @Transactional
    public Rule update(Rule newRule, Long id) {
        LOGGER.info("Updating rule with id -> {}", id);

        Rule rule = ruleRepository.findById(id)
                .map(_rule -> {
                    _rule.setName(newRule.getName());
                    _rule.setActions(newRule.getActions());
                    _rule.setTriggers(newRule.getTriggers());
                    return ruleRepository.save(_rule);
                }).orElseThrow(() -> new RuleNotFoundException(id));

        LOGGER.info("Rule successfully updated -> {}", rule);

        return rule;
    }

    /**
     * Deletes a rule with the specified ID.
     *
     * @param id the ID of the rule to delete
     * @throws RuleNotFoundException if the rule with the specified ID does not exist
     */
    @Transactional
    public void delete(Long id) {
        LOGGER.info("Deleting rule with id -> {}", id);

        Rule rule = get(id);
        IRuleEngine.unregisterRule(rule);
        ruleRepository.delete(rule);

        LOGGER.info("Rule deleted successfully");
    }

    /**
     * Event listener for handling entity change events.
     *
     * @param event The entity change event.
     */
    @Async
    @EventListener(condition = "#event.entity == T(com.hover.iot.entity.Device) && #event.changeType == T(com.hover.iot.enumeration.ChangeType).MODIFIED")
    public void onChangeEvent(@NotNull EntityChangeEvent event) {
        LOGGER.info("Entity change event received -> {}", event);

        synchronized (IRuleEngine) {
            if (event.getEntity() instanceof Device) {
                IRuleEngine.evaluatePossibleTrigger((Device) event.getEntity());
            }
        }

        LOGGER.info("Entity change event processed");
    }
}
