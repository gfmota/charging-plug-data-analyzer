package com.template.template;

import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

public interface ChargingPlugStationGatewayClient {
    @GetExchange(url = "/lastDayReport", accept = "application/json")
    Mono<Object> getChargingPlugStationDailyReport();

    @GetExchange(url = "/currentStatus", accept = "application/json")
    Mono<Object> getChargingPlugStationCurrentStatus();
}
