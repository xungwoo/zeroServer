package com.thirtygames.zero.common.model.admintool;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminLog extends GridFilter {
	
	Integer logKey;
	String memo;
	Integer type;
	String dataKey;
	
	String modId;
	String modYmdt;
	
	@RequiredArgsConstructor
	public enum AdminLogType {
		ACCOUNT(1), DECK(2), EQUIPMENT(3);
		
		@Getter
		private final int code;
		
		@Getter
		private static final java.util.Map<java.lang.Integer, AdminLogType> $CODE_LOOKUP = new java.util.HashMap<java.lang.Integer, AdminLogType>();
		static {
			for (AdminLogType status : AdminLogType.values()) {
				$CODE_LOOKUP.put(status.code, status);
			}
		}
		
		public static AdminLogType findByCode(final int code) {
			if ($CODE_LOOKUP.containsKey(code)) {
				return $CODE_LOOKUP.get(code);
			}
			throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'AdminLogType' has no value for 'code = %s'", code));
		}		
		
	}	
}
