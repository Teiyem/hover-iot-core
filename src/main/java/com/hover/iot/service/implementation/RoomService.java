package com.hover.iot.service.implementation;

import com.hover.iot.dto.RoomDTO;
import com.hover.iot.exception.EntityNotFoundException;
import com.hover.iot.mapper.RoomDTOMapper;
import com.hover.iot.entity.Room;
import com.hover.iot.repository.RoomRepository;
import com.hover.iot.service.IDeviceService;
import com.hover.iot.service.IRoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A service class that handles operations related to room management. Implements the {@link IRoomService} interface.
 */
@Service
public class RoomService implements IRoomService {

    /**
     * The room repository.
     */
    private final RoomRepository roomRepository;

    /**
     * The DTO mapper for rooms.
     */
    private final RoomDTOMapper roomDTOMapper;

    /**
     * Initializes a new instance of {@link DeviceService} class.
     *
     * @param roomRepository The room repository.
     * @param roomDTOMapper The DTO mapper for rooms
     */
    public RoomService(RoomRepository roomRepository, RoomDTOMapper roomDTOMapper) {
        this.roomRepository = roomRepository;
        this.roomDTOMapper = roomDTOMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(String name) {
        var room = new Room();
        room.setName(name);

        roomRepository.save(room);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RoomDTO> getAll() {
        return roomRepository.findAll()
                .stream()
                .map(roomDTOMapper)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Long id, String name) {
        var room = roomRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Room.class.getSimpleName(), id));

        if(room.getName().equals(name)){
            return false;
        }

        room.setName(name);

        roomRepository.save(room);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Long id) {
        return false;
    }
}
