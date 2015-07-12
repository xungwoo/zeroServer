package com.thirtygames.zero.common.service;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

import com.thirtygames.zero.common.model.Account;

@Slf4j
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = { "/rest-dispatcher-servlet.xml" })
public class AccountServiceImplTest {

	@Test
	public void generateAccountTest() {
		int result[] = { 0, 0, 0, 0 };
		Account account = new Account();
		for (int j = 0; j <= 3; j++) {
			for (int i = 0; i <= 100000; i++) {
//				if (account.generateAccountId() == 1) {
//					result[j]++;
//				} else {
//					result[j]--;
//				}
			}
		}

		log.debug("result::" + (result[0] + result[1] + result[2] + result[3]));
		
		
		for (int j = 0; j <= 3; j++) {
			for (int i = 0; i <= 100000; i++) {
				int a = (int) System.currentTimeMillis();
				if (a%2 == 1) {
					result[j]++;
				} else {
					result[j]--;
				}
			}
		}
		
		log.debug("result::" + (result[0] + result[1] + result[2] + result[3]));
	}

}
