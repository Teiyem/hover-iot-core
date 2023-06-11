package com.hover.iot.repository;

import com.hover.iot.entity.Scene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface that provides access to {@link Scene} data stored in a database.
 */
@Repository
public interface SceneRepository extends JpaRepository<Scene, Long> {
}

