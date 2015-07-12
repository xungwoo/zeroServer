package com.thirtygames.zero.common.service.admintool.event;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmWellcomePresentMapper;
import com.thirtygames.zero.common.model.admintool.AdminWellcomePresent;

@Service("adminWellcomePresentService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmWellcomePresentServiceImpl extends AdminMetaServiceImpl<AdmWellcomePresentMapper, AdminWellcomePresent, String>  implements AdmWellcomePresentService {

}
