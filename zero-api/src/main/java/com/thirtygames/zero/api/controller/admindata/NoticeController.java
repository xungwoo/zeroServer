package com.thirtygames.zero.api.controller.admindata;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thirtygames.zero.api.controller.common.ApiGenericController;
import com.thirtygames.zero.api.validator.meta.NoticeValidator;
import com.thirtygames.zero.common.model.admindata.Notice;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.service.admindata.NoticeService;

@Slf4j
@Controller
@RequestMapping(value = "/meta/notice")
public class NoticeController extends ApiGenericController<Notice, Integer, NoticeService, NoticeValidator> {

	@RequestMapping(value = "/langs/{lang}", method = { RequestMethod.GET })
	public @ResponseBody
	ApiJsonResult<Notice> lang(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, 
			@PathVariable("lang") String lang)  {
		
		ApiJsonResult<Notice> result = new ApiJsonResult<Notice>();
		Notice notice = new Notice();
		notice.setLang(lang);
		result.setResult(service.search(notice));
		return postGet(result);
	}		
	
	@Override
	protected Notice preSearch(Notice entity, String accountId)  {
		return entity;
	}
	
	@Override
	protected Notice preAdd(Notice entity, String accountId)  {
		return null;
	}

	@Override
	protected Notice preUpdate(Notice entity, String accountId)  {
		return null;
	}
	


}