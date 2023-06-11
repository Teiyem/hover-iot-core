package com.hover.iot.service.implementation;

import com.hover.iot.dto.DeviceDTO;
import com.hover.iot.dto.RoomDTO;
import com.hover.iot.entity.Device;
import com.hover.iot.event.EntityChangeEvent;
import com.hover.iot.exception.EntityNotFoundException;
import com.hover.iot.mapper.DeviceDTOMapper;
import com.hover.iot.platform.IPlatformApi;
import com.hover.iot.repository.DeviceRepository;
import com.hover.iot.repository.RoomRepository;
import com.hover.iot.test.utils.DeviceTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private DeviceDTOMapper deviceDTOMapper;

    @Mock
    private VaultService vaultService;

    @InjectMocks
    private DeviceService deviceService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private List<IPlatformApi> platformApiList = new ArrayList<>();

    @Test
    public void testAdd_ValidRequest() {
        // Given
        var request = DeviceTestUtils.createTestAddDeviceRequest();
        var room = DeviceTestUtils.createTestRoom();

        // Mock
        when(roomRepository.findByName(request.room())).thenReturn(Optional.of(room));

        // When
        deviceService.add(request);

        // Then
        verify(deviceRepository, times(1)).save(any(Device.class));
        verify(eventPublisher, times(1)).publishEvent(any(EntityChangeEvent.class));
    }

    @Test
    public void testGetById_ExistingDeviceId() {
        // Given
        var device = DeviceTestUtils.createTestDevice(1L);

        var room = device.getRoom();

        // Mock
        when(deviceRepository.findById(device.getId())).thenReturn(Optional.of(device));
        when(deviceDTOMapper.apply(device)).thenReturn(new DeviceDTO(device.getId(), device.getName(),
                device.getAttributes(), device.getFirmware(),device.isStatus(), new RoomDTO(room.getId(), room.getName()),
                device.getType(), device.getPlatform()));

        // When
        var result = deviceService.getById(device.getId());

        // Then
        assertNotNull(result);
        assertEquals(device.getId(), result.id());
        assertEquals(device.getName(), result.name());

        verify(deviceRepository, times(1)).findById(device.getId());
    }

    @Test
    public void testGetById_NonExistingDeviceId() {
        // Given
        var deviceId = 0L;

        // Mock
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> deviceService.getById(deviceId));

        verify(deviceRepository, times(1)).findById(deviceId);
    }

    @Test
    public void testGetAll_ExistingDevices() {
        // Given
        var devices = DeviceTestUtils.createTestDeviceList();

        // Mock
        when(deviceRepository.findAll()).thenReturn(devices);

        // When
        var result = deviceService.getAll();

        // Then
        assertNotNull(result);
        assertEquals(devices.size(), result.size());

        verify(deviceRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate_ExistingDeviceId() {
        // Given
        var deviceId = 0L;
        var oldDevice = DeviceTestUtils.createTestDevice(deviceId);
        var newDevice = DeviceTestUtils.createTestDevice(deviceId);
        newDevice.setName("Room Light");
        newDevice.setFirmware("2.0.0");

        var request = DeviceTestUtils.createTestUpdateDeviceRequest();

        var room = newDevice.getRoom();

        var expected = new DeviceDTO(newDevice.getId(), newDevice.getName(),
                newDevice.getAttributes(), newDevice.getFirmware(),newDevice.isStatus(), new RoomDTO(room.getId(), room.getName()),
                newDevice.getType(),
                newDevice.getPlatform());

        // Mock
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(oldDevice));
        when(deviceService.update(deviceId, request)).thenReturn(expected);

        // When
        var actual = deviceService.update(deviceId, request);

        // Then
        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(deviceRepository, times(2)).findById(deviceId);
        verify(deviceRepository, times(1)).save(oldDevice);
        verify(eventPublisher, times(1)).publishEvent(any(EntityChangeEvent.class));
    }

    @Test
    public void test_Update_NonExistingDeviceId() {
        // Given
        var deviceId = 1L;

        var request = DeviceTestUtils.createTestUpdateDeviceRequest();

        // Mock
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> deviceService.update(deviceId, request));

        verify(deviceRepository, times(1)).findById(deviceId);
        verify(deviceRepository, times(0)).save(any(Device.class));
        verify(eventPublisher, times(0)).publishEvent(any(EntityChangeEvent.class));
    }

    @Test
    public void tesDelete_ExistingDeviceId() {
        // Given
        var deviceId = 1L;
        var device = DeviceTestUtils.createTestDevice(deviceId);

        // Mock
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        // When
        var result = deviceService.delete(deviceId);

        // Then
        assertTrue(result);
        verify(deviceRepository, times(1)).findById(deviceId);
        verify(deviceRepository, times(1)).delete(device);
    }

    @Test
    public void testSetAttribute_ExistingDeviceId() {
        // Given
        var deviceId = 1L;
        var device = DeviceTestUtils.createTestDevice(deviceId);
        var request = DeviceTestUtils.createTestDeviceAttributeRequest();

        // Mock
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        // When
        deviceService.setAttribute(deviceId, request);

        // Then
        verify(deviceRepository, times(1)).findById(deviceId);
    }
}
