package com.thirtygames.zero.common.model.equipment.meta;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper = false)
public class EquipCategory extends GridFilter {
	private static final long serialVersionUID = -5075636645636188088L;

	private Integer categoryKey;
	
	private Integer codeLevel;
	
	private Integer categoryCode;
	
	private String categoryName;
	
	private Integer subCategoryCode;
	
	private String subCategoryName;
}
