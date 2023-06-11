package com.hover.iot.repository;

import com.hover.iot.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * An interface that provides access to {@link Rule} data stored in a database.
 */
public interface RuleRepository extends JpaRepository<Rule, Long> {
}

