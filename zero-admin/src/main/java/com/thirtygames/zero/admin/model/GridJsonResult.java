package com.thirtygames.zero.admin.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thirtygames.zero.common.generic.GenericModel;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class GridJsonResult<T> extends GenericModel {
	private static final long serialVersionUID = 2850797522028512410L;
	
	List<T> rows;		// resultList
	Integer page;	
	Integer total;		// count
	Integer records;	// page size
	
	
	public GridJsonResult() { }
	
	public GridJsonResult(List<T> rows) {
		this.rows = rows;
	};
	
	public GridJsonResult(List<T> rows, int page, int total, int records) {
		this.rows = rows;
		this.page = page;
		this.total = total;
		this.records = records;
	};	
	
	
}
