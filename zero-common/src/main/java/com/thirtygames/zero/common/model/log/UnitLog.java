package com.thirtygames.zero.common.model.log;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UnitLog extends GridFilter implements Serializable {
	private static final long serialVersionUID = 7131230204327883182L;

	String accountId;
	String unitId;
	Integer unitType;
	Integer status;
	
	Integer prevLevel;
	Integer prevSkill0Lv;
	Integer prevSkill1Lv;
	Integer prevSkill2Lv;
	Integer prevSkill3Lv;
	Integer resultLevel;
	Date logYmdt;

	@RequiredArgsConstructor 
	public enum UnitLogStatus {
		Acquire(1), LevelUp(2), LimitExceedStart(3), LimitExceedEnd(4), Reset(5), SkillLevelUp(6);
		
		@Getter
		private final int code;
		
		@Getter
		private static final java.util.Map<java.lang.Integer, UnitLogStatus> $CODE_LOOKUP = new java.util.HashMap<java.lang.Integer, UnitLogStatus>();
		static {
			for (UnitLogStatus status : UnitLogStatus.values()) {
				$CODE_LOOKUP.put(status.code, status);
			}
		}
		
		public static UnitLogStatus findByCode(final int code) {
			if ($CODE_LOOKUP.containsKey(code)) {
				return $CODE_LOOKUP.get(code);
			}
			throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'UnitLogStatus' has no value for 'code = %s'", code));
		}
	}
}
