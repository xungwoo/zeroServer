
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
import com.thirtygames.zero.api.validator.meta.AchievementMetaValidator;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.Achievement;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.service.meta.AchievementMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/meta/achievement")
public class AchievementMetaController extends ApiMetaController<Achievement, Integer, AchievementMetaService, AchievementMetaValidator> {
	
	@RequestMapping(value = "/multi", method = { RequestMethod.POST })
	public @ResponseBody ApiJsonResult<Achievement> addMulti(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, 
			@RequestHeader("myClientVersion") String myClientVersion, 
			@RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestBody List<Achievement> missionList, 
			BindingResult bindingResult, 
			SessionStatus status)  {
		
		ApiJsonResult<Achievement> result = new ApiJsonResult<Achievement>();
		result.setResultCount(service.multiAdd(missionList));
		return result;
	}


}
