package com.thirtygames.zero.admin.controller.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.thirtygames.zero.admin.ftl.PageInfo;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.validator.BaseValidator;
import com.thirtygames.zero.common.generic.GenericService;

@Slf4j
public abstract class AdminJsonController<T, K, S extends GenericService<T, K>, V extends BaseValidator> extends AdminGenericController<T, K, S, V> {
	
	protected T preJsonSearch(T entity, ModelMap model) {
		return entity;
	}
	
	protected Map<String, Object> postJsonSearch(Map<String, Object> resultMap) {
		return resultMap;
	}
	
	protected Map<String, Object> postJson(Map<String, Object> resultMap) {
		return resultMap;
	}

	protected Map<String, Object> postJsonAdd(Map<String, Object> resultMap, T entity) {
		return resultMap;
	}
	
	protected T preJsonUpdate(T entity) {
		return entity;
	}		
	
	protected Map<String, Object> postJsonUpdate(Map<String, Object> resultMap, T entity) {
		return resultMap;
	}		
	
	protected Map<String, Object> postJsonDelete(Map<String, Object> resultMap) {
		return resultMap;
	}		
	
	
	// @TODO 구조변경
	@RequestMapping(value = "/json/xeditable", method = { RequestMethod.POST })
	public @ResponseBody
	Map<String, Object> xEditable(
			@RequestParam(required = true, value = "name") String name, 
			@RequestParam(required = true, value = "value") String value, 
			@RequestParam(required = true, value = "pk") K pk, 
			ModelMap model)  {
		log.debug("################### GenericController Json xEditable !!" + name + ":" +  value + ":" + pk);
		
		T entity;
		try {
			entity = (T) modelClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
		
		try {
			java.lang.reflect.Field f = modelClass.getDeclaredField(name);
			Object convertedValue = conversionService.convert(value, f.getType());
			f.setAccessible(true);
			try {
				f.set(entity, convertedValue);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		entity = preJsonUpdate(entity);
		//service.update(entity, pk);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//resultMap = postJsonAdd(resultMap);
		resultMap.put("paramModel", entity);
		resultMap.put("result", "Update Sucess");
		resultMap.put("pageInfo", pageInfo);
		return resultMap;
	}	
	
	

	@RequestMapping(value = "/json/add", method = { RequestMethod.POST })
	public @ResponseBody
	Map<String, Object> jsonAdd(
			@RequestParam(required = false, value = "page", defaultValue = "1") int page, 
			@ModelAttribute T entity, 
			BindingResult result, 
			SessionStatus status, 
			ModelMap model)  {

		log.debug("################### GenericController Ajax Add!!" + entity.toString());

		validator.validate(model, result);
		validator.processErrors(result);

		service.save(entity);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = postJsonAdd(resultMap, entity);
		resultMap.put("paramModel", entity);
		resultMap.put("result", "Success");
		resultMap.put("pageInfo", pageInfo);
		return resultMap;
	}	
	
	
	@RequestMapping(value = "/json/update", method = { RequestMethod.POST })
	public @ResponseBody
	Map<String, Object> jsonUpdate(
			@RequestParam(required = false, value = "page", defaultValue = "1") int page, 
			@ModelAttribute T entity, 
			BindingResult result, 
			SessionStatus status, 
			ModelMap model)  {
		log.debug("################### GenericController Json update!!" + entity.toString());
		
		entity = preJsonUpdate(entity);
		service.update(entity);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = postJsonUpdate(resultMap, entity);
		resultMap.put("paramModel", entity);
		resultMap.put("result", "Success");
		resultMap.put("pageInfo", pageInfo);
		return resultMap;
	}		
	
	@RequestMapping(value = "/json/delete/{id}", method = { RequestMethod.POST })
	public @ResponseBody
	Map<String, Object> jsonDelete(@PathVariable("id") K id, HttpServletRequest request, ModelMap model)  {
		log.debug("################### GenericController delete!!" + id.toString());
		service.delete(id);
		model = postDelete(model);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = postJsonDelete(resultMap);
		resultMap.put("paramModel", id);
		resultMap.put("result", "Delete Sucess");
		resultMap.put("pageInfo", pageInfo);
		return resultMap;
	}		
	
	@RequestMapping(value = "/json", method = { RequestMethod.GET })
	public @ResponseBody
	Map<String, Object> json(@RequestParam(required = false, value = "page", defaultValue = "1") int page, ModelMap model, BindingResult result)
			throws RestException {
		
		log.debug("################### GenericController Ajax List!!");
		
		validator.validate(model, result);
		validator.processErrors(result);
		PageInfo pageInfo = (PageInfo) model.get("pageInfo");
		pageInfo.setPageInfo(page);
		
		List<T> entityList = service.range(pageInfo.getStart(), pageInfo.getPageSize());
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = postJson(resultMap);
		resultMap.put("rows", entityList);
		resultMap.put("page", pageInfo.getPage());
		resultMap.put("total", pageInfo.getCount());
		resultMap.put("records", pageInfo.getPageSize());
		return resultMap;
	}

	@RequestMapping(value = "/json/search", method = { RequestMethod.POST })
	public @ResponseBody
	Map<String, Object> jsonSearch(@RequestParam(required = false, value = "page", defaultValue = "1") int page, @ModelAttribute T entity, BindingResult result, SessionStatus status, ModelMap model)
			throws RestException {

		log.debug("################### GenericController Ajax Search!!" + entity.toString());

		validator.validate(model, result);
		validator.processErrors(result);
		PageInfo pageInfo = (PageInfo) model.get("pageInfo");
		pageInfo.setPageInfo(page);

		entity = preJsonSearch(entity, model);
		List<T> entityList = service.search(pageInfo.getStart(), pageInfo.getPageSize(), entity);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = postJsonSearch(resultMap);
		resultMap.put("rows", entityList);
		resultMap.put("page", pageInfo.getPage());
		resultMap.put("total", pageInfo.getCount());
		resultMap.put("records", pageInfo.getPageSize());		
		return resultMap;
	}

}
