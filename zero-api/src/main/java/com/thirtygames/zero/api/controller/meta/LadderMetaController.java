package com.thirtygames.zero.api.controller.meta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.model.meta.LeagueCount;
import com.thirtygames.zero.common.model.meta.LeagueMeta;
import com.thirtygames.zero.common.model.meta.LeaguePrize;
import com.thirtygames.zero.common.service.meta.LeagueCountService;
import com.thirtygames.zero.common.service.meta.LeagueMetaService;
import com.thirtygames.zero.common.service.meta.LeaguePrizeService;

@Slf4j
@Controller
@RequestMapping(value = "/meta/ladder")
public class LadderMetaController {
	
	@Autowired
	LeagueMetaService metaService;
	
	@Autowired
	LeaguePrizeService prizeService;
	
	@Autowired
	LeagueCountService countService;
	
	@RequestMapping(method = { RequestMethod.GET })
	public @ResponseBody Map<String, Object> ladderMeta(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, 
			@RequestHeader("myClientVersion") String myClientVersion, 
			@RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestHeader("myMetaRevision") int myMetaRevision
			)  {
		Map<String, Object> ladderMap = new HashMap<String, Object>();
		ladderMap.put("leagueMeta", metaService.rangeByCache(0, 0, myMetaRevision));
		ladderMap.put("leaguePrize", prizeService.rangeByCache(0, 0, myMetaRevision));
		return ladderMap; 
	}
	
	@RequestMapping(value="/leagues/{league}/countries/{country}",  method = { RequestMethod.GET })
	public @ResponseBody ApiJsonResult<Object> leagueCount(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@PathVariable("league") Integer league,
			@PathVariable("country") String country
			)  {
		ApiJsonResult<Object> result = new ApiJsonResult<Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		int count = 0;
		LeagueCount lc = countService.get(league);
		if (lc != null && lc.getCount() > 0) {
			count = lc.getCount();
		}
		resultMap.put("leagueCount", count);
		resultMap.put("isLadderAI", metaService.isLadderAI(country));
		
		result.setResult(resultMap);
		return result; 
	}
	
	
	@RequestMapping(value = "/league/multi", method = { RequestMethod.POST })
	public @ResponseBody ApiJsonResult<LeagueMeta> addMultiMeta(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestBody List<LeagueMeta> list, 
			BindingResult bindingResult, 
			SessionStatus status)  {
		
		ApiJsonResult<LeagueMeta> result = new ApiJsonResult<LeagueMeta>();
		result.setResultCount(metaService.multiAdd(list));
		return result;
	}	
	
	@RequestMapping(value = "/league/prize/multi", method = { RequestMethod.POST })
	public @ResponseBody ApiJsonResult<LeaguePrize> addMultiPrize(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestBody List<LeaguePrize> list, 
			BindingResult bindingResult, 
			SessionStatus status)  {
		
		ApiJsonResult<LeaguePrize> result = new ApiJsonResult<LeaguePrize>();
		result.setResultCount(prizeService.multiAdd(list));
		return result;
	}	

}