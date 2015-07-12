package com.thirtygames.zero.common.service.meta;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.WellcomePresentMapper;
import com.thirtygames.zero.common.model.meta.WellcomePresent;

@Service("wellcomePresentService")
public class WellcomePresentServiceImpl extends MetaServiceImpl<WellcomePresentMapper, WellcomePresent, Integer> implements WellcomePresentService {

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)	
	public List<WellcomePresent> getListByLang(String lang) {
		return mapper.getListByLang(lang);
	}

}
