package com.thirtygames.zero.api.controller.meta;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thirtygames.zero.api.controller.common.ApiMetaController;
import com.thirtygames.zero.api.validator.meta.StageMetaValidator;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.model.meta.Stage;
import com.thirtygames.zero.common.service.meta.StageMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/meta/stages")
public class StageMetaController extends ApiMetaController<Stage, String, StageMetaService, StageMetaValidator> {
	
	@RequestMapping(value = "/{chapter}/{stage}", method = { RequestMethod.GET })
	public @ResponseBody
	ApiJsonResult<Stage> get(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, 
			@PathVariable("chapter") int chapter,
			@PathVariable("stage") int stage)  {
		
		Stage paramModel = new Stage();
		paramModel.setChapter(chapter);
		paramModel.setStage(stage);
		
		List<Stage> resultList = service.search(0, 0, paramModel);
		return new ApiJsonResult<Stage>(resultList);
	}

	
}