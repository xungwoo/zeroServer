package com.thirtygames.zero.common.etc.datasource;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.thirtygames.zero.common.service.datasource.DataSourceService;

@Slf4j
@Aspect
@Component
@Order(value = 1)
public class ExecutionLoggingAspect implements InitializingBean {
	@Autowired
	DataSourceService dsManager;

	@Around("execution(* com.thirtygames.zero..*Service.*(..)) || execution(* org.springframework.security.core.userdetails.UserDetailsService.*(..))")
	public Object doServiceProfiling(ProceedingJoinPoint joinPoint) throws Throwable {
		DataSource newDS = null;
		DataSource currentDS = ContextHolder.getDataSource();
		stepLog(currentDS, "##1. Current::");

		Method method = this.getMethod(joinPoint);
		stepLog(currentDS, "##1-1. method::" + method.toString() + ":");
		
		DataSourceAnnotation annotation = (DataSourceAnnotation) method.getAnnotation(DataSourceAnnotation.class);
		if (annotation == null) {
			newDS = DataSource.getAccountDS(ContextHolder.getAccount());
			stepLog(newDS, "##2-1. Account DS::");
			
		} else {
			DataSourceType annotationDST = annotation.value();
			switch(annotationDST) {
			case ZERO_DYNAMIC:
				newDS = ContextHolder.getDynamicDS();
				break;
			case ZERO_DYNAMIC_LOG:
				newDS = DataSource.getDynamicLogDS(ContextHolder.getDynamicDS());
				break;
			case ZERO_LOG:
				newDS = ContextHolder.getLogDS();
				break;
			case ZERO_META:
				newDS = DataSource.ZERO_META;
				break;
			case ZERO_INDEX:
				newDS = DataSource.ZERO_INDEX;
				break;
			default:
				newDS = DataSource.getAccountDS(ContextHolder.getAccount());
				break;	
			}
			stepLog(newDS, "##2-2. Annotation DS::");
		}
		
		ContextHolder.setDataSource(newDS);
		stepLog(ContextHolder.getDataSource(), "##4. Setted:: ");

		Object returnValue = joinPoint.proceed();
		
		ContextHolder.clearDS();
		if (currentDS != null) {
			ContextHolder.setDataSource(currentDS);
		}
		
		stepLog(ContextHolder.getDataSource(), "##5. After::");
		return returnValue;
	}

	private void stepLog(DataSource currentDS, String step) {
		log.debug(System.currentTimeMillis() + step + currentDS + " > ");
	}

	private Method getMethod(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
		final String methodName = joinPoint.getSignature().getName();
		final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		if (method.getDeclaringClass().isInterface()) {
			method = joinPoint.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
		}
		return method;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	}
}