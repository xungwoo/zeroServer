package com.thirtygames.zero.common.service.admintool.ladder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmLeagueMetaMapper;
import com.thirtygames.zero.common.model.admintool.AdminLeagueMeta;

@Service("adminLeagueMetaService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmLeagueMetaServiceImpl extends AdminMetaServiceImpl<AdmLeagueMetaMapper, AdminLeagueMeta, String>  implements AdmLeagueMetaService {

}
