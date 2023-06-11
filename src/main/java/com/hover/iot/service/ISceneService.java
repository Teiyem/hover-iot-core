package com.hover.iot.service;

import com.hover.iot.entity.Scene;
import com.hover.iot.exception.EntityNotFoundException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * A service interface that defines the methods for scene management.
 */
public interface ISceneService {

    /**
     * Creates a new scene.
     *
     * @param scene The scene to create.
     * @return The created scene.
     */
    Scene create(Scene scene);

    /**
     * Retrieves all scenes.
     *
     * @return A list of all scenes.
     */
    List<Scene> getAll();

    /**
     * Retrieves a scene by its id.
     *
     * @param id The id of the scene to retrieve.
     * @return The retrieved scene.
     * @throws EntityNotFoundException if the scene with the given id does not exist.
     */
    Scene getById(Long id);

    /**
     * Updates an existing scene.
     *
     * @param scene The scene to update.
     * @return The updated scene.
     */
    Scene update(Scene scene);

    /**
     * Executes the scene asynchronously.
     *
     * @param id The ID of the scene to execute.
     * @return A CompletableFuture representing the asynchronous result of the execution of the scene.
     */
    CompletableFuture<Boolean> execute(Long id);

    /**
     * Deletes a scene by its id.
     *
     * @param id The id of the scene to delete.
     */
    boolean delete(Long id);
}
