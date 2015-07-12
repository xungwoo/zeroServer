package com.thirtygames.zero.common.mapper.admintool;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.admintool.AdminBossEventMeta;


public interface AdmBossEventMetaMapper extends GenericMapper<AdminBossEventMeta, String> {

	int saveName(AdminBossEventMeta entity);

	void updateName(AdminBossEventMeta entity);

	void deleteName(String id);	
}