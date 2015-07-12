package com.thirtygames.zero.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Setup extends GridFilter {
	private static final long serialVersionUID = 4198430771880920709L;
	
	public static final int INIT_GRAPHIC = 2;
	public static final int INIT_PROFILE = 1;
	public static final int INIT_PUSH = 1;
	public static final int INIT_MIDNIGHT = 1;
	public static final int INIT_AUTO_SCREEN_OFF = 1;
	public static final int INIT_BG_SOUND = 1;
	public static final int INIT_EFFECT_SOUND = 1;
	
	
	String accountId;
	Integer graphic;
	Integer profile;
	Integer push;
	Integer midnight;
	Integer autoScreenOff;
	Integer bgSound;
	Integer effectSound;
}
