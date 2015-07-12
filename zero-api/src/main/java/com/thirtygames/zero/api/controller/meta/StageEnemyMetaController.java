package com.thirtygames.zero.api.controller.meta;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.api.controller.common.ApiMetaController;
import com.thirtygames.zero.api.validator.meta.StageEnemyMetaValidator;
import com.thirtygames.zero.common.model.meta.StageEnemy;
import com.thirtygames.zero.common.service.meta.StageEnemyMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/meta/stages/enemies")
public class StageEnemyMetaController extends ApiMetaController<StageEnemy, Integer, StageEnemyMetaService, StageEnemyMetaValidator> {
	
}