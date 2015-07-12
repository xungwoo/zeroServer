package com.thirtygames.zero.api.controller.meta;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.api.controller.common.ApiMetaController;
import com.thirtygames.zero.api.validator.meta.EquipLevelMetaValidator;
import com.thirtygames.zero.common.model.equipment.meta.EquipLevelMeta;
import com.thirtygames.zero.common.service.equipment.meta.EquipLevelMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/meta/equipments/level")
public class EquipLevelMetaController extends ApiMetaController<EquipLevelMeta, Integer, EquipLevelMetaService, EquipLevelMetaValidator> {

}
