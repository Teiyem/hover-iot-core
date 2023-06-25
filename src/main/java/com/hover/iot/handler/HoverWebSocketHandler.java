package com.hover.iot.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hover.iot.model.EventNotification;
import com.hover.iot.service.IEventNotifier;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * A WebSocket handler for handling WebSocket events and notifications.
 */
public class HoverWebSocketHandler implements WebSocketHandler, IEventNotifier {

    /**
     * Object mapper for converting objects to JSON and vice versa.
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Set of WebSocket sessions connected to the server.
     */
    private final Set<WebSocketSession> sessions = new HashSet<>();

    /**
     * Invoked after a WebSocket connection is established.
     * Adds the session to the active sessions set.
     *
     * @param session The WebSocket session.
     */
    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) {
        sessions.add(session);
    }

    /**
     * Invoked when a WebSocket message is received. Handles incoming messages from clients.
     *
     * @param session The WebSocket session.
     * @param message The incoming message.
     */
    @Override
    public void handleMessage(@NotNull WebSocketSession session, @NotNull WebSocketMessage<?> message) {
        // Not used in this implementation
    }

    /**
     * Invoked when a transport error occurs in the WebSocket session.
     *
     * @param session   The WebSocket session.
     * @param exception The exception representing the transport error.
     */
    @Override
    public void handleTransportError(@NotNull WebSocketSession session, @NotNull Throwable exception) {
        // Not used in this implementation
    }

    /**
     * Invoked after a WebSocket connection is closed.
     * Removes the session from the active sessions set.
     *
     * @param session The WebSocket session.
     * @param status  The close status.
     * @throws Exception if an error occurs during handling.
     */
    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * Notifies all connected clients with the provided notification.
     *
     * @param eventNotification The notification to send.
     * @throws IOException If an I/O error occurs while sending the notification.
     */
    public void notify(EventNotification eventNotification) throws IOException {
        var noty = objectMapper.writeValueAsString(eventNotification);
        var message = new TextMessage(noty);
        for (WebSocketSession session : sessions) {
            session.sendMessage(message);
        }
    }

}
