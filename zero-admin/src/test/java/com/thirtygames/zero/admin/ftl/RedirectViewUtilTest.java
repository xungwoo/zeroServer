package com.thirtygames.zero.admin.ftl;

import junit.framework.Assert;

import org.junit.Test;

public class RedirectViewUtilTest {


	@Test
	public void getRedirectListUrlTest() {
		
		Assert.assertEquals("zero/test/list/1", RedirectViewUtil.getRedirectListUrl("zero/test/delete"));
		Assert.assertEquals("zero/test/list/1", RedirectViewUtil.getRedirectListUrl("zero/test/list"));
		Assert.assertEquals("zero/test/list/1", RedirectViewUtil.getRedirectListUrl("zero/test///"));
	}	
}
