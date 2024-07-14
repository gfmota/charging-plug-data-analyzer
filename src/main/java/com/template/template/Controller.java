package com.template.template;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController("/")
@Slf4j
public class Controller {
    @PostMapping("/daily-report")
    private ResponseEntity<?> postChargingPlugDailyReport(final @RequestBody Object dailyReport) {
        log.info("Received daily report: {}", dailyReport);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/current-status")
    private ResponseEntity<?> postChargingPlugStationCurrentStatus(final @RequestBody String currentStatus) {
        log.info("Received current status: {}", currentStatus);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/hourly-report")
    private ResponseEntity<?> postChargingPlugHourlyReport(final @RequestBody Object hourlyReport) {
        log.info("Received hourly report: {}", hourlyReport);
        return ResponseEntity.ok().build();
    }
}
