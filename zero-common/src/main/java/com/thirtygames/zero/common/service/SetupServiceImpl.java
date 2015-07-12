package com.thirtygames.zero.common.service;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.SetupMapper;
import com.thirtygames.zero.common.model.Setup;

@Service("setupService")
public class SetupServiceImpl extends GenericServiceImpl<SetupMapper, Setup, String> implements SetupService {

}