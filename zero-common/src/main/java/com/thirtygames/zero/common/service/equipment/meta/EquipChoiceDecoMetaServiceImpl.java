package com.thirtygames.zero.common.service.equipment.meta;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.EquipChoiceDecoMetaMapper;
import com.thirtygames.zero.common.model.equipment.meta.EquipChoiceDecoMeta;

@Slf4j
@Service("equipChoiceDecoMetaService")
public class EquipChoiceDecoMetaServiceImpl extends MetaServiceImpl<EquipChoiceDecoMetaMapper, EquipChoiceDecoMeta, String> implements EquipChoiceDecoMetaService {
}
