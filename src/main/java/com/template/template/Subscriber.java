package com.template.template;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class Subscriber implements ApplicationRunner {
    @Value("${number-of-clients}")
    private Integer numberOfClients;

    private final String gatewayPath = "http://localhost:8080";
    private final String subscribeUri = "/subscribe";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < numberOfClients; i++) {
            subscribeDaily();
            subscribeLastStatus();
            subscribeHourly();
        }
    }

    private void subscribeHourly() {
        log.info("Subscribing to daily report");
        final var requestBody = ChargingPlugGatewaySubscribeRequestBodyDTO.builder()
                .path("http://localhost:8081")
                .uri("/hourly-report")
                .eventType(ChargingPlugStationEventType.HOURLY)
                .build();

        WebClient.create(gatewayPath)
                .post()
                .uri(subscribeUri)
                .accept(MediaType.ALL)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::is2xxSuccessful, clientResponse -> {
                    log.info("Subscribing hourly report success");
                    return Mono.empty();
                })
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    log.error("Error subscribing for hourly report");
                    return Mono.empty();
                })
                .bodyToMono(Void.class)
                .onErrorResume(throwable -> {
                    log.error("Error connecting to gateway", throwable);
                    return Mono.empty();
                })
                .subscribe();
    }

    private void subscribeDaily() {
        log.info("Subscribing to daily report");
        final var requestBody = ChargingPlugGatewaySubscribeRequestBodyDTO.builder()
                .path("http://localhost:8081")
                .uri("/daily-report")
                .eventType(ChargingPlugStationEventType.DAILY)
                .build();

        WebClient.create(gatewayPath)
                .post()
                .uri(subscribeUri)
                .accept(MediaType.ALL)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::is2xxSuccessful, clientResponse -> {
                    log.info("Subscribing daily report success");
                    return Mono.empty();
                })
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    log.error("Error subscribing for daily report");
                    return Mono.empty();
                })
                .bodyToMono(Void.class)
                .onErrorResume(throwable -> {
                    log.error("Error connecting to gateway", throwable);
                    return Mono.empty();
                })
                .subscribe();
    }

    private void subscribeLastStatus() {
        log.info("Subscribing to current status");
        final var requestBody = ChargingPlugGatewaySubscribeRequestBodyDTO.builder()
                .path("http://localhost:8081")
                .uri("/current-status")
                .eventType(ChargingPlugStationEventType.LAST_STATUS)
                .build();

        WebClient.create(gatewayPath)
                .post()
                .uri(subscribeUri)
                .accept(MediaType.ALL)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::is2xxSuccessful, clientResponse -> {
                    log.info("Subscribing current status success");
                    return Mono.empty();
                })
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    log.error("Error subscribing for current status");
                    return Mono.empty();
                })
                .bodyToMono(Void.class)
                .onErrorResume(throwable -> {
                    log.error("Error connecting to gateway", throwable);
                    return Mono.empty();
                })
                .subscribe();
    }
}
