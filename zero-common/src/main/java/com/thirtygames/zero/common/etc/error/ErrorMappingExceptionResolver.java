package com.thirtygames.zero.common.etc.error;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.thirtygames.zero.common.etc.exception.RestException;

/**
 * ErrorMappingExceptionResolver
 *
 */
@Slf4j
public class ErrorMappingExceptionResolver extends SimpleMappingExceptionResolver {

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		int errorCode = 0;
		
		log.error("## Exception getCause:", ex.getCause());
		log.error("## Exception toString:", ex.toString());
		log.error(ex.getMessage() + " request parameter:" + getParameterString(request.getParameterMap()) );
		
		PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        int status  = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        
        if (ex instanceof NoSuchRequestHandlingMethodException) {
        	status = HttpServletResponse.SC_NOT_FOUND;
		}
		else if (ex instanceof HttpRequestMethodNotSupportedException) {
			status = HttpServletResponse.SC_METHOD_NOT_ALLOWED;
		}
		else if (ex instanceof HttpMediaTypeNotSupportedException) {
			status = HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
		}
		else if (ex instanceof HttpMediaTypeNotAcceptableException) {
			status = HttpServletResponse.SC_NOT_ACCEPTABLE;
		}
		else if (ex instanceof MissingServletRequestParameterException) {
			status = HttpServletResponse.SC_BAD_REQUEST;
		}
		else if (ex instanceof ServletRequestBindingException) {
			status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
		else if (ex instanceof ConversionNotSupportedException) {
			status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
		else if (ex instanceof TypeMismatchException) {
			status = HttpServletResponse.SC_BAD_REQUEST;
		}
		else if (ex instanceof HttpMessageNotReadableException) {
			status = HttpServletResponse.SC_BAD_REQUEST;
		}
		else if (ex instanceof HttpMessageNotWritableException) {
			status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
		else if (ex instanceof RestException ) {
			RestException restEx = (RestException)ex;
			status = restEx.getStatusCode();
			errorCode = restEx.getErrorCode();
		}
        
        String message = ex.getMessage();
        if( message != null ) {
        	message.replace("\\","\\\\");
        	message.replace("\"","\\\"");
        }
        
        response.setStatus(status);
        
        out.print("{\"error\":");
        out.print(status);
        out.print(",");
        out.print("\"code\":");
        out.print(errorCode);
        out.print(",");
        out.print("\"message\":\"");
        out.print(message);
        out.print("\"}");
        out.close();

		return null;
	}
	
	private String getParameterString(Map<String, String[]> parameterMap) {
		StringBuilder builder = new StringBuilder("{");		
		
		if (MapUtils.isEmpty(parameterMap)) {
			builder.append("}");
			return builder.toString();
		}

		for (String key : parameterMap.keySet()) {
			builder.append(key);
			builder.append(":");
			builder.append(ToStringBuilder.reflectionToString((String[])MapUtils.getObject(parameterMap, key), ToStringStyle.SIMPLE_STYLE));
			builder.append(", ");
		}
				
		return StringUtils.chomp(builder.toString(), ", ") + "}";
	}
}
