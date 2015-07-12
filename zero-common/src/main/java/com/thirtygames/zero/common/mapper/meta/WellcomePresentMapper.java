package com.thirtygames.zero.common.mapper.meta;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.meta.WellcomePresent;

public interface WellcomePresentMapper extends GenericMapper<WellcomePresent, Integer> {

	List<WellcomePresent> getListByLang(@Param("lang")String lang);

}
