package com.thirtygames.zero.common.service.admintool.balance;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.etc.util.StringUtil;
import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmMissionMetaMapper;
import com.thirtygames.zero.common.model.admintool.AdminMissionMeta;

@Service("adminMissionMetaService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmMissionMetaServiceImpl extends AdminMetaServiceImpl<AdmMissionMetaMapper, AdminMissionMeta, String>  implements AdmMissionMetaService {
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int save(AdminMissionMeta meta)  {
		if (meta.getStartYmdt() != null) meta.setStartYmdt(StringUtil.removeSpecialChar(meta.getStartYmdt()));		
		if (meta.getDueDateYmdt() != null) meta.setDueDateYmdt(StringUtil.removeSpecialChar(meta.getDueDateYmdt()));		
		return mapper.save(meta);
	}	
	
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int update(AdminMissionMeta meta)  {
		if (meta.getStartYmdt() != null) meta.setStartYmdt(StringUtil.removeSpecialChar(meta.getStartYmdt()));		
		if (meta.getDueDateYmdt() != null) meta.setDueDateYmdt(StringUtil.removeSpecialChar(meta.getDueDateYmdt()));
		return mapper.update(meta);
	}

}
