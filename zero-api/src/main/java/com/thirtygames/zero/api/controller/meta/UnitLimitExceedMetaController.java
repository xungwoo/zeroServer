package com.thirtygames.zero.api.controller.meta;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.api.controller.common.ApiMetaController;
import com.thirtygames.zero.api.validator.meta.UnitLimitExceedMetaValidator;
import com.thirtygames.zero.common.model.meta.UnitLimitExceed;
import com.thirtygames.zero.common.service.meta.UnitLimitExceedMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/meta/units/limit-exceed")
public class UnitLimitExceedMetaController extends ApiMetaController<UnitLimitExceed, Integer, UnitLimitExceedMetaService, UnitLimitExceedMetaValidator> {



}
