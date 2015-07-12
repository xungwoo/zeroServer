package com.thirtygames.zero.common.etc.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.thirtygames.zero.common.etc.datasource.ContextHolder;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.secret.ClientInfoManager;
import com.thirtygames.zero.common.model.meta.ClientInfo;
import com.thirtygames.zero.common.service.AccountService;
import com.thirtygames.zero.common.service.meta.ApiMetaService;
import com.thirtygames.zero.common.service.meta.ClientInfoService;

@Slf4j
@Component
public class CertificateInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ApiMetaService apiMetaService;
	
	@Autowired
	private ClientInfoService clientInfoService;	
	
	
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.debug("#############@@@@ Certificate Interceptor preHandle!!");
		boolean isDebug = false;
		
		String accountId = request.getHeader("myAccountId");
		String accessToken = request.getHeader("myAccessToken");
		String clientVersion = request.getHeader("myClientVersion");
		String clientPlatform = request.getHeader("myClientPlatform");
		String timeStamp = request.getHeader("myTimeStamp");
		if (accountId == null || accessToken == null || clientVersion == null || clientPlatform == null || timeStamp == null) {
			throw new RestException(HttpServletResponse.SC_BAD_REQUEST, "Invalid.Headers");
		}
		
		log.debug("myTimeStamp::" + timeStamp);
		ClientInfoManager clientManager = ClientInfoManager.getInstance();
		List<String> tsList = clientManager.validate(accountId, timeStamp, clientPlatform, clientVersion, accessToken);
		String revision = tsList.get(1);
		String clientHash = tsList.get(2);
		
		String systemRunMode = System.getProperty("mode");
		if (("alpha".equals(systemRunMode) || "local".equals(systemRunMode) || "dev".equals(systemRunMode) || "test".equals(systemRunMode)) && ("FANTASTIC30GAMES".equals(clientHash) || "[Fantastic 30Games!]".equals(accessToken))) {
			isDebug = true;
			log.debug("############# Debug MODE:" + isDebug);
			log.debug("############# System Run Mode:" + systemRunMode);
		}

		if (!isDebug) {
			ClientInfo clientInfo = new ClientInfo();
			clientInfo.setClientVersion(clientVersion);
			clientInfo.setClientPlatform(clientPlatform);
			clientInfo.setClientHash(clientHash);
			List<ClientInfo> clientInfoList = clientInfoService.searchByCache(clientInfo, Integer.valueOf(revision));
			log.debug("clientInfoList::" + clientInfoList);
			if (clientInfoList.isEmpty()) {
				throw new RestException(HttpServletResponse.SC_METHOD_NOT_ALLOWED, Errors.InvalidClientInfo);
			}		
		}
		
		ContextHolder.setAccount(accountId);
		
		if (!isDebug) {
			String token = accountService.getToken(accountId);
			if (token == null) throw new RestException(HttpServletResponse.SC_GONE, "No account found!");
			if (!accessToken.equals(token)) throw new RestException(HttpServletResponse.SC_UNAUTHORIZED, "Certification failed!");
		}

		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		log.debug("#############@@@@ Certificate Interceptor afterCompletion!!");
		ContextHolder.clear();
		super.afterCompletion(request, response, handler, ex);
	}
}
