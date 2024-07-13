package com.template.template;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Component
@Slf4j
public class ChargingPlugStationDataAnalyzer implements ApplicationRunner {
    @Value("${number-of-requests}")
    private Integer numberOfRequests;

    private ChargingPlugStationGatewayClient chargingPlugStationGatewayClient;

    @Autowired
    private ChargingPlugStationDataAnalyzer(@Value("${charging-plug-station-gateway.url}") final String url) {
        final WebClient webClient = WebClient.builder().baseUrl(url).build();
        final WebClientAdapter adapter = WebClientAdapter.forClient(webClient);
        final HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(adapter).build();
        this.chargingPlugStationGatewayClient = factory.createClient(ChargingPlugStationGatewayClient.class);
    }

    @Override
    public void run(ApplicationArguments args) throws InterruptedException {
        for (int i = 0; i < numberOfRequests; i++) {
            analyzeDailyReport();
            analyzeCurrentStatus();
            Thread.sleep(10);
        }
    }

    private void analyzeDailyReport() {
        log.info("Analyzing daily report");
        chargingPlugStationGatewayClient.getChargingPlugStationDailyReport()
                .subscribe(res -> log.info("Daily report: {}", res),
                        e -> log.error("Error while requesting daily report", e));
    }

    private void analyzeCurrentStatus() {
        log.info("Analyzing current status");
        chargingPlugStationGatewayClient.getChargingPlugStationCurrentStatus()
                .subscribe(res -> log.info("Current status: {}", res),
                        e -> log.error("Error while requesting current status", e));
    }
}
