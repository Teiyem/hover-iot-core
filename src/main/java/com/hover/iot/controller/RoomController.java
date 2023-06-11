package com.hover.iot.controller;

import com.hover.iot.dto.RoomDTO;
import com.hover.iot.response.ApiResponse;
import com.hover.iot.service.IRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A Controller class for handling HTTP requests related to room management.
 */
@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    /**
     * The service that is used to handle room management.
     */
    private final IRoomService roomService;

    /**
     * Initializes a new instance of {@link RoomController} class.
     *
     * @param roomService The service that is used to handle room management.
     */
    public RoomController(IRoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * Handles a request to add a new room.
     *
     * @param name The name of the room to add.
     * @return A {@link ApiResponse} object containing the result of the request.
     */
    @PostMapping()
    public ResponseEntity<ApiResponse<Object>> add(@RequestBody String name) {
        roomService.add(name);
        var response = new ApiResponse<>(HttpStatus.OK, "Successfully added room");
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handles a request to get a list of rooms.
     *
     * @return A {@link ApiResponse} object containing a list of rooms.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoomDTO>>> getAll() {
        var response = new ApiResponse<>(HttpStatus.OK, roomService.getAll());
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handles a request to update a room.
     *
     * @param id   The id of the room to update.
     * @param name The room name to update.
     * @return A {@link ApiResponse} object containing the updated device.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> update(@PathVariable Long id, @RequestBody String name) {
        var response = new ApiResponse<>(HttpStatus.OK, roomService.update(id, name));
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handles a request to delete a room.
     *
     * @param id The id of the room to delete.
     * @return A {@link ApiResponse} object containing the result of the request.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable Long id) {
        var response = new ApiResponse<>(HttpStatus.OK, roomService.delete(id));
        return new ResponseEntity<>(response, response.getStatus());
    }

}
