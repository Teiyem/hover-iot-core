package com.hover.iot.service.implementation;

import com.hover.iot.dto.DeviceDTO;
import com.hover.iot.entity.Device;
import com.hover.iot.entity.Room;
import com.hover.iot.entity.Vault;
import com.hover.iot.enumeration.ChangeType;
import com.hover.iot.enumeration.DeviceStatus;
import com.hover.iot.enumeration.DeviceType;
import com.hover.iot.event.EntityChangeEvent;
import com.hover.iot.exception.EntityNotFoundException;
import com.hover.iot.mapper.DeviceDTOMapper;
import com.hover.iot.model.Credentials;
import com.hover.iot.platform.IPlatformHandler;
import com.hover.iot.repository.DeviceRepository;
import com.hover.iot.request.AddDeviceRequest;
import com.hover.iot.request.DeviceAttributeRequest;
import com.hover.iot.request.UpdateDeviceRequest;
import com.hover.iot.service.IDeviceService;
import com.hover.iot.service.IRoomService;
import com.hover.iot.service.IVaultService;
import com.hover.iot.util.UniqueIdentifierGenerator;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * The repository that is used for device data storage and retrieval.
     */
    private final DeviceRepository deviceRepository;

    /**
     * The service that is used to handle room management.
     */
    private final IRoomService roomService;

    /**
     * The DTO mapper for devices.
     */
    private final DeviceDTOMapper deviceDTOMapper;

    /**
     * The service that is used to handle db secret data.
     */
    private final IVaultService vaultService;

    /**
     * A map that stores platform names as keys and corresponding PlatformApi instances as values.
     */
    private final Map<String, IPlatformHandler> platformApiMap;

    /**
     * Initializes a new instance of {@link DeviceService} class.
     *
     * @param eventPublisher   The event publisher.
     * @param deviceRepository The repository that is used for device data storage and retrieval..
     * @param roomService      The service that is used to handle room management..
     * @param deviceDTOMapper  The DTO mapper for devices.
     * @param vaultService     The service that is used to handle db secret data.
     * @param platformApiList  The list of platform apis.
     */
    public DeviceService(ApplicationEventPublisher eventPublisher, DeviceRepository deviceRepository,
                         IRoomService roomService, DeviceDTOMapper deviceDTOMapper, VaultService vaultService,
                         @NotNull List<IPlatformHandler> platformApiList) {
        this.eventPublisher = eventPublisher;
        this.deviceRepository = deviceRepository;
        this.roomService = roomService;
        this.deviceDTOMapper = deviceDTOMapper;
        this.vaultService = vaultService;
        this.platformApiMap = platformApiList.stream()
                .collect(Collectors.toMap(IPlatformHandler::getName, Function.identity()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void add(@NotNull AddDeviceRequest request) {
        var device = new Device();

        device.setName(request.name());
        device.setHost(request.host());
        device.setAttributes(request.attributes());
        device.setFirmware(request.firmware());
        device.setStatus(DeviceStatus.ONLINE);
        device.setType(request.type());
        device.setPlatform(request.platform());

        var room = getRoom(request.room());

        device.setRoom(room);

        try {
            String data = device.getId() + device.getFirmware() + device.getName() + UUID.randomUUID();
            device.setUuid(UniqueIdentifierGenerator.generateUniqueIdentifier(data));
        } catch (Exception e) {
            device.setUuid(UUID.randomUUID().toString());
            logger.error(e.getMessage(), e);
        }

        if (request.credentials() != null) {
            saveDeviceCredentials(request.credentials(), device.getUuid());
        }

        processDeviceChanges(device, ChangeType.ADDED, true);
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
    @Transactional
    public DeviceDTO getById(Long id) {
        return deviceRepository.findById(id)
                .map(deviceDTOMapper).orElseThrow(() ->
                        new EntityNotFoundException(Device.class.getSimpleName(), id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<DeviceDTO> getByRoom(String name) {
        return deviceRepository.findDevicesByRoomName(name)
                .stream()
                .map(deviceDTOMapper)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<DeviceDTO> getByType(DeviceType type) {
        return deviceRepository.findDevicesByType(type)
                .stream()
                .map(deviceDTOMapper)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<DeviceDTO> getAll() {
        return deviceRepository.findAll()
                .stream()
                .map(deviceDTOMapper)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public DeviceDTO update(Long id, @NotNull UpdateDeviceRequest request) {
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
            var room = getRoom(request.room());

            device.setRoom(room);
            updated++;
        }

        if (updated > 0) {
            processDeviceChanges(device, ChangeType.MODIFIED, true);
        }

        return deviceDTOMapper.apply(device);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public boolean delete(Long id) {
        var device = deviceRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Device.class.getSimpleName(), id));
        deviceRepository.delete(device);
        processDeviceChanges(device, ChangeType.DELETED, false);
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
                        IPlatformHandler platformApi = platformApiMap.get(device.getPlatform());
                        try {
                            platformApi.writeAttribute(device, attribute);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets a room by the room's name.
     *
     * @param name The name of the room to get.
     * @return The room.
     * @throws EntityNotFoundException If the room does not exist.
     */
    private Room getRoom(@NotNull String name) {
        return roomService.getByName(name);
    }

    /**
     * Processes the changes on a device, optionally saving it to the repository and publishing an event.
     *
     * @param device     The device to process the changes for.
     * @param changeType The type of change that occurred on the device.
     * @param save       Indicates whether to save the device to the repository.
     */
    private void processDeviceChanges(Device device, ChangeType changeType, boolean save) {
        if (save)
            deviceRepository.save(device);

        eventPublisher.publishEvent(new EntityChangeEvent(this, device, changeType));
    }
}

