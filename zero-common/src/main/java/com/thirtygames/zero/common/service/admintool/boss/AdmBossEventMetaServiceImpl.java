package com.thirtygames.zero.common.service.admintool.boss;

import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.etc.util.StringUtil;
import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmBossEventMetaMapper;
import com.thirtygames.zero.common.model.admintool.AdminBossEventMeta;
import com.thirtygames.zero.common.model.admintool.AdminBossRaidMeta;

@Service("adminBossEventMetaService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmBossEventMetaServiceImpl extends AdminMetaServiceImpl<AdmBossEventMetaMapper, AdminBossEventMeta, String>  implements AdmBossEventMetaService {

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int save(AdminBossEventMeta meta)  {
		if (meta.getExposeStartYmdt() != null) meta.setExposeStartYmdt(StringUtil.removeSpecialChar(meta.getExposeStartYmdt()));
		if (meta.getExposeEndYmdt() != null) meta.setExposeEndYmdt(StringUtil.removeSpecialChar(meta.getExposeEndYmdt()));
		mapper.save(meta);
		return mapper.saveName(meta); 
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int update(AdminBossEventMeta meta) {
		if (meta.getExposeStartYmdt() != null) meta.setExposeStartYmdt(StringUtil.removeSpecialChar(meta.getExposeStartYmdt()));
		if (meta.getExposeEndYmdt() != null) meta.setExposeEndYmdt(StringUtil.removeSpecialChar(meta.getExposeEndYmdt()));		
		mapper.saveName(meta);
		return mapper.update(meta);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int delete(String id) {
		mapper.deleteName(id);
		return mapper.delete(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)	
	public void addByPreset(AdminBossRaidMeta presetMeta) {
		String timeStamp = new Long(Calendar.getInstance().getTimeInMillis()).toString(); 
		AdminBossEventMeta eventMeta = new AdminBossEventMeta();
		eventMeta.setBossRaidMetaId(timeStamp);
		eventMeta.setTitleKo(presetMeta.getTitleKo());
		eventMeta.setTitleEn(presetMeta.getTitleEn());
		eventMeta.setBossNameKo(presetMeta.getBossNameKo());
		eventMeta.setBossNameEn(presetMeta.getBossNameEn());
		eventMeta.setBossDefensePhy(presetMeta.getBossDefensePhy());
		eventMeta.setDefensePhyScaler(presetMeta.getDefensePhyScaler());
		eventMeta.setBossDefenseSpell(presetMeta.getBossDefenseSpell());
		eventMeta.setDefenseSpellScaler(presetMeta.getDefenseSpellScaler());
		eventMeta.setBossImage(presetMeta.getBossImage());
		eventMeta.setBossMaxHp(0);
		eventMeta.setBossScale(presetMeta.getBossScale());
		eventMeta.setBossSkillLv0(presetMeta.getBossSkillLv0());
		eventMeta.setBossSkillLv1(presetMeta.getBossSkillLv1());
		eventMeta.setBossSkillLv2(presetMeta.getBossSkillLv2());
		eventMeta.setBossUnitLevel(0);
		eventMeta.setBossUnitType(presetMeta.getBossUnitType());
		eventMeta.setExposeEndYmdt("20000101");
		eventMeta.setExposeStartYmdt("20000101");
		eventMeta.setFee(presetMeta.getFee());
		eventMeta.setGoldReward(0l);
		eventMeta.setGradeRatio(presetMeta.getGradeRatio());
		eventMeta.setMinionHpScale(presetMeta.getMinionHpScale());
		eventMeta.setMinionScale(presetMeta.getMinionScale());
		eventMeta.setMinionSkillLv0(presetMeta.getMinionSkillLv0());
		eventMeta.setMinionSkillLv1(presetMeta.getMinionSkillLv1());
		eventMeta.setMinionSkillLv2(presetMeta.getMinionSkillLv2());
		eventMeta.setMinionSpawnInterval(presetMeta.getMinionSpawnInterval());
		eventMeta.setMinionSpawnNum(presetMeta.getMinionSpawnNum());
		eventMeta.setMinionSpawnStart(presetMeta.getMinionSpawnStart());
		eventMeta.setMinionUnitType(presetMeta.getMinionUnitType());
		eventMeta.setRelativeMinionLevel(presetMeta.getRelativeMinionLevel());
		eventMeta.setScene(presetMeta.getScene());
		eventMeta.setTexture0(presetMeta.getTexture0());
		eventMeta.setTexture1(presetMeta.getTexture1());
		eventMeta.setTexture2(presetMeta.getTexture2());
		mapper.save(eventMeta);
		mapper.saveName(eventMeta); 
	}
}
