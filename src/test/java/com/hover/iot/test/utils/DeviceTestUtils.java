package com.hover.iot.test.utils;

import com.hover.iot.entity.Attribute;
import com.hover.iot.entity.Device;
import com.hover.iot.entity.Room;
import com.hover.iot.enumeration.AttributeType;
import com.hover.iot.enumeration.DeviceType;
import com.hover.iot.model.Credentials;
import com.hover.iot.request.AddDeviceRequest;
import com.hover.iot.request.DeviceAttributeRequest;
import com.hover.iot.request.UpdateDeviceRequest;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A utility class for creating test objects related to devices.
 */
public class DeviceTestUtils {

    /**
     * Creates a test room object.
     *
     * @return The created test room.
     */
    public static @NotNull Room createTestRoom() {
        var room = new Room();
        room.setId(1L);
        room.setName("Living Room");
        return room;
    }

    /**
     * Creates a test AddDeviceRequest object.
     *
     * @return The created test AddDeviceRequest.
     */
    public static @NotNull AddDeviceRequest createTestAddDeviceRequest() {
        var name = "Light";
        var host = "192.0.151.1";
        var attribute = new Attribute("brightness", 100,
                AttributeType.INTEGER);
        var attributes = Collections.singletonList(attribute);
        var firmware = "1.0";
        var type = DeviceType.LIGHT;
        var credentials = new Credentials("{username, password}", "{test_username, test_pass}");
        var platform = "Hover";

        return new AddDeviceRequest(name, host, attributes, firmware, createTestRoom().getName(), type, credentials,
                platform);
    }

    /**
     * Creates a test UpdateDeviceRequest object.
     *
     * @return The created test UpdateDeviceRequest.
     */
    public static @NotNull UpdateDeviceRequest createTestUpdateDeviceRequest() {
        return new UpdateDeviceRequest("Room Light", "192.168.0.1",
                "2.0", "Bedroom", new Credentials("{username, password}", "{updated_username, updated_pass}"));
    }

    /**
     * Creates a test DeviceAttributeRequest object.
     *
     * @return The created test DeviceAttributeRequest.
     */
    public static @NotNull DeviceAttributeRequest createTestDeviceAttributeRequest() {
        var attributes = Collections.singletonList(new Attribute("brightness",
                100, AttributeType.INTEGER));

        return new DeviceAttributeRequest(attributes);
    }

    /**
     * Creates a test device.
     *
     * @return The created test device.
     */
    public static @NotNull Device createTestDevice(Long id) {
        var device = new Device();

        device.setId(id);
        device.setName("Bulb " + id);
        device.setHost("192.168.0.1");
        device.setAttributes(Collections.singletonList(new Attribute("state",
                false, AttributeType.BOOLEAN)));
        device.setFirmware("1.0");
        device.setStatus(true);
        device.setType(DeviceType.LIGHT);
        device.setRoom(createTestRoom());
        device.setPlatform("Hover");

        return device;
    }

    /**
     * Creates a test list of devices.
     *
     * @return The created test device list.
     */
    public static @NotNull List<Device> createTestDeviceList() {
        var testDevice = createTestDevice(1L);
        var anotherTestDevice = createTestDevice(2L);
        return Arrays.asList(testDevice, anotherTestDevice);
    }

}
