package com.thirtygames.zero.api.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.thirtygames.zero.api.controller.common.ApiGenericController;
import com.thirtygames.zero.api.validator.LadderValidator;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.CalendarUtil;
import com.thirtygames.zero.common.model.Friend;
import com.thirtygames.zero.common.model.Ladder;
import com.thirtygames.zero.common.model.LadderMatch;
import com.thirtygames.zero.common.model.ResetDateInfo;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.model.meta.LeagueCount;
import com.thirtygames.zero.common.model.meta.LeagueMeta;
import com.thirtygames.zero.common.service.AccountResourceService;
import com.thirtygames.zero.common.service.DeckService;
import com.thirtygames.zero.common.service.FriendService;
import com.thirtygames.zero.common.service.LadderOpponentService;
import com.thirtygames.zero.common.service.LadderService;
import com.thirtygames.zero.common.service.datasource.DataSourceService;
import com.thirtygames.zero.common.service.log.LadderMatchLogService;
import com.thirtygames.zero.common.service.meta.LeagueCountService;
import com.thirtygames.zero.common.service.meta.LeagueMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/ladder")
public class LadderController extends ApiGenericController<Ladder, String, LadderService, LadderValidator> {

	@Autowired
	FriendService frService;

	@Autowired
	AccountResourceService arService;

	@Autowired
	LadderMatchLogService matchLogService;

	@Autowired
	DeckService deckService;

	@Autowired
	DataSourceService dsManager;

	@Autowired
	LeagueCountService countMetaService;
	
	@Autowired
	LeagueMetaService leagueMetaService;
	
	@Autowired
	LadderOpponentService ladderOpponentService;

	@RequestMapping(value = "/match", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Object> matchUpdate(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, @RequestHeader("myMetaRevision") int myMetaRevision, 
			@ModelAttribute Ladder ladder,
			BindingResult bindingResult, SessionStatus status) throws CloneNotSupportedException {
		
		ApiJsonResult<Object> result = new ApiJsonResult<Object>();
		
		LadderMatch ladderLog = new LadderMatch();
		ladderLog.setAccountId(myAccountId);
		ladderLog.setLeague(ladder.getLeague());
		ladderLog.setResetDate(ladder.getResetDate());
		List<LadderMatch> ladderLogList = matchLogService.search(ladderLog);
		if (ladderLogList.isEmpty()) {
			countMetaService.increase(ladder.getLeague(), ladder.getResetDate());
		}
		
		LeagueMeta leagueMeta = leagueMetaService.getByCache(ladder.getLeague(), myMetaRevision);
		ladder.setAccountId(myAccountId);
		
		result.setResult(service.matchResult(ladder, leagueMeta));
		result.setResource(arService.get(myAccountId));
		return result;
	}
	
	
	

	@Override
	protected Ladder preSearch(Ladder entity, String accountId)  {
		entity.setAccountId(accountId);
		return entity;
	}

	@RequestMapping(value = "/leagues/reward", method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Object> leagueReward(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(value = "prevRank", defaultValue = "0") long prevRank, 
			@RequestParam(required = true, value = "currentResetDate") String currentResetDate)  {
		ApiJsonResult<Object> result = new ApiJsonResult<Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		TimeZone utc = TimeZone.getTimeZone("Asia/Seoul");
		int year = Integer.parseInt(currentResetDate.substring(0, 4));
		int month = Integer.parseInt(currentResetDate.substring(4, 6));
		int date = Integer.parseInt(currentResetDate.substring(6, 8));

		Calendar resetCalendar = Calendar.getInstance(utc);
		resetCalendar.set(year, month - 1, date);
		log.debug("resetCalendar::" + CalendarUtil.toString(resetCalendar, "yyyyMMdd", utc));

		Calendar nowCalendar = Calendar.getInstance(utc);
		nowCalendar.add(Calendar.HOUR, -1);
		log.debug("nowCalendar::" + CalendarUtil.toString(nowCalendar, "yyyyMMdd", utc));

		Calendar nextWeekCalendar = Calendar.getInstance(utc);
		nextWeekCalendar.set(year, month - 1, date);
		nextWeekCalendar.add(Calendar.DATE, 7);
		log.debug("nextWeekCalendar::" + CalendarUtil.toString(nextWeekCalendar, "yyyyMMdd", utc));

		if (resetCalendar.before(nowCalendar) || resetCalendar.after(nextWeekCalendar)) {
			throw new RestException("Invalid.resetDate");
		}

		int league = 1;
		String myResetDate = "";
		Ladder myLadder = service.get(myAccountId);
		if (myLadder != null) {
			myResetDate = myLadder.getResetDate();
			if (myLadder.getLeague() > 0)
				league = myLadder.getLeague();
		}
		log.debug("myResetDate::" + myResetDate);
		log.debug("myLadder::" + myLadder);

		if (currentResetDate.equals(myResetDate)) {
			throw new RestException(Errors.NotPromotion);
		}
		
		LeagueCount countMeta = countMetaService.getPrevLeagueCount(league, currentResetDate);
		if (countMeta == null || countMeta.getCount() <= 0) {
			throw new RestException(Errors.NotPromotion);
		}
		log.debug("prev countMeta::" + countMeta);
		
		int totalCount = countMeta.getCount();
		resultMap = service.leagueResult(myAccountId, prevRank, currentResetDate, league, totalCount);
		result.setResult(resultMap);
		result.setResource(arService.get(myAccountId));
		return result;
	}

	@RequestMapping(value = "/ai/match/{league}/{ladder}", method = { RequestMethod.GET })
	public @ResponseBody
	ApiJsonResult<Friend> matchAI(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, @PathVariable("league") int league,
			@PathVariable("ladder") int ladder)  {
		ApiJsonResult<Friend> result = new ApiJsonResult<Friend>();

		Friend aiFriend = frService.matchingAI(myAccountId, league, ladder);
		result.setResult(aiFriend);
		return result;
	}
	
	

	@RequestMapping(value = "/reset-date", method = { RequestMethod.GET })
	public @ResponseBody
	ApiJsonResult<ResetDateInfo> reset(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp)  {
		ApiJsonResult<ResetDateInfo> result = new ApiJsonResult<ResetDateInfo>();
		result.setResult(service.getResetDateInfo());
		return result;
	}	
	

	@Override
	protected Ladder preUpdate(Ladder ladder, String accountId)  {
		throw new RestException("not.allow.request");
	}

	@Override
	protected Ladder preAdd(Ladder ladder, String accountId)  {
		throw new RestException("not.allow.request");
	}

}
