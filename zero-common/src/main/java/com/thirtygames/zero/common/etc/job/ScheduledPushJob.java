package com.thirtygames.zero.common.etc.job;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class ScheduledPushJob {

	
	@Scheduled(cron="*/5 * * * * ?")
    public void demoServiceMethod()
    {
        System.out.println("Method executed at every 5 seconds. Current time is :: "+ new Date());
        log.debug("********************************************************************");
        log.debug("********************************************************************");
        log.debug("********************************************************************");
        log.debug("Method executed at every 5 seconds. Current time is :: "+ new Date());
    }
}
