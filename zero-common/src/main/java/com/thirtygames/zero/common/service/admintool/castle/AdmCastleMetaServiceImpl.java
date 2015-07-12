package com.thirtygames.zero.common.service.admintool.castle;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmCastleMetaMapper;
import com.thirtygames.zero.common.model.admintool.AdminCastleMeta;

@Service("adminCastleMetaService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmCastleMetaServiceImpl extends AdminMetaServiceImpl<AdmCastleMetaMapper, AdminCastleMeta, String>  implements AdmCastleMetaService {
}
