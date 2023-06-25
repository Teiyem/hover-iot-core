package com.hover.iot.event;

import org.springframework.context.ApplicationEvent;

/**
 * An event representing an execution of a scene.
 */
public class SceneActionEvent extends ApplicationEvent {

    /**
     * The id of the scene to execute.
     */
    private final Long sceneId;

    /**
     * Constructs a new SceneExecEvent.
     *
     * @param source   the source object that triggered the event
     * @param sceneId  the ID of the scene being executed
     */
    public SceneActionEvent(Object source, Long sceneId) {
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
