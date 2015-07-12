
package com.thirtygames.zero.api.controller.meta;
import java.util.List;

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

import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.model.meta.ApiMeta;
import com.thirtygames.zero.common.model.meta.ServerInfo;
import com.thirtygames.zero.common.service.meta.ApiMetaService;
import com.thirtygames.zero.common.service.meta.ServerInfoService;

@Slf4j
@Controller
@RequestMapping(value = "/meta")
public class ApiMetaController  {
	
	@Autowired
	ApiMetaService service;
	
	@Autowired
	ServerInfoService serverInfoService;
	
	@RequestMapping(value = "/multi", method = { RequestMethod.POST })
	public @ResponseBody ApiJsonResult<ApiMeta> addMulti(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestBody List<ApiMeta> metaList, 
			BindingResult bindingResult, 
			SessionStatus status)  {
		
		ApiJsonResult<ApiMeta> result = new ApiJsonResult<ApiMeta>();
		result.setResultCount(service.multiAdd(metaList));
		return result;
	}
	
	@RequestMapping(value = "/clients/{clientPlatform}", method = { RequestMethod.GET })
	public @ResponseBody ApiJsonResult<ApiMeta> clientVersion(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@PathVariable("clientPlatform") String clientPlatform
			)  {
		ApiJsonResult<ApiMeta> result = new ApiJsonResult<ApiMeta>();
		result.setResult(service.get(clientPlatform));
		return result;
	}
	
	@RequestMapping(value = "/servers", method = { RequestMethod.GET })
	public @ResponseBody ApiJsonResult<ServerInfo> servers()  {
		ApiJsonResult<ServerInfo> result = new ApiJsonResult<ServerInfo>();
		result.setResult(serverInfoService.range(0, 0));
		return result;
	}
	
	@RequestMapping(value = "/trevi", method = { RequestMethod.GET })
	public @ResponseBody ApiJsonResult<ApiMeta> encryptionKey()  {
		ApiJsonResult<ApiMeta> result = new ApiJsonResult<ApiMeta>();
		result.setResult(service.range(0,0));
		return result;
	}	

}
