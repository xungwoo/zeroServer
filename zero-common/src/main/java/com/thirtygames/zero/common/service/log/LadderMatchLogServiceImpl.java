package com.thirtygames.zero.common.service.log;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.LogServiceImpl;
import com.thirtygames.zero.common.mapper.log.LadderMatchLogMapper;
import com.thirtygames.zero.common.model.LadderMatch;

@Slf4j
@Service("ladderMatchLogService")
public class LadderMatchLogServiceImpl extends LogServiceImpl<LadderMatchLogMapper, LadderMatch, String> implements LadderMatchLogService  {
	

}