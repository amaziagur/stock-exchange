package com.foresightautomotive.stock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PublicStockUpdaterScheduler {

    private PublicStockDataPopulatorService populatorService;

    @Autowired
    public PublicStockUpdaterScheduler(PublicStockDataPopulatorService populatorService){
        this.populatorService = populatorService;
    }

    @Scheduled(cron = "${foresight.cron.refresh}")
    public void updatePrices() {
        log.info("going to call update prices");
        this.populatorService.refreshPrices();
        log.info("successfully update prices");
    }
}
