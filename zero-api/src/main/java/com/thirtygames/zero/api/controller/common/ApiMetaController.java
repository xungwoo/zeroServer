package com.thirtygames.zero.api.controller.common;

import java.util.List;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.GenericUtil;
import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.common.ApiJsonResult;

/**
 * 
 * @method
 * add
 * update
 * delete
 * get
 * search
 * range
 * recent
 *
 * @param <T> : Model Entity
 * @param <K> : KeyColumn Type
 * @param <S> : Service Interface
 * @param <V> : Validator
 * 
 * @author xungwoo
 */
@Slf4j
public abstract class ApiMetaController<T, K, S extends MetaService<T, K>, V extends Validator> {

	@Autowired
	private ApplicationContext applicationContext;

	private Class<S> serviceClass;
	private Class<V> validatorClass;

	protected S service;
	protected V validator;


	@SuppressWarnings("unchecked")
	public ApiMetaController() {
		this.serviceClass = (Class<S>) GenericUtil.getClassOfGenericTypeIn(getClass(), 2);
		this.validatorClass = (Class<V>) GenericUtil.getClassOfGenericTypeIn(getClass(), 3);
	}

	@PostConstruct
	public void setUp() {
		this.service = applicationContext.getBean(serviceClass);
		this.validator = applicationContext.getBean(validatorClass);
	}

	@RequestMapping(method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<T> add(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, 
			@ModelAttribute T entity, 
			BindingResult bindingResult, 
			SessionStatus status) {
		log.debug("################### GenericController add!!" + entity.toString());

		//validator.validate(entity, bindingResult);
		if (bindingResult.hasErrors()) {
			throw new RestException("Param Binding Error:" + bindingResult.getAllErrors().toString());
		}

		status.setComplete();
		
		ApiJsonResult<T> result = new ApiJsonResult<T>();
		result.setParams(entity);
		result.setResultCount(service.save(entity));
		return result;
	}

	@RequestMapping(method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<T> update(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, @ModelAttribute T entity, BindingResult bindingResult, SessionStatus status)  {
		//validator.validate(entity, bindingResult);
		if (bindingResult.hasErrors()) {
			throw new RestException("Param Invalid:" + bindingResult.getAllErrors());
		}
		
		status.setComplete();

		ApiJsonResult<T> result = new ApiJsonResult<T>();
		result.setParams(entity);	
		result.setResultCount(service.update(entity));
		return result;
	}
	

	@RequestMapping(method = { RequestMethod.DELETE })
	public @ResponseBody
	ApiJsonResult<T> delete(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, @PathVariable("id") K id)  {
		ApiJsonResult<T> result = new ApiJsonResult<T>();
		result.setResultCount(service.delete(id));
		return result;
	}
	
	
	@RequestMapping(value = "/multi", method = { RequestMethod.POST })
	public @ResponseBody ApiJsonResult<T> addMulti(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestBody List<T> list, 
			BindingResult bindingResult, 
			SessionStatus status)  {
		
		ApiJsonResult<T> result = new ApiJsonResult<T>();
		result.setResultCount(service.multiAdd(list));
		return result;
	}		
	
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	public @ResponseBody
	T get(@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, 
			@RequestHeader("myClientVersion") String myClientVersion, 
			@RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestHeader("myMetaRevision") int myMetaRevision,
			@PathVariable("id") K id)  {
		return service.getByCache(id, myMetaRevision);
	}	
	
	
	@RequestMapping(method = { RequestMethod.GET })
	public @ResponseBody
	List<T> range(@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, 
			@RequestHeader("myClientVersion") String myClientVersion, 
			@RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestHeader("myMetaRevision") int myMetaRevision,
			@RequestParam(required = false, value = "from", defaultValue = "0") int from, 
			@RequestParam(required = false, value = "length", defaultValue = "0") int length)
			throws RestException {
		return service.rangeByCache(from, length, myMetaRevision);
	}
}
