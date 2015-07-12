package com.thirtygames.zero.common.model.equipment.meta;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class EquipGachaRateMeta implements Serializable {
	private static final long serialVersionUID = 7595202446484618835L;

	Integer rateType;
	Integer value;		// grade or class
	Float rate;
	
}
