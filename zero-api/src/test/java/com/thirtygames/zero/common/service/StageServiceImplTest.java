package com.thirtygames.zero.common.service;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thirtygames.zero.common.etc.datasource.ContextHolder;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.model.StageClear;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/rest-dispatcher-servlet.xml" })
public class StageServiceImplTest {
	
	@Autowired
	StageService stageService;
	
	
	@Ignore
	@Test
	public void isBreakRecordTest()  {
		String testId = "f6dbd435-154b-4505-8a97-adc9f8a3b68a";
		//ContextHolder.setDataSource(DataSourceService.getDataSource(testId));
		ContextHolder.setDataSource(DataSource.ZERO_GAME_1);
		String recordStageKey = "004_004";
		
		StageClear stageClear = new StageClear();
		stageClear.setAccountId(testId);
		stageClear.setStageKey(recordStageKey);
		
		stageClear.setClearMode(1000);
		stageClear.setClearStep(1);
		stageClear.setClearProgress();
		stageClear.setExposedScenes(0);
		stageService.saveStageClear(stageClear);

		Assert.assertEquals(false, stageService.isBreakStageClearRecord(testId, "002_001", 1000));
	}

}
