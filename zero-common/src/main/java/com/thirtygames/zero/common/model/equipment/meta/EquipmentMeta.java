package com.thirtygames.zero.common.model.equipment.meta;

import java.util.Random;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper = false)
public class EquipmentMeta extends GridFilter {
	private static final long serialVersionUID = 5867667720905133320L;
	
	public enum EqClass {
		Normal(0), Magic(1), Rare(2), Unique(3), Set(4);  

		private int code;
		public int getCode() { return code; }
		
		private EqClass(int code) {
			this.code = code;
		}
	}
	
	// openSocket 부여확률
	@JsonIgnore
	float socketRates[] = {0.4f, 0.3f, 0.2f, 0.1f};

	Integer equipMetaKey;
	Integer equipmentType;
	Integer category;
	Integer subCategory;
	Integer deco1Grade;
	Float deco1Rate;
	Integer deco2Grade;
	Float deco2Rate;
	Integer grade;
	Float statGrowth;
	Integer sellPrice;
	Integer maxSockets;
	
	Integer eqClass;
	Integer dropRate;
	Integer setKey;
	String image;
	String ko;
	
	public Integer getOpenSockets(int maxSockets) {
		Random r = new Random();
		int openSockets = 0;
		float rate = 0f;
		for (int i=0; i<=maxSockets; i++) {
			rate +=socketRates[i];
			if (rate > r.nextFloat()) {
				openSockets = i;
				break;
			}
		}
		
		return openSockets;
	}
	
}
