package com.thirtygames.zero.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.thirtygames.zero.api.controller.common.ApiGenericController;
import com.thirtygames.zero.api.validator.DeckValidator;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.Deck;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.service.DeckService;
import com.thirtygames.zero.common.service.FriendService;

@Slf4j
@Controller
@RequestMapping(value = "/decks")
public class DeckController extends ApiGenericController<Deck, String, DeckService, DeckValidator> {
	
	@Autowired
	FriendService frService;

	@Override
	protected Deck preSearch(Deck deck, String accountId)  {
		deck.setAccountId(accountId);
		return deck;
	}
	
	@Override
	protected ApiJsonResult<Deck> postSearch(ApiJsonResult<Deck> result)  {
		List<Deck> resultList = result.getResultList();
		result.setResult(getDeckJson(resultList));
		return result;
	}

	@RequestMapping(value = "/team-update", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Deck> update(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, @ModelAttribute Deck deck, BindingResult bindingResult, SessionStatus status)  {
		validator.validate(deck, bindingResult);
		validator.validateUpdateTeam(deck, bindingResult);
		validator.processErrors(bindingResult);
		status.setComplete();

		deck.setAccountId(myAccountId);
		service.teamUpdate(deck);
		
		ApiJsonResult<Deck> result = new ApiJsonResult<Deck>();
		result.setResult(getDeckJson(service.getDeckUnitList(myAccountId)));
		return result;
	}

	private Map<Integer, Map<Integer, String>> getDeckJson(List<Deck> resultList) {
		Map<Integer, Map<Integer, String>> teamMap = new HashMap<Integer, Map<Integer, String>>();
		for (int i = 1; i <= Deck.TEAM_MAX; i++) {
			Map<Integer, String> unitMap = new HashMap<Integer, String>();
			for (Deck deck : resultList) {
				unitMap.put(deck.getPositionId(), deck.getUnitId());
			}

			teamMap.put(i, unitMap);
		}

		return teamMap;
	}

	@Override
	protected Deck preAdd(Deck entity, String accountdId)  {
		throw new RestException("not.allow.request");
	}

	@Override
	protected Deck preUpdate(Deck entity, String accountdId)  {
		throw new RestException("not.allow.request");
	}
}
