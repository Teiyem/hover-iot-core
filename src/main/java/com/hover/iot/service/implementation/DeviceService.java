package com.hover.iot.service.implementation;

import com.hover.iot.dto.DeviceDto;
import com.hover.iot.enumeration.ChangeType;
import com.hover.iot.event.EntityEvent;
import com.hover.iot.exception.EntityNotFoundException;
import com.hover.iot.mapper.DeviceDTOMapper;
import com.hover.iot.model.Credentials;
import com.hover.iot.model.Device;
import com.hover.iot.model.Vault;
import com.hover.iot.platform.PlatformApi;
import com.hover.iot.repository.DeviceRepository;
import com.hover.iot.repository.RoomRepository;
import com.hover.iot.request.AddDeviceRequest;
import com.hover.iot.request.DeviceAttributeRequest;
import com.hover.iot.request.UpdateDeviceRequest;
import com.hover.iot.service.IDeviceService;
import com.hover.iot.util.UniqueIdentifierGenerator;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A service class that handles operations related to device management. Implements the {@link IDeviceService} interface.
 */
@Service
public class DeviceService implements IDeviceService {

    /**
     * The logger for DeviceService.
     */
    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);

    /**
     * The application event publisher.
     */
    private final ApplicationEventPublisher eventPublisher;

    /**
     * The device repository.
     */
    private final DeviceRepository deviceRepository;

    /**
     * The device repository.
     */
    private final RoomRepository roomRepository;

    /**
     * The DTO mapper for devices.
     */
    private final DeviceDTOMapper deviceDTOMapper;

    /**
     * The vault service.
     */
    private final VaultService vaultService;

    /**
     * A map that stores platform names as keys and corresponding PlatformApi instances as values.
     */
    private final Map<String, PlatformApi> platformApiMap;

    /**
     * Initializes a new instance of {@link DeviceService} class.
     *
     * @param eventPublisher   The event publisher.
     * @param deviceRepository The device repository.
     * @param roomRepository   The room repository.
     * @param deviceDTOMapper  The DTO mapper for devices.
     * @param vaultService     The vault service.
     * @param platformApiList  The list of platform apis.
     */
    public DeviceService(ApplicationEventPublisher eventPublisher, DeviceRepository deviceRepository,
                         RoomRepository roomRepository, DeviceDTOMapper deviceDTOMapper, VaultService vaultService,
                         @NotNull List<PlatformApi> platformApiList) {
        this.eventPublisher = eventPublisher;
        this.deviceRepository = deviceRepository;
        this.roomRepository = roomRepository;
        this.deviceDTOMapper = deviceDTOMapper;
        this.vaultService = vaultService;
        this.platformApiMap = platformApiList.stream()
                .collect(Collectors.toMap(PlatformApi::getName, Function.identity()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(@NotNull AddDeviceRequest request) {
        var device = new Device();

        device.setName(request.name());
        device.setHost(request.host());
        device.setAttributes(request.attributes());
        device.setFirmware(request.firmware());
        device.setStatus(true);
        device.setType(request.type());
        device.setPlatform(request.platform());

        var room = roomRepository.findByName(request.room())
                .orElseThrow(() -> new EntityNotFoundException("Room with name -> " + request.room()));

        device.setRoom(room);

        try {
            String data = device.getId() + device.getFirmware() + device.getName() + UUID.randomUUID();
            device.setUuid(UniqueIdentifierGenerator.generateUniqueIdentifier(data));
        } catch (Exception e) {
            device.setUuid(UUID.randomUUID().toString());
            logger.error(e.getMessage(), e);
        }

        deviceRepository.save(device);

        if (request.credentials() != null) {
            saveDeviceCredentials(request.credentials(), device.getUuid());
        }


        eventPublisher.publishEvent(new EntityEvent(this, device, ChangeType.ADDED));
    }

    /**
     * Saves device credentials to a vault.
     *
     * @param credentials The device credentials to save
     * @param key         The key to associate with the vault.
     */
    private void saveDeviceCredentials(Credentials credentials, String key) {
        var vault = new Vault();

        var data = vaultService.packData(credentials);

        vault.setKey(key);
        vault.setData(data);

        vaultService.add(vault);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceDto getById(Long id) {
        return deviceRepository.findById(id)
                .map(deviceDTOMapper).orElseThrow(() ->
                        new EntityNotFoundException(Device.class.getSimpleName(), id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeviceDto> getByRoom(String name) {
        return deviceRepository.findDevicesByRoomName(name)
                .stream()
                .map(deviceDTOMapper)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeviceDto> getAll() {
        return deviceRepository.findAll()
                .stream()
                .map(deviceDTOMapper)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceDto update(Long id, @NotNull UpdateDeviceRequest request) {
        var device = deviceRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Device.class.getSimpleName(), id));

        int updated = 0;

        if (!request.name().equals(device.getName())) {
            device.setName(request.name());
            updated++;
        }

        if (!request.host().equals(device.getHost())) {
            device.setHost(request.host());
            updated++;
        }

        if (request.firmware().equals(device.getFirmware())) {
            device.setFirmware(request.firmware());
            updated++;
        }

        if (request.room().equals(device.getRoom().getName())) {
            var room = roomRepository.findByName(request.room())
                    .orElseThrow(() -> new EntityNotFoundException("Room with name -> " + request.name()));

            device.setRoom(room);
            updated++;
        }

        if (updated > 0) {
            deviceRepository.save(device);
            eventPublisher.publishEvent(new EntityEvent(this, device, ChangeType.MODIFIED));
        }

        return deviceDTOMapper.apply(device);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Long id) {
        deviceRepository.deleteById(id);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAttribute(Long id, @NotNull DeviceAttributeRequest request) {
        var device = deviceRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Device.class.getSimpleName(), id));

        var attributes = device.getAttributes();

        for (var attribute : attributes) {
            for (var _attribute : request.attributes()) {
                if (Objects.equals(attribute.getName(), _attribute.getName())) {
                    if (!Objects.equals(attribute.getValue(), _attribute.getValue())) {
                        PlatformApi platformApi = platformApiMap.get(device.getPlatform());
                        try {
                            platformApi.setAttribute(device, attribute);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }
}

