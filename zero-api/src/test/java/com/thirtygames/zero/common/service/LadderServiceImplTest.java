package com.thirtygames.zero.common.service;

import junit.framework.Assert;
import lombok.extern.slf4j.Slf4j;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thirtygames.zero.common.model.Ladder;
import com.thirtygames.zero.common.model.LadderMatch;
import com.thirtygames.zero.common.service.log.LadderMatchLogService;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/rest-dispatcher-servlet.xml" })
public class LadderServiceImplTest {
	
	@Autowired
	LadderService ladderService;
	
	@Autowired
	LadderMatchLogService matchLogService;
	
	
	@Test
	@Ignore
	public void ladderPointTest()  {
		
		String myId = "f6dbd435-154b-4505-8a97-adc9f8a3b68a";
		String opId = "aa8ba0b0-28cb-4a61-9249-e777f267f9c0";
		String roomNo = "123123";
		Integer gameNo = 123123;
		Long timeStamp = 123123l;

		// Add pvp log.
		Ladder ladder = new Ladder();
		ladder.setAccountId(myId);
		ladder.setOpponentId(opId);
		ladder.setTimeStamp(timeStamp);
		ladder.setGameNo(gameNo);
		ladder.setRoomNo(roomNo);
		ladder.setIsWin(true);
		ladder.setMyLadder(1000);
		ladder.setOpLadder(1000);
		ladder.setPlayTime(20);		
		
		LadderMatch matchResult = ladderService.calcLadderPoint(ladder);
		int winnerPoint = matchResult.getWinnerPoint();
		int looserPoint = matchResult.getLooserPoint();
		Assert.assertEquals(30, winnerPoint);
		Assert.assertEquals(1, looserPoint);
		log.debug("Result:::" + winnerPoint + " :: " + looserPoint);
		
		ladder.setIsWin(true);
		ladder.setMyLadder(1000);
		ladder.setOpLadder(1009);
		
		matchResult = ladderService.calcLadderPoint(ladder);
		winnerPoint = matchResult.getWinnerPoint();
		looserPoint = matchResult.getLooserPoint();
		Assert.assertEquals(37, winnerPoint);
		Assert.assertEquals(1, looserPoint);		
		log.debug("Result:::" + winnerPoint + " :: " + looserPoint);
		
		ladder.setIsWin(false);
		ladder.setMyLadder(1000);
		ladder.setOpLadder(1009);
		
		matchResult = ladderService.calcLadderPoint(ladder);
		winnerPoint = matchResult.getWinnerPoint();
		looserPoint = matchResult.getLooserPoint();
		Assert.assertEquals(30, winnerPoint);
		Assert.assertEquals(1, looserPoint);		
		log.debug("Result:::" + winnerPoint + " :: " + looserPoint);
		
		ladder.setIsWin(true);
		ladder.setMyLadder(1070);
		ladder.setOpLadder(1000);
		
		matchResult = ladderService.calcLadderPoint(ladder);
		winnerPoint = matchResult.getWinnerPoint();
		looserPoint = matchResult.getLooserPoint();
		Assert.assertEquals(14, winnerPoint);
		Assert.assertEquals(1, looserPoint);		
		log.debug("Result:::" + winnerPoint + " :: " + looserPoint);
		
		ladder.setIsWin(false);
		ladder.setMyLadder(1070);
		ladder.setOpLadder(1000);
		
		matchResult = ladderService.calcLadderPoint(ladder);
		winnerPoint = matchResult.getWinnerPoint();
		looserPoint = matchResult.getLooserPoint();
		Assert.assertEquals(83, winnerPoint);
		Assert.assertEquals(1, looserPoint);		
		log.debug("Result:::" + winnerPoint + " :: " + looserPoint);
	}

}
