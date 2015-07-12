package com.thirtygames.zero.common.model.log;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.admintool.UserResource;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResourceLog extends GridFilter {

	String logKey;
	String accountId;
	Integer status;
	Long gold; 
	Long cash; 
	Long ap; 
	Long bp; 
	Long fp; 
	Long unlockKey; 
	Long equipLevelUpDrog; 
	Long equipTicket;
	
	String logYmdt;
	
	public void makeLog(AccountResource ar) {
		if (ar.getGold() != null && ar.getGold() > 0) this.gold = ar.getGold();
		if (ar.getCash() != null && ar.getCash() > 0) this.cash = ar.getCash();
		if (ar.getAp() != null && ar.getAp() > 0) this.ap = ar.getAp();
		if (ar.getBp() != null && ar.getBp() > 0) this.bp = ar.getBp();
		if (ar.getFp() != null && ar.getFp() > 0) this.fp = ar.getFp();
		if (ar.getUnlockKey() != null && ar.getUnlockKey() > 0) this.unlockKey = ar.getUnlockKey();
		if (ar.getEquipLevelUpDrug() != null && ar.getEquipLevelUpDrug() > 0) this.equipLevelUpDrog = ar.getEquipLevelUpDrug();
		if (ar.getEquipTicket() != null && ar.getEquipTicket() > 0) this.equipTicket = ar.getEquipTicket();
	}
	
	public void makeLog(UserResource ar) {
		if (ar.getGold() != null && ar.getGold() > 0) this.gold = ar.getGold();
		if (ar.getCash() != null && ar.getCash() > 0) this.cash = ar.getCash();
		if (ar.getAp() != null && ar.getAp() > 0) this.ap = ar.getAp();
		if (ar.getBp() != null && ar.getBp() > 0) this.bp = ar.getBp();
		if (ar.getFp() != null && ar.getFp() > 0) this.fp = ar.getFp();
		if (ar.getUnlockKey() != null && ar.getUnlockKey() > 0) this.unlockKey = ar.getUnlockKey();
		if (ar.getEquipLevelUpDrug() != null && ar.getEquipLevelUpDrug() > 0) this.equipLevelUpDrog = ar.getEquipLevelUpDrug();
		if (ar.getEquipTicket() != null && ar.getEquipTicket() > 0) this.equipTicket = ar.getEquipTicket();
	}
	
	@RequiredArgsConstructor
	public enum Status {
		Subtraction(0), 
		Addition(1);
		
		@Getter
		private final int code;
		
		@Getter
		private static final java.util.Map<java.lang.Integer, Status> $CODE_LOOKUP = new java.util.HashMap<java.lang.Integer, Status>();
		static {
			for (Status status : Status.values()) {
				$CODE_LOOKUP.put(status.code, status);
			}
		}
		
		public static Status findByCode(final int code) {
			if ($CODE_LOOKUP.containsKey(code)) {
				return $CODE_LOOKUP.get(code);
			}
			throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'LogStatus' has no value for 'code = %s'", code));
		}
	}	
}
