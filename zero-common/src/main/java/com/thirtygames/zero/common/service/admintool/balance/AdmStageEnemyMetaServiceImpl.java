package com.thirtygames.zero.common.service.admintool.balance;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmStageEnemyMapper;
import com.thirtygames.zero.common.model.meta.StageEnemy;

@Service("adminStageEnemyMetaService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmStageEnemyMetaServiceImpl extends AdminMetaServiceImpl<AdmStageEnemyMapper, StageEnemy, String>  implements AdmStageEnemyMetaService {

}
