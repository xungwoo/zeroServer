package com.thirtygames.zero.common.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.etc.util.CalendarUtil;

@Slf4j
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResetDateInfo implements Serializable {
	
	// %H%i%s 
	//public static final String RESET_TIME = "151000"; 
	public static final int RESET_DAY_OF_WEEK = 2;
	public static final int DAY_OF_WEEK = 7;
	public static final int LIMIT_SECOND = 30 * 60;

	String resetDate;
	Long resetTimeStamp;
	
	@JsonIgnore String currentDate;
	@JsonIgnore Integer dayOfWeek;
	
	Boolean isLimitTime;
	@JsonIgnore Boolean isOverStandardTime;
	@JsonIgnore Integer diffByStandardTime;
	

	public ResetDateInfo makeResetDateInfo(String resetTime, int dayOfWeek) {
		int result = ((DAY_OF_WEEK + dayOfWeek) - this.dayOfWeek) % DAY_OF_WEEK;
		if (result == 0 && this.diffByStandardTime < 0) result = DAY_OF_WEEK;
		
		Calendar resetCalendar = CalendarUtil.toCalendar(this.currentDate, 4, 2, 2);
		resetCalendar.add(Calendar.DAY_OF_WEEK, result);
		
		int hour = Integer.parseInt(resetTime.substring(0, 2));
		int min = Integer.parseInt(resetTime.substring(2, 4));
		int sec = Integer.parseInt(resetTime.substring(4, 6));
		log.debug("hour/min/sec : " + hour + "/" + min + "/" + sec);
		
		resetCalendar.set(Calendar.HOUR_OF_DAY, hour);
		resetCalendar.set(Calendar.MINUTE, min);
		resetCalendar.set(Calendar.SECOND, sec);
		this.resetDate = CalendarUtil.toString(resetCalendar, "yyyyMMdd");
		
		Timestamp resetTimeStamp = new java.sql.Timestamp(resetCalendar.getTime().getTime());
		this.resetTimeStamp = resetTimeStamp.getTime() / 1000;
		
		this.isLimitTime = false;
		if (diffByStandardTime > 0 && diffByStandardTime <= LIMIT_SECOND && this.dayOfWeek == dayOfWeek) {
			this.isLimitTime = true;
		}		
		return this;
	}
}

