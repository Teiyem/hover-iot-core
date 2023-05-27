package com.hover.iot.service.implementation;

import com.hover.iot.dto.DeviceDto;
import com.hover.iot.enumeration.AttributeType;
import com.hover.iot.event.EntityEvent;
import com.hover.iot.exception.EntityNotFoundException;
import com.hover.iot.mapper.DeviceDTOMapper;
import com.hover.iot.model.*;
import com.hover.iot.platform.PlatformApi;
import com.hover.iot.repository.DeviceRepository;
import com.hover.iot.repository.RoomRepository;
import com.hover.iot.request.AddDeviceRequest;
import com.hover.iot.request.DeviceAttributeRequest;
import com.hover.iot.request.UpdateDeviceRequest;
import com.hover.iot.service.IRoomService;
import com.hover.iot.test.utils.DeviceTestUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import java.util.*;

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
    private List<PlatformApi> platformApiList = new ArrayList<>();

    @Test
    public void testAdd_ValidRequest() {
        // Given
        AddDeviceRequest request = DeviceTestUtils.createTestAddDeviceRequest();
        Room room = DeviceTestUtils.createTestRoom();

        // Mock
        when(roomRepository.findByName(request.room())).thenReturn(Optional.of(room));

        // When
        deviceService.add(request);

        // Then
        verify(deviceRepository, times(1)).save(any(Device.class));
        verify(eventPublisher, times(1)).publishEvent(any(EntityEvent.class));
    }

    @Test
    public void testGetById_ExistingDeviceId() {
        // Given
        Device device = DeviceTestUtils.createTestDevice(1L);

        // Mock
        when(deviceRepository.findById(device.getId())).thenReturn(Optional.of(device));
        when(deviceDTOMapper.apply(device)).thenReturn(new DeviceDto(device.getId(), device.getName(), device.getFirmware(),
                device.isStatus(), device.getType(), device.getPlatform()));

        // When
        DeviceDto result = deviceService.getById(device.getId());

        // Then
        assertNotNull(result);
        assertEquals(device.getId(), result.id());
        assertEquals(device.getName(), result.name());

        verify(deviceRepository, times(1)).findById(device.getId());
    }

    @Test
    public void testGetById_NonExistingDeviceId() {
        // Given
        Long deviceId = 0L;

        // Mock
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> deviceService.getById(deviceId));

        verify(deviceRepository, times(1)).findById(deviceId);
    }

    @Test
    public void testGetAll_ExistingDevices() {
        // Given
        List<Device> devices = DeviceTestUtils.createTestDeviceList();

        // Mock
        when(deviceRepository.findAll()).thenReturn(devices);

        // When
        List<DeviceDto> result = deviceService.getAll();

        // Then
        assertNotNull(result);
        assertEquals(devices.size(), result.size());

        verify(deviceRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate_ExistingDeviceId() {
        // Given
        Long deviceId = 1L;
        Device device = DeviceTestUtils.createTestDevice(deviceId);
        DeviceDto expectedResult = deviceDTOMapper.apply(device);

        UpdateDeviceRequest request = DeviceTestUtils.createTestUpdateDeviceRequest();

        // Mock
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));
        when(deviceService.update(deviceId, request)).thenReturn(expectedResult);
        when(deviceDTOMapper.apply(device)).thenReturn(new DeviceDto(deviceId, device.getName(), device.getFirmware(),
                device.isStatus(), device.getType(), device.getPlatform()));

        // When
        DeviceDto result = deviceService.update(deviceId, request);

        // Then
        assertNotNull(result);
        assertEquals(device.getId(), result.id());
        assertEquals(request.name(), result.name());

        verify(deviceRepository, times(2)).findById(deviceId);
        verify(deviceRepository, times(1)).save(device);
        verify(eventPublisher, times(1)).publishEvent(any(EntityEvent.class));
    }

    @Test
    public void test_Update_NonExistingDeviceId() {
        // Given
        Long deviceId = 1L;
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());
        UpdateDeviceRequest request = DeviceTestUtils.createTestUpdateDeviceRequest();

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> deviceService.update(deviceId, request));

        verify(deviceRepository, times(1)).findById(deviceId);
        verify(deviceRepository, times(0)).save(any(Device.class));
        verify(eventPublisher, times(0)).publishEvent(any(EntityEvent.class));
    }

    @Test
    public void tesDelete_ExistingDeviceId() {
        // Given
        Long deviceId = 1L;

        // When
        boolean result = deviceService.delete(deviceId);

        // Then
        assertTrue(result);
        verify(deviceRepository, times(1)).deleteById(deviceId);
    }

    @Test
    public void testSetAttribute_ExistingDeviceId() {
        // Given
        Long deviceId = 1L;
        Device device = DeviceTestUtils.createTestDevice(deviceId);
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));
        DeviceAttributeRequest request = DeviceTestUtils.createTestDeviceAttributeRequest();

        // When
        deviceService.setAttribute(deviceId, request);

        // Then
        verify(deviceRepository, times(1)).findById(deviceId);
    }


}
