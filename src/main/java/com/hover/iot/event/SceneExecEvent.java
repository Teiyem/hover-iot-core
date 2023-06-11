package com.hover.iot.event;

import org.springframework.context.ApplicationEvent;

/**
 * Custom event representing the execution of a scene.
 */
public class SceneExecEvent extends ApplicationEvent {

    private final Long sceneId;

    /**
     * Constructs a new SceneExecEvent.
     *
     * @param source   the source object that triggered the event
     * @param sceneId  the ID of the scene being executed
     */
    public SceneExecEvent(Object source, Long sceneId) {
        super(source);
        this.sceneId = sceneId;
    }

    /**
     * Gets the ID of the scene being executed.
     *
     * @return the scene ID
     */
    public Long getSceneId() {
        return sceneId;
    }
}
