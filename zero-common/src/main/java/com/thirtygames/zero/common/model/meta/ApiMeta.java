package com.thirtygames.zero.common.model.meta;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ApiMeta implements Serializable {
	
	public final static String CLIENT_PLATFORM_ANDROID = "android";
	public final static String CLIENT_PLATFORM_IOS = "ios";
	public final static String ENCRYPTION_KEY = "encryptionKey";
	public final static String STAGE_REVIVAL_CASH = "stageRevivalCash";
	public final static String RESET_TIME = "resetTime";
	public static final String RESET_DAYOFWEEK = "resetDayOfWeek";
	
	
	@JsonIgnore Integer metaKey;
	String name;
	String value;
	Long longValue;
	Float floatValue;
	@JsonIgnore String desc;	
	
}
