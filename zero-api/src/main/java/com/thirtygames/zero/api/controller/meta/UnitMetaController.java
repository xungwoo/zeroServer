package com.thirtygames.zero.api.controller.meta;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.api.controller.common.ApiMetaController;
import com.thirtygames.zero.api.validator.meta.UnitMetaValidator;
import com.thirtygames.zero.common.model.meta.UnitMeta;
import com.thirtygames.zero.common.service.meta.UnitMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/meta/units")
public class UnitMetaController extends ApiMetaController<UnitMeta, Integer, UnitMetaService, UnitMetaValidator> {

}
