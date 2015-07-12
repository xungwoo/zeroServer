package com.thirtygames.zero.common.model.equipment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class EquipLog extends GridFilter {
	
	String logKey;
	String equipmentId;
	String accountId;
	Integer equipmentType;
	Integer subCategory; // client : category
	Integer grade;
	Integer status;
	String installedEquipId;
	Integer installedSocketNo;
	String usedEqList;	//equipmentIds seperate ","
	Long logYmdt;
	String logDate;

	@RequiredArgsConstructor
	public enum EquipLogStatus {
		Acquire(1), Sell(2), MergeMaterial(3), MergeAcquire(4), Install(5), Clear(6);
		
		@Getter
		private final int code;
		
		@Getter
		private static final java.util.Map<java.lang.Integer, EquipLogStatus> $CODE_LOOKUP = new java.util.HashMap<java.lang.Integer, EquipLogStatus>();
		static {
			for (EquipLogStatus status : EquipLogStatus.values()) {
				$CODE_LOOKUP.put(status.code, status);
			}
		}
		
		public static EquipLogStatus findByCode(final int code) {
			if ($CODE_LOOKUP.containsKey(code)) {
				return $CODE_LOOKUP.get(code);
			}
			throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'EquipLogStatus' has no value for 'code = %s'", code));
		}		
		
	}
}
