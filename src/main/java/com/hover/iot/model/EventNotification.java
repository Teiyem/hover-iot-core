package com.hover.iot.model;

/**
 * A event notification model class.
 */
public class EventNotification {

    /**
     * The notification's type.
     */
    private String type;

    /**
     * The notification's data.
     */
    private String data;

    /**
     * Gets the type of the notification.
     *
     * @return the type of the notification
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the notification.
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the data of the notification.
     *
     * @return The data of the notification.
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the data of the notification.
     *
     * @param data The data to set.
     */
    public void setData(String data) {
        this.data = data;
    }
}

