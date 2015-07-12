package com.thirtygames.zero.common.service.admintool.boss;

import com.thirtygames.zero.common.generic.admintool.AdminMetaService;
import com.thirtygames.zero.common.model.admintool.AdminBossEventMeta;
import com.thirtygames.zero.common.model.admintool.AdminBossRaidMeta;

public interface AdmBossEventMetaService extends AdminMetaService<AdminBossEventMeta, String> {

	void addByPreset(AdminBossRaidMeta presetMeta);
}