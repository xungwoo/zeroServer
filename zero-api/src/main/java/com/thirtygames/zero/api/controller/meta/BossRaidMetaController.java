package com.thirtygames.zero.api.controller.meta;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.google.common.collect.Lists;
import com.thirtygames.zero.api.controller.common.ApiMetaController;
import com.thirtygames.zero.api.validator.meta.BossRaidMetaValidator;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.BossRaid;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.service.meta.BossRaidMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/meta/boss-raids")
public class BossRaidMetaController extends ApiMetaController<BossRaid, String, BossRaidMetaService, BossRaidMetaValidator> {
	
	@RequestMapping(value = "/multi", method = { RequestMethod.POST })
	public @ResponseBody ApiJsonResult<BossRaid> addMulti(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestBody List<BossRaid> bossRaidList, 
			BindingResult bindingResult, 
			SessionStatus status)  {
		
		ApiJsonResult<BossRaid> result = new ApiJsonResult<BossRaid>();
		result.setResultCount(service.multiAdd(bossRaidList));
		return result;
	}

	
	
	@RequestMapping(value = "/langs/{lang}", method = { RequestMethod.GET })
	public @ResponseBody
	List<BossRaid> rangeByLang(@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, 
			@RequestHeader("myClientVersion") String myClientVersion, 
			@RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestHeader("myMetaRevision") int myMetaRevision,
			@PathVariable("lang") String lang)
			throws RestException {
		BossRaid bossRaid = new BossRaid();
		bossRaid.setLang(lang);
		
		List<BossRaid> resultList = Lists.newArrayList();
		
		List<BossRaid> bossMetaList = service.search(bossRaid);
		if (!bossMetaList.isEmpty()) {
			resultList.addAll(bossMetaList);
		}
		
		List<BossRaid> eventMetaList = service.getEventBossMetaListByLang(lang);
		if (!eventMetaList.isEmpty()) {
			resultList.addAll(eventMetaList);
		}
		
		return resultList;
	}	

}
