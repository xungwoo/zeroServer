package com.thirtygames.zero.common.secret;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

import com.thirtygames.zero.common.etc.secret.ClientInfoManager;

@Slf4j
public class CertificationTest {

	@Test
	public void SecretTest() throws Exception {
		ClientInfoManager clientInfo = ClientInfoManager.getInstance();
		String result = clientInfo.TripleDesDecrypt("maqvAo9oRYtKteCM5DA+TKREBlgOhWJVurcFrA7iLh4vwYLr+UumMSi+vkg8+Brn9VGzJUYhk8Y=]");
		System.out.println("result::" + result);
		
		
		
	}

}
