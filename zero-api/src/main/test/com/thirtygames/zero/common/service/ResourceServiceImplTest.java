package com.thirtygames.zero.common.service;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;

import com.thirtygames.zero.common.model.AccountResource;

@Slf4j
public class ResourceServiceImplTest {
	
	@Test
	public void titleBundleTest() {
		AccountResource ar = new AccountResource();
		ar.setTitleBundle1(0L);
		ar.setTitleBundle2(0L);
		ar.setTitleBundle3(0L);
		
		int n = 1;
		ar.setTitle(n);
		Long temp = (long) Math.pow(2, (n%60)-1);
		log.debug("## result1 temp::" + temp);
		log.debug("## result1 ar.getTitleBundle1()::" + ar.getTitleBundle1());
		Assert.assertEquals(ar.getTitleBundle1(), temp);
		
		
		n = 63;
		ar.setTitle(n);
		temp = (long) Math.pow(2, (n%60)-1);
		log.debug("## result2 temp::" + temp);
		log.debug("## result2 ar.getTitleBundle2()::" + ar.getTitleBundle2());
		Assert.assertEquals(ar.getTitleBundle2(), temp);
		
		n = 127;
		ar.setTitle(n);
		temp = (long) Math.pow(2, (n%60)-1);
		log.debug("## result3 temp::" + temp);
		log.debug("## result3 ar.getTitleBundle3()::" + ar.getTitleBundle3());
		Assert.assertEquals(ar.getTitleBundle3(), temp);
	}
	
	
	@Test
	public void checkTitleTest() {
		AccountResource ar = new AccountResource();
		
		ar.setTitle(8);
		Assert.assertTrue(ar.checkTitle(8));
		ar.setTitle(68);
		Assert.assertTrue(ar.checkTitle(68));
		ar.setTitle(129);
		Assert.assertTrue(ar.checkTitle(129));
		
		ar.setTitleBundle1(0L);
		ar.setTitleBundle2(1L);
		ar.setTitleBundle3(1L);
		
		Assert.assertFalse(ar.checkTitle(8));
		Assert.assertTrue(ar.checkTitle(61));
		Assert.assertTrue(ar.checkTitle(121));
		Assert.assertFalse(ar.checkTitle(123));
		

		ar.setTitle(180);
		Assert.assertFalse(ar.checkTitle(180));
	}

}
