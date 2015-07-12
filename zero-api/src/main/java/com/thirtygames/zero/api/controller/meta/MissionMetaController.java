package com.thirtygames.zero.api.controller.meta;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.thirtygames.zero.api.controller.common.ApiMetaController;
import com.thirtygames.zero.api.validator.meta.MissionMetaValidator;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.Mission;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.service.meta.MissionMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/meta/mission")
public class MissionMetaController extends ApiMetaController<Mission, Integer, MissionMetaService, MissionMetaValidator> {
	
	@RequestMapping(value = "/multi", method = { RequestMethod.POST })
	public @ResponseBody ApiJsonResult<Mission> addMulti(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestBody List<Mission> missionList, 
			BindingResult bindingResult, 
			SessionStatus status)  {
		
		ApiJsonResult<Mission> result = new ApiJsonResult<Mission>();
		result.setResultCount(service.multiAdd(missionList));
		return result;
	}


}
