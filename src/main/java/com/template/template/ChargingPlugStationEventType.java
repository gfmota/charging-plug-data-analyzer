package com.template.template;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ChargingPlugStationEventType {
    DAILY("DAILY"),
    HOURLY("HOURLY"),
    LAST_STATUS("LAST_STATUS");

    @JsonValue
    private String label;

    ChargingPlugStationEventType(final String label) {
        this.label = label;
    }
}