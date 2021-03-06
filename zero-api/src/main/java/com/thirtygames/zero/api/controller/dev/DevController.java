package com.thirtygames.zero.api.controller.dev;

import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.common.etc.datasource.ContextHolder;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.Unit;
import com.thirtygames.zero.common.service.AccountService;
import com.thirtygames.zero.common.service.UnitService;


@Slf4j
@Controller
@RequestMapping(value = "/dev")
public class DevController {
	
	
	
	@Autowired
	UnitService unitService;

	@Autowired
	AccountService accountService;

	@Test
	public void createAllUnitTest()  {
		
		DataSource[] dataSourceType = { DataSource.ZERO_GAME_1, DataSource.ZERO_GAME_2 };
		String searchAccount = "leesang";
		Integer unitType[] = { 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115 };

		int addUnitCount = 0;
		int accountCount = 0;

		Account a = new Account();
		for (DataSource dst : dataSourceType) {
			ContextHolder.setDataSource(dst);
			a.setChannelId(searchAccount);
			List<Account> accountList = accountService.search(0, 0, a);
			accountCount += accountList.size();
			log.debug("aList:::" + accountList);

			Unit unit = new Unit();
			unit.setLevel(Unit.DEFAULT_LEVEL);
			unit.setSkill0Lv(Unit.DEFAULT_LEVEL);
			unit.setSkill1Lv(Unit.DEFAULT_LEVEL);
			unit.setSkill2Lv(Unit.DEFAULT_LEVEL);
			unit.setSkill3Lv(Unit.DEFAULT_LEVEL);

			for (Account account : accountList) {
				unit.setAccountId(account.getAccountId());
				ContextHolder.setDataSource(DataSource.getAccountDS(account.getAccountId()));

				Unit unitParam = new Unit();
				unitParam.setAccountId(account.getAccountId());
				List<Unit> unitList = unitService.search(0, 0, unitParam);

				for (Integer code : unitType) {
					boolean isExistUnitType = false;
					for (Unit existUnit : unitList) {
						if (existUnit.getUnitType().equals(code)) {
							isExistUnitType = true;
						}
					}

					if (!isExistUnitType) {
						unit.setUnitType(code);
						unit.setUnitId(UUID.randomUUID().toString());
						unitService.save(unit);
						addUnitCount++;
					}
				}
			}
			ContextHolder.clear();
		}

		log.debug("==========================================================================");
		log.debug("Target Account Count::" + accountCount);
		log.debug("Add Unit Count::" + addUnitCount);
		log.debug("==========================================================================");

	}	
	
}