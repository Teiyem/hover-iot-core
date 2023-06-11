package com.hover.iot.controller;

import com.hover.iot.entity.Scene;
import com.hover.iot.response.ApiResponse;
import com.hover.iot.service.ISceneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A Controller class for handling HTTP requests related to scene management.
 */
@RestController
@RequestMapping("/scene")
public class SceneController {

    /**
     * The scene service that is used to handle device management requests.
     */
    private final ISceneService sceneService;

    /**
     * Constructs a new SceneController with the specified scene service.
     *
     * @param sceneService The scene service implementation.
     */
    public SceneController(ISceneService sceneService) {
        this.sceneService = sceneService;
    }

    /**
     * Handles a request to creates a new scene.
     *
     * @param scene The scene object to create.
     * @return The created scene object.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Scene>> create(@RequestBody Scene scene) {
        var response = new ApiResponse<>(HttpStatus.OK, sceneService.create(scene));
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handles a request to get list of scenes.
     *
     * @return A list of all scenes.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Scene>>> getAll() {
        var response = new ApiResponse<>(HttpStatus.OK, sceneService.getAll());
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handles a request to get a scene by its id.
     *
     * @param id The id of the scene to retrieve.
     * @return The scene object with the specified ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Scene>> getById(@PathVariable Long id) {
        var response = new ApiResponse<>(HttpStatus.OK, sceneService.getById(id));
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handles a request to update a scene.
     *
     * @param id    The id of the scene to update.
     * @param scene The updated scene object.
     * @return The updated scene object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Scene>> update(@PathVariable Long id, @RequestBody Scene scene) {
        scene.setId(id);
        var response = new ApiResponse<>(HttpStatus.OK, sceneService.update(scene));
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handles a request to delete a scene by its id.
     *
     * @param id The id of the scene to delete.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable Long id) {
        var response = new ApiResponse<>(HttpStatus.OK, sceneService.delete(id));
        return new ResponseEntity<>(response, response.getStatus());

    }
}

