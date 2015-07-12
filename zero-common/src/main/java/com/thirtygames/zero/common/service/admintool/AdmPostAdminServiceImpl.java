package com.thirtygames.zero.common.service.admintool;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.etc.util.StringUtil;
import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmPostAdminMapper;
import com.thirtygames.zero.common.model.admintool.AdminPost;

@Service("adminPostAdminService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmPostAdminServiceImpl extends AdminMetaServiceImpl<AdmPostAdminMapper, AdminPost, String> implements AdmPostAdminService  {
	
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int save(AdminPost post)  {
		if (post.getStartYmdt() != null) post.setStartYmdt(StringUtil.removeSpecialChar(post.getStartYmdt()));		
		if (post.getExpireYmdt() != null) post.setExpireYmdt(StringUtil.removeSpecialChar(post.getExpireYmdt()));		
		return mapper.save(post);
	}	
	
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int update(AdminPost post)  {
		if (post.getStartYmdt() != null) post.setStartYmdt(StringUtil.removeSpecialChar(post.getStartYmdt()));		
		if (post.getExpireYmdt() != null) post.setExpireYmdt(StringUtil.removeSpecialChar(post.getExpireYmdt()));
		return mapper.update(post);
	}	
	
}