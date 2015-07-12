package com.thirtygames.zero.common.system;

import lombok.extern.slf4j.Slf4j;

import org.junit.Ignore;
import org.junit.Test;

import com.thirtygames.zero.common.etc.system.SystemInfo;

@Slf4j
public class ServerInfoTest {

	@Ignore
	@Test
    public void testServerInfo() throws Exception {
		SystemInfo serverInfo = SystemInfo.getInstance();
		log.debug("hostname ::::::::::::::" + serverInfo.getHostname());
    }
	
}
