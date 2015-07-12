package com.thirtygames.zero.common.mapper.admintool;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.admintool.AdminAchvMeta;


public interface AdmAchvMetaMapper extends GenericMapper<AdminAchvMeta, String> {

	int saveName(AdminAchvMeta entity);

	void updateName(AdminAchvMeta entity);

	void deleteName(String id);
}