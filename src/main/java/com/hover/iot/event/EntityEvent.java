package com.hover.iot.event;

import com.hover.iot.enumeration.ChangeType;
import com.hover.iot.service.implementation.DeviceService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEvent;

/**
 * An event that occurs on an entity and holds information about the entity, the type of change and the source.
 */
public class EntityEvent extends ApplicationEvent {

    /**
     * The entity that is the subject of the event.
     */
    private final Object entity;

    /**
     * The type of change that occurred.
     */
    private final ChangeType changeType;

    /**
     * Initializes a new instance of {@link EntityEvent} class.
     *
     * @param source     The object on which the event initially occurred.
     * @param entity     The entity that is the subject of the event.
     * @param changeType The type of change that occurred.
     */
    public EntityEvent(@NotNull Object source, @NotNull Object entity, @NotNull ChangeType changeType) {
        super(source);
        this.entity = entity;
        this.changeType = changeType;
    }

    /**
     * Gets the type of change that occurred.
     *
     * @return The type of change that occurred.
     */
    public Object getEntity() {
        return entity;
    }

    /**
     * Gets the type of change that occurred.
     *
     * @return The type of change that occurred.
     */
    public ChangeType getChangeType() {
        return changeType;
    }

    /**
     * Gets the simple name of the class of the entity that is the subject of the event.
     *
     * @return The simple name of the class of the entity that is the subject of the event.
     */
    public String getEntityName() {
        Class<?> obj = entity.getClass();
        return obj.getSimpleName();
    }
}

