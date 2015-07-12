package com.thirtygames.zero.common.service.admintool;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.CharMatcher;
import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.admintool.AdminServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmPostMapper;
import com.thirtygames.zero.common.model.admintool.AdminPost;

@Service("adminPostService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmPostServiceImpl extends AdminServiceImpl<AdmPostMapper, AdminPost, String> implements AdmPostService  {
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public int bulkSave(List<AdminPost> entities) {
		return super.bulkSave(entities);
	}
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public int save(AdminPost post)  {
		CharMatcher digits = CharMatcher.inRange('0', '9').precomputed();
		if (post.getStartYmdt() != null) {
		    String startYmdt = digits.retainFrom(post.getStartYmdt());
		    post.setStartYmdt(startYmdt);
		}
		if (post.getExpireYmdt() != null) {
		    String expireYmdt = digits.retainFrom(post.getExpireYmdt());
		    post.setExpireYmdt(expireYmdt);
		}		
		
		return mapper.save(post);
	}	
	
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public int update(AdminPost post)  {
		CharMatcher digits = CharMatcher.inRange('0', '9').precomputed();
		if (post.getStartYmdt() != null) {
		    String startYmdt = digits.retainFrom(post.getStartYmdt());
		    post.setStartYmdt(startYmdt);
		}
		if (post.getExpireYmdt() != null) {
		    String expireYmdt = digits.retainFrom(post.getExpireYmdt());
		    post.setExpireYmdt(expireYmdt);
		}
		
		return mapper.update(post);
	}	
}