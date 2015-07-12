package com.thirtygames.zero.common.service.meta;

import java.util.List;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.meta.WellcomePresent;

public interface WellcomePresentService extends MetaService<WellcomePresent, Integer> {

	List<WellcomePresent> getListByLang(String lang);

}
