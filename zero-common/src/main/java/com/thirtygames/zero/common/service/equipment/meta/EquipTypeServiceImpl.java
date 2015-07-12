package com.thirtygames.zero.common.service.equipment.meta;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.EquipTypeMapper;
import com.thirtygames.zero.common.model.equipment.meta.EquipType;

@Slf4j
@Service("equipTypeService")
public class EquipTypeServiceImpl extends MetaServiceImpl<EquipTypeMapper, EquipType, String> implements EquipTypeService {

}
