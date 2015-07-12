package com.thirtygames.zero.common.mapper.admintool;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.admintool.AdminBossRaidMeta;


public interface AdmBossRaidMetaMapper extends GenericMapper<AdminBossRaidMeta, String> {

	int saveName(AdminBossRaidMeta entity);

	void updateName(AdminBossRaidMeta entity);

	void deleteName(String id);	
}