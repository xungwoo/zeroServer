package com.thirtygames.zero.common.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.PeriodItemMapper;
import com.thirtygames.zero.common.model.PeriodItem;

@Slf4j
@Service("periodItemService")
public class PeriodItemServiceImpl extends GenericServiceImpl<PeriodItemMapper, PeriodItem, String> implements PeriodItemService {

}