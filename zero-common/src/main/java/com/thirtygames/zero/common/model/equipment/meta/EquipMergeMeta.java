package com.thirtygames.zero.common.model.equipment.meta;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EquipMergeMeta implements Serializable {
	private static final long serialVersionUID = -2066531667824246521L;

	Integer class1;
	Integer class2; 
	Float normalRate;
	Float magicRate;
	Float rareRate;
	Float uniqueRate;
	Float setRate;
}
