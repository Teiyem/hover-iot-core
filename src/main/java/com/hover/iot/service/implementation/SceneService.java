package com.hover.iot.service.implementation;

import com.hover.iot.entity.Scene;
import com.hover.iot.exception.EntityNotFoundException;
import com.hover.iot.repository.SceneRepository;
import com.hover.iot.service.ISceneService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * A service class that handles operations related to scene management. Implements the {@link ISceneService} interface.
 */
@Service
public class SceneService implements ISceneService {

    /**
     * The repository that is used for scene data storage and retrieval.
     */
    private final SceneRepository sceneRepository;

    /**
     * Initializes a new instance of {@link SceneService} class.
     *
     * @param sceneRepository The repository that is used for scene data storage and retrieval.
     */
    public SceneService(SceneRepository sceneRepository) {
        this.sceneRepository = sceneRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Scene create(Scene scene) {
        return sceneRepository.save(scene);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Scene> getAll() {
        return sceneRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Scene getById(Long id) {
        return sceneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Scene", id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Scene update(Scene scene) {
        return sceneRepository.save(scene);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<Boolean> execute(Long id) {
        return null; // TODO
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Long id) {
        sceneRepository.deleteById(id);
        return true;
    }
}

