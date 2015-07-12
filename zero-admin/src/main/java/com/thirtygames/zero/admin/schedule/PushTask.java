package com.thirtygames.zero.admin.schedule;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.thirtygames.zero.common.service.hsp.HSPPushManager;
import com.thirtygames.zero.common.service.meta.BossRaidMetaService;

@Slf4j
@Component
public class PushTask {
	
	@Autowired
	HSPPushManager pushManager;
	
	@Autowired
	BossRaidMetaService bossRaidMetaService;
	
	@Scheduled(fixedDelay=600000)
	public void pushEventBoss() {
		
//		List<BossRaid> eventBossList = bossRaidMetaService.getEventBossList4Push();
//		for(BossRaid eventBoss : eventBossList) {
//			HSPPushResponse res = pushManager.sendAllPush(eventBoss.getPush);
//			if (res.getStatus() == 0) {
//				bossRaidMetaService.eventBossPushLog(eventBoss.getBossRaidMetaId());
//				log.debug("Push Log Complete.");
//			}
//		}
	}
	

}
