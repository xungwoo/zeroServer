package com.thirtygames.zero.common.mapper.admintool;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.admintool.AdminCastleMeta;


public interface AdmCastleMetaMapper extends GenericMapper<AdminCastleMeta, String> {

	int saveName(AdminCastleMeta entity);

	void updateName(AdminCastleMeta entity);

	void deleteName(String id);	
}