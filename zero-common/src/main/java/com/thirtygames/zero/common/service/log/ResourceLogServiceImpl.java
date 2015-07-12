package com.thirtygames.zero.common.service.log;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.LogServiceImpl;
import com.thirtygames.zero.common.mapper.log.ResourceLogMapper;
import com.thirtygames.zero.common.model.log.ResourceLog;

@Slf4j
@Service("resourceLogService")
public class ResourceLogServiceImpl extends LogServiceImpl<ResourceLogMapper, ResourceLog, String> implements ResourceLogService {

}