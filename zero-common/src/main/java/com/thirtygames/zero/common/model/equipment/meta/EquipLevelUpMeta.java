package com.thirtygames.zero.common.model.equipment.meta;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
public class EquipLevelUpMeta implements Serializable {
	
	Integer grade;
	Integer level;
	Float rate;

}
