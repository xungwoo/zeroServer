package com.thirtygames.zero.api.controller.common;

import java.util.List;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.GenericUtil;
import com.thirtygames.zero.common.etc.validator.BaseValidator;
import com.thirtygames.zero.common.generic.GenericService;
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
public abstract class ApiGenericController<T, K, S extends GenericService<T, K>, V extends BaseValidator> {

	@Autowired
	private ApplicationContext applicationContext;

	private Class<S> serviceClass;
	private Class<V> validatorClass;

	protected S service;
	protected V validator;


	@SuppressWarnings("unchecked")
	public ApiGenericController() {
		this.serviceClass = (Class<S>) GenericUtil.getClassOfGenericTypeIn(getClass(), 2);
		this.validatorClass = (Class<V>) GenericUtil.getClassOfGenericTypeIn(getClass(), 3);
	}

	@PostConstruct
	public void setUp() {
		this.service = applicationContext.getBean(serviceClass);
		this.validator = applicationContext.getBean(validatorClass);
	}

	abstract protected T preAdd(T entity, String accountId) ;
	protected ApiJsonResult<T> postAdd(ApiJsonResult<T> result)  {
		return result;
	};

	@RequestMapping(method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<T> add(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, 
			@ModelAttribute T entity, 
			BindingResult bindingResult, 
			SessionStatus status) {
		log.debug("################### GenericController add!!" + entity.toString());

		validator.validate(entity, bindingResult);
		validator.processErrors(bindingResult);
		status.setComplete();

		ApiJsonResult<T> result = new ApiJsonResult<T>();
		entity = preAdd(entity, myAccountId);
		result.setParams(entity);
		result.setResultCount(service.save(entity));
		return postAdd(result);
	}

	abstract protected T preUpdate(T entity, String accountId) ;
	protected ApiJsonResult<T> postUpdate(ApiJsonResult<T> result)  {
		return result;
	};

	@RequestMapping(method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<T> update(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, @ModelAttribute T entity, BindingResult bindingResult, SessionStatus status)  {
		log.debug("################### GenericController update!!" + entity.toString());

		validator.validate(entity, bindingResult);
		validator.processErrors(bindingResult);
		status.setComplete();

		ApiJsonResult<T> result = new ApiJsonResult<T>();
		entity = preUpdate(entity, myAccountId);
		result.setParams(entity);	
		result.setResultCount(service.update(entity));
		return postUpdate(result);
	}
	
	protected ApiJsonResult<T> postDelete(ApiJsonResult<T> result)  {
		return result;
	}

	@RequestMapping(method = { RequestMethod.DELETE })
	public @ResponseBody
	ApiJsonResult<T> delete(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, @PathVariable("id") K id)  {

		log.debug("################### GenericController delete!!" + id.toString());
		
		ApiJsonResult<T> result = new ApiJsonResult<T>();
		result.setResultCount(service.delete(id));
		return postDelete(result);
	}
	
	abstract protected T preSearch(T entity, String accountId) ;
	protected ApiJsonResult<T> postSearch(ApiJsonResult<T> result)  {
		return result;
	};

	@RequestMapping(value = "/search", method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<T> search(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, 
			@RequestParam(required = false, value = "from", defaultValue = "0") int from, 
			@RequestParam(required = false, value = "length", defaultValue = "0") int length,
			@ModelAttribute T entity, BindingResult bindingResult, SessionStatus status)  {
		log.debug("################### GenericController search!!" + entity.toString() + " from:" + from + " length:" + length);
		
		validator.validate(entity, bindingResult);
		validator.processErrors(bindingResult);
		status.setComplete();

		ApiJsonResult<T> result = new ApiJsonResult<T>();
		entity = preSearch(entity, myAccountId);
		result.setParams(entity);
		List<T> resultList = service.search(from, length, entity);
		result.setResultList(resultList);
		result.setResult(resultList);
		result.setResultCount(resultList.size());
		
		return postSearch(result);
	}
	
	
	protected ApiJsonResult<T> postGet(ApiJsonResult<T> result)  {
		return result;
	};

	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	public @ResponseBody
	ApiJsonResult<T> get(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, @PathVariable("id") K id)  {
		log.debug("################### GenericController get!!" + id.toString());
		
		ApiJsonResult<T> result = new ApiJsonResult<T>();
		result.setResult(service.get(id));
		return postGet(result);
	}	
	
	
	protected ApiJsonResult<T> postRange(ApiJsonResult<T> result)  {
		return result;
	};

	@RequestMapping(method = { RequestMethod.GET })
	public @ResponseBody
	ApiJsonResult<T> range(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, @RequestParam(required = false, value = "from", defaultValue = "0") int from, @RequestParam(required = false, value = "length", defaultValue = "0") int length)
			throws RestException {
		log.debug("################### GenericController range!!");

		ApiJsonResult<T> result = new ApiJsonResult<T>();
		result.setResult(service.range(from, length));
		return postRange(result);
	}
	
	

	protected ApiJsonResult<T> postRecent(ApiJsonResult<T> result)  {
		return result;
	};

	@RequestMapping(value = "/recent", method = { RequestMethod.GET })
	public @ResponseBody
	ApiJsonResult<T> recent(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform)  {
		log.debug("################### GenericController recent!!");

		ApiJsonResult<T> result = new ApiJsonResult<T>();
		result.setResult(service.recent());
		return postRecent(result);
	}
}
