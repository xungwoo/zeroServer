package com.thirtygames.zero.common.service.admintool.user;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.admintool.AdminServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmEquipmentStatMapper;
import com.thirtygames.zero.common.model.equipment.EquipmentStat;

@Service("adminEquipmentStatService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmEquipmentStatServiceImpl extends AdminServiceImpl<AdmEquipmentStatMapper, EquipmentStat, String>  implements AdmEquipmentStatService {

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public List<EquipmentStat> getEquipmentStatList(String sidx, String sord,
			EquipmentStat equipmentStat) {
		return mapper.selectEquipmentStatList(sidx, sord, equipmentStat);
	}

}
