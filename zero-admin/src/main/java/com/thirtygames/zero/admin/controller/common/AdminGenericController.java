package com.thirtygames.zero.admin.controller.common;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.thirtygames.zero.admin.ftl.PageInfo;
import com.thirtygames.zero.admin.ftl.RedirectViewUtil;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.GenericUtil;
import com.thirtygames.zero.common.etc.validator.BaseValidator;
import com.thirtygames.zero.common.generic.GenericService;

@Slf4j
public abstract class AdminGenericController<T, K, S extends GenericService<T, K>, V extends BaseValidator> extends AdminBaseController {
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	protected ConversionService conversionService;	
	
	protected Class<S> serviceClass;
	protected Class<T> modelClass;
	protected Class<V> validatorClass;
	
	protected S service;
	protected V validator;	

	@SuppressWarnings("unchecked")
	public AdminGenericController() {
		this.modelClass = (Class<T>) GenericUtil.getClassOfGenericTypeIn(getClass(), 0);	
		this.serviceClass = (Class<S>) GenericUtil.getClassOfGenericTypeIn(getClass(), 2);
		this.validatorClass = (Class<V>) GenericUtil.getClassOfGenericTypeIn(getClass(), 3);
	}

	@PostConstruct
	public void setUp() {
		this.service = applicationContext.getBean(serviceClass);
		this.validator = applicationContext.getBean(validatorClass);
	}
	
	protected ModelMap postGet(ModelMap model) {
		return model;
	};
	
	protected ModelMap postAdd(ModelMap model) {
		return model;
	};
	
	protected ModelMap postDelete(ModelMap model) {
		return model;
	}	

	protected ModelMap postSearch(ModelMap model) {
		return model;
	};

	protected ModelMap postGrid(ModelMap model) {
		return model;
	};

	protected ModelMap postUpdate(ModelMap model) {
		return model;
	};

	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	public ModelAndView get(@PathVariable("id") K id, ModelMap model)  {
		log.debug("################### GenericController get!!" + id.toString());

		model.addAttribute("result", service.get(id));
		model = postGet(model);
		return new ModelAndView(super.getViewName());
	}		

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView add(@ModelAttribute T entity, BindingResult result, SessionStatus status, HttpServletRequest request, ModelMap model)  {
		log.debug("################### GenericController add!!" + entity.toString());

		validator.validate(model, result);
		validator.processErrors(result);

		if (result.hasErrors()) {
			log.debug("################### hasErrors!!");
		} else {
			service.save(entity);
			model = postAdd(model);
			status.setComplete();
		}
		
		return new ModelAndView(RedirectViewUtil.getRedirectView(super.getRedirectUrl(), request.getRequestURI()), model);
	}	


	@RequestMapping(method = { RequestMethod.PUT })
	public ModelAndView update(@ModelAttribute T entity, BindingResult result, SessionStatus status)  {
		log.debug("################### GenericController update!!" + entity.toString());

		validator.validate(entity, result);
		validator.processErrors(result);

		service.update(entity);
		// postUpdate(entity);

		return new ModelAndView(super.getViewName());
	}
	
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.POST })
	public ModelAndView delete(@PathVariable("id") K id, HttpServletRequest request, ModelMap model)  {
		log.debug("################### GenericController delete!!" + id.toString());
		service.delete(id);
		model = postDelete(model);

		return new ModelAndView(RedirectViewUtil.getRedirectView(super.getRedirectUrl(), request.getRequestURI()), model);
	}	

	@RequestMapping(value = "/search", method = { RequestMethod.POST })
	public ModelAndView search(@RequestParam(required = false, value = "page", defaultValue = "1") int page, @ModelAttribute T entity, BindingResult result, SessionStatus status, ModelMap model)
			throws RestException {

		log.debug("################### GenericController search!!" + entity.toString());

		validator.validate(model, result);
		validator.processErrors(result);
		
		PageInfo pageInfo = (PageInfo) model.get("pageInfo");
		pageInfo.setPageInfo(page);

		List<T> entityList = service.search(pageInfo.getStart(), pageInfo.getPageSize(), entity);

		model = postSearch(model);
		model.addAttribute("resultList", entityList);

		return new ModelAndView(super.getViewName());
	}

	@RequestMapping(value = "/list/{page}", method = { RequestMethod.GET })
	public ModelAndView list(@PathVariable("page") int page, HttpServletRequest request, ModelMap model)  {
		log.debug("################### GenericController list page!!");

		PageInfo pageInfo = (PageInfo) model.get("pageInfo");
		pageInfo.setPageInfo(page);
		List<T> entityList = service.range(pageInfo.getStart(), pageInfo.getPageSize());

		model.addAttribute("resultList", entityList);
		model = postGrid(model);

		return new ModelAndView(super.getViewName());
	}

	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public ModelAndView list(HttpServletRequest request, ModelMap model)  {
		log.debug("################### GenericController list!!");
		return new ModelAndView(RedirectViewUtil.getRedirectView(super.getRedirectUrl(), request.getRequestURI()), model);
	}


}
