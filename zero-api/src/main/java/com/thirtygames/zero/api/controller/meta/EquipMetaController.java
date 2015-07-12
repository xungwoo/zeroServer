package com.thirtygames.zero.api.controller.meta;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thirtygames.zero.common.model.equipment.meta.EquipImageMeta;
import com.thirtygames.zero.common.service.equipment.meta.EquipImageMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/meta")
public class EquipMetaController {
	
	@Autowired
	EquipImageMetaService service;
	
	@RequestMapping(value = "/equipments/{lang}", method = { RequestMethod.GET })
	public @ResponseBody List<EquipImageMeta> meta(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestHeader("myMetaRevision") int myMetaRevision,
			@PathVariable("lang") String lang)  {
		
		log.debug("EquipMeta::myTimeStamp::" + myTimeStamp);
		
		List<EquipImageMeta> metaList = service.getEquipMetaList(lang, myMetaRevision);
		return metaList; 
	}
	
	@RequestMapping(value = "/gems/{lang}", method = { RequestMethod.GET })
	public @ResponseBody List<EquipImageMeta> gemMeta(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestHeader("myMetaRevision") int myMetaRevision,
			@PathVariable("lang") String lang)  {
		
		List<EquipImageMeta> metaList = service.getGemMetaList(lang, myMetaRevision);
		return metaList; 
	}
	
}
