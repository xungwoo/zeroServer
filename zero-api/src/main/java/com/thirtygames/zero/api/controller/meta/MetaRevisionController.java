package com.thirtygames.zero.api.controller.meta;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.model.meta.MetaRevision;
import com.thirtygames.zero.common.service.meta.MetaRevisionService;

@Slf4j
@Controller
@RequestMapping(value = "/meta/revisions")
public class MetaRevisionController {
	
	@Autowired
	MetaRevisionService service;

	@RequestMapping(method = { RequestMethod.GET })
	public @ResponseBody
	ApiJsonResult<MetaRevision> range() throws RestException {

		ApiJsonResult<MetaRevision> result = new ApiJsonResult<MetaRevision>();
		result.setResult(service.range(0, 0));
		return result;
	}
	
}
