package com.hover.iot.controller;

import com.hover.iot.dto.DeviceDTO;
import com.hover.iot.enumeration.DeviceType;
import com.hover.iot.request.AddDeviceRequest;
import com.hover.iot.request.DeviceAttributeRequest;
import com.hover.iot.request.UpdateDeviceRequest;
import com.hover.iot.response.ApiResponse;
import com.hover.iot.service.IDeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A Controller class for handling HTTP requests related to device management.
 */
@RestController
@RequestMapping("/api/v1/device")
public class DeviceController {

    /**
     * The service that is used to handle device management requests.
     */
    private final IDeviceService deviceService;

    /**
     * Initializes a new instance of {@link DeviceController} class.
     *
     * @param deviceService The service that is used to handle device management requests.
     */
    public DeviceController(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * Handles a request to add a new device.
     *
     * @param request A {@link AddDeviceRequest} object containing the device's details
     * @return A {@link ApiResponse} object containing the result of the request.
     */
    @PostMapping()
    public ResponseEntity<ApiResponse<Object>> add(@RequestBody AddDeviceRequest request) {
        deviceService.add(request);
        var response = new ApiResponse<>(HttpStatus.OK, "Successfully added device");
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handles a request to a device by id.
     *
     * @param id The id of the device to get.
     * @return A {@link ApiResponse} object containing the device.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DeviceDTO>> get(@PathVariable Long id) {
        var response = new ApiResponse<>(HttpStatus.OK, deviceService.getById(id));
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handles a request to get a list of devices.
     *
     * @return A {@link ApiResponse} object containing a list of devices.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<DeviceDTO>>> getAll() {
        var response = new ApiResponse<>(HttpStatus.OK, deviceService.getAll());
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handles a request to get a list of devices by room.
     *
     * @param name The name of the room to get the devices by.
     * @return A {@link ApiResponse} object containing a list of devices.
     */
    @GetMapping("/room/{name}")
    public ResponseEntity<ApiResponse<List<DeviceDTO>>> getAllByRoom(@PathVariable String name) {
        var response = new ApiResponse<>(HttpStatus.OK, deviceService.getByRoom(name));
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handles a request to get a list of devices by type.
     * @param type The name of the type to get the devices by.
     * @return A {@link ApiResponse} object containing a list of devices.
     */
    @GetMapping("/group/{type}")
    public ResponseEntity<ApiResponse<List<DeviceDTO>>> getAllByType(@PathVariable DeviceType type) {
        var response = new ApiResponse<>(HttpStatus.OK, deviceService.getByType(type));
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handles a request to update the attributes of a device.
     *
     * @param id      The id of the device to update.
     * @param request A {@link DeviceAttributeRequest} object containing the device's attribute details.
     * @return A {@link ApiResponse} object containing the updated device.
     */
    @PutMapping("/{id}/attribute")
    public ResponseEntity<ApiResponse<Object>> writeAttribute(@PathVariable Long id, DeviceAttributeRequest request) {
        deviceService.setAttribute(id, request);
        var response = new ApiResponse<>(HttpStatus.OK, "Successfully updated device attribute");
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handles a request to update a device.
     *
     * @param id      The id of the device to update
     * @param request A {@link UpdateDeviceRequest} object containing the device's details.
     * @return A {@link ApiResponse} object containing the updated device.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DeviceDTO>> update(@PathVariable Long id, @RequestBody UpdateDeviceRequest request) {
        var response = new ApiResponse<>(HttpStatus.OK, deviceService.update(id, request));
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handles a request to delete a device.
     *
     * @param id The id of the device to delete.
     * @return A {@link ApiResponse} object containing the result of the request.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable Long id) {
        var response = new ApiResponse<>(HttpStatus.OK, deviceService.delete(id));
        return new ResponseEntity<>(response, response.getStatus());
    }
}
