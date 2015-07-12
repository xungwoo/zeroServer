package com.thirtygames.zero.common.service.admintool.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmDeckMapper;
import com.thirtygames.zero.common.model.admintool.DeckGrid;

@Service("adminDeckService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmDeckServiceImpl extends AdminServiceImpl<AdmDeckMapper, DeckGrid, String>  implements AdmDeckService {

}
