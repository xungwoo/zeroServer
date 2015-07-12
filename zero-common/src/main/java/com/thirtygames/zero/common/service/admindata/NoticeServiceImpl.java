package com.thirtygames.zero.common.service.admindata;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.admindata.NoticeMapper;
import com.thirtygames.zero.common.model.admindata.Notice;

@Service("noticeService")
public class NoticeServiceImpl extends MetaServiceImpl<NoticeMapper, Notice, Integer> implements NoticeService {

}