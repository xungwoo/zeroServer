package com.thirtygames.zero.common.model.admindata;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Notice extends GridFilter {

	Integer noticeKey;
	
	String lang;
	String imgUrl;
	Integer landingType;
	String linkUrl; 
	Boolean isDel;

	String expireYmd;
	String expireHms;
	Date expireYmdt;
	String modYmdt;
	String modId;
}
