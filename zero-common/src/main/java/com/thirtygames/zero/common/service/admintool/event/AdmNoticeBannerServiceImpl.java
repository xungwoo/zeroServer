package com.thirtygames.zero.common.service.admintool.event;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmNoticeBannerMapper;
import com.thirtygames.zero.common.model.admindata.Notice;

@Service("adminNoticeBannerService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmNoticeBannerServiceImpl extends
		AdminMetaServiceImpl<AdmNoticeBannerMapper, Notice, String> implements AdmNoticeBannerService  {
}