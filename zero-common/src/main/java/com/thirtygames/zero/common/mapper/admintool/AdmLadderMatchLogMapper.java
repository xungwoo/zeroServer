package com.thirtygames.zero.common.mapper.admintool;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.LadderMatch;


public interface AdmLadderMatchLogMapper extends GenericMapper<LadderMatch, String> {
	List<LadderMatch> selectLadderMatchLog(@Param("sidx")String sidx, @Param("sord")String sord,
			@Param("model")LadderMatch ladderMatch);
}
