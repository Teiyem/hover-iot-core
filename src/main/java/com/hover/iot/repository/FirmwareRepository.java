package com.hover.iot.repository;

import com.hover.iot.entity.Firmware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface that provides access to {@link Firmware} data stored in a database.
 */
@Repository
public interface FirmwareRepository extends JpaRepository<Firmware, Long> {
}
