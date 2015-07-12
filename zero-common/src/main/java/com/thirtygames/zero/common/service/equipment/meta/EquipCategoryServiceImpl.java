package com.thirtygames.zero.common.service.equipment.meta;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.EquipCategoryMapper;
import com.thirtygames.zero.common.model.equipment.meta.EquipCategory;

@Slf4j
@Service("equipCategoryService")
public class EquipCategoryServiceImpl extends MetaServiceImpl<EquipCategoryMapper, EquipCategory, String> implements EquipCategoryService {

}
