package com.thirtygames.zero.common.service.admintool.user;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.admintool.AdminServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmEquipmentMapper;
import com.thirtygames.zero.common.model.equipment.Equipment;

@Service("adminEquipmentService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmEquipmentServiceImpl extends AdminServiceImpl<AdmEquipmentMapper, Equipment, String>  implements AdmEquipmentService {

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public List<Equipment> getList(List<String> equipmentIdList) {
		return mapper.getList(equipmentIdList);
	}
}
