package com.thirtygames.zero.common.service.admintool.user;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.generic.admintool.AdminMetaService;
import com.thirtygames.zero.common.model.meta.LeagueCount;

public interface AdmLeagueCountService extends AdminMetaService<LeagueCount, Integer> {
}