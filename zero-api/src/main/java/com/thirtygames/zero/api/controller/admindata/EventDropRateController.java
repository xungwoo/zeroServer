
package com.thirtygames.zero.api.controller.admindata;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.admindata.EventDropRate;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.service.AccountService;
import com.thirtygames.zero.common.service.admindata.EventDropRateService;

@Slf4j
@Controller
@RequestMapping(value = "/meta/event/drop-rate/")
public class EventDropRateController  {
	
	@Autowired
	EventDropRateService service;
	
	@Autowired
	AccountService accountService;
	
	@RequestMapping(value="/day-light/{saving}", method = { RequestMethod.GET })
	public @ResponseBody ApiJsonResult<EventDropRate> eventDropRate(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform,
			@PathVariable("saving") int saving)  {
		
		ApiJsonResult<EventDropRate> result = new ApiJsonResult<EventDropRate>();
		
		Account account = accountService.get(myAccountId);
		ValidationUtil.isNullModel(account, " accountId:" + myAccountId);
		int localTimeZone = account.getLocalTimeZone();
		if (saving == 1) {
			localTimeZone = localTimeZone + EventDropRate.DAY_LIGHT_SAVING_SEC;
		}
		result.setResult(service.localEventDropRateList(localTimeZone));
		return result;
	}
			
	


}
