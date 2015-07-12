package com.thirtygames.zero.admin.ftl;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;

@Slf4j
public class RedirectViewUtil {
	
	public static RedirectView getRedirectView(String redirectUrl, String refererUri) {
		return Strings.isNullOrEmpty(redirectUrl) ? RedirectViewUtil.getDefaultRedirectView(refererUri) : new RedirectView(redirectUrl);
	}

	public static RedirectView getDefaultRedirectView(HttpServletRequest request) {
		return new RedirectView(getRedirectListUrl(request.getRequestURI()));
	}

	public static RedirectView getDefaultRedirectView(String uri) {
		return new RedirectView(getRedirectListUrl(uri));
	}

	public static String getRedirectListUrl(String uri) {
		int delStrIndex = uri.indexOf("/delete");
		if (delStrIndex > 0) {
			uri = uri.substring(0, delStrIndex);
		}
		
		uri = CharMatcher.anyOf("/").trimTrailingFrom(uri);
		
		int listStrIndex = uri.indexOf("/list");
		if (listStrIndex < 0) {
			uri = Joiner.on("/").join(uri, "list");
		}
		uri = Joiner.on("/").join(uri, "1");
		return uri;
	}	
}
