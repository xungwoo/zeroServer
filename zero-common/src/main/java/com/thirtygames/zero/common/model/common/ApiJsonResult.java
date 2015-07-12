package com.thirtygames.zero.common.model.common;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.generic.GenericModel;
import com.thirtygames.zero.common.model.AccountResource;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ApiJsonResult<T> extends GenericModel {
	private static final long serialVersionUID = 2850797522028512410L;

	
	T params;
	Integer resultCount;
	Object result;
	
	@JsonIgnore T resultModel;
	@JsonIgnore List<T> resultList;
	
	Object resultLog;
	
	AccountResource resource;
	
	public ApiJsonResult() { }
	
	public ApiJsonResult(Object result) {
		this.result = result;
	};
	
	public ApiJsonResult(Object result, T params) {
		this.result = result;
		this.params = params;
	};	
	
	public ApiJsonResult(Object result, AccountResource resource) {
		this.result = result;
		this.resource = resource;
	};
	
	public ApiJsonResult(Object result, T params, AccountResource resource) {
		this.result = result;
		this.params = params;
		this.resource = resource;
	};	
}
