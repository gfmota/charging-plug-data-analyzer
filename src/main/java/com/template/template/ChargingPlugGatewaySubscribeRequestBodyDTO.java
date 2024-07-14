package com.template.template;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChargingPlugGatewaySubscribeRequestBodyDTO {
    private String path;
    private String uri;
    private ChargingPlugStationEventType eventType;
}
