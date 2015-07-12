package com.thirtygames.zero.api.controller.meta;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.api.controller.common.ApiMetaController;
import com.thirtygames.zero.api.validator.meta.EquipLevelUpMetaValidator;
import com.thirtygames.zero.common.model.equipment.meta.EquipLevelUpMeta;
import com.thirtygames.zero.common.service.equipment.meta.EquipLevelUpMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/meta/equipments/level-up")
public class EquipLevelUpMetaController extends ApiMetaController<EquipLevelUpMeta, Integer, EquipLevelUpMetaService, EquipLevelUpMetaValidator> {

}
