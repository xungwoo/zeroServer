package com.thirtygames.zero.common.generic;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.MysqlDataTruncation;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.mapper.ResourceMapper;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.log.ResourceLog;
import com.thirtygames.zero.common.service.log.ResourceLogService;

@Slf4j
public abstract class ResourceServiceImpl<M extends GenericMapper<T, K>, T, K> extends GenericServiceImpl<M, T, K> implements ResourceService<T, K> {

	@Autowired
	protected ResourceMapper resourceMapper;
	
	@Autowired
	ResourceLogService resourceLogService;
	
	@Override
	@Transactional
	public AccountResource updateAddition(AccountResource ar, boolean isReturnResource) {
		try {
			resourceMapper.updateAddition(ar);
		} catch (MysqlDataTruncation e) {
			throw new RestException(Errors.FailResourceUpdate, "Addition");
		}
		
		this.saveLog(ar, ResourceLog.Status.Addition);
		return (isReturnResource) ? resourceMapper.get(ar.getAccountId()) : null;
	}

	@Override
	@Transactional
	public AccountResource updateSubtraction(AccountResource ar, boolean isReturnResource) {
		checkSubtractResource(ar);
		try {
			resourceMapper.updateSubtraction(ar);
		} catch (MysqlDataTruncation e) {
			throw new RestException(Errors.FailResourceUpdate, "Subtraction");
		}
		
		this.saveLog(ar, ResourceLog.Status.Subtraction);
		return (isReturnResource) ? resourceMapper.get(ar.getAccountId()) : null;
	}	
	
	@Transactional
	public AccountResource updateByResource(T entity, AccountResource ar) {
		checkSubtractResource(ar);

		try {
			resourceMapper.updateSubtraction(ar);
			mapper.update(entity);
		} catch (Exception e) {
			throw new RestException(Errors.FailResourceUpdate, "updateByResource");
		}
		
		this.saveLog(ar, ResourceLog.Status.Subtraction);
		return resourceMapper.get(ar.getAccountId());
	}
	

	private void saveLog(AccountResource ar, ResourceLog.Status status) {
		ResourceLog log = new ResourceLog();
		log.makeLog(ar);
		log.setAccountId(ar.getAccountId());
		log.setStatus(status.getCode());
		resourceLogService.save(log);
	}	

	public void checkSubtractResource(AccountResource useR) {
		AccountResource hasR = resourceMapper.get(useR.getAccountId());
		ValidationUtil.isNullModel(hasR, "accountId:" + useR.getAccountId());
		
		if (useR.getCash() != null && useR.getCash() > hasR.getCash()) {
			throw new RestException(Errors.NotEnoughCash);
		}

		if (useR.getGold() != null && useR.getGold() > hasR.getGold()) {
			throw new RestException(Errors.NotEnoughGold);
		}
		
		if (useR.getAp() != null) {
			long hasApTime = hasR.getCurrentTimeStamp() - hasR.getApLastYmdt();
			hasApTime += hasR.getApExtra() * AccountResource.apRechargeTime;
			long useApTime = useR.getAp() * AccountResource.apRechargeTime;
			if (useApTime > hasApTime) {
				throw new RestException(Errors.NotEnoughAP);
			}
		}
		
		if (useR.getBp() != null) {
			long hasBpTime = hasR.getCurrentTimeStamp() - hasR.getBpLastYmdt();
			hasBpTime += hasR.getBpExtra() * AccountResource.bpRechargeTime;
			long useBpTime = useR.getBp() * AccountResource.bpRechargeTime;
			if (useBpTime > hasBpTime) {
				throw new RestException(Errors.NotEnoughBP);
			}
		}
		
		if (useR.getUnlockKey() != null && useR.getUnlockKey() > hasR.getUnlockKey()) {
			throw new RestException(Errors.NotEnoughUnlockKey);
		}
		
		if (useR.getFp() != null && useR.getFp() > hasR.getFp()) {
			throw new RestException(Errors.NotEnoughFP);
		}
		
		if (useR.getEquipLevelUpDrug() != null && useR.getEquipLevelUpDrug() > hasR.getEquipLevelUpDrug()) {
			throw new RestException(Errors.NotEnoughEquipLevelUpDrug);
		}
		
		if (useR.getEquipTicket() != null && useR.getEquipTicket() > hasR.getEquipTicket()) {
			throw new RestException(Errors.NotEnoughEquipTicket);
		}
	}
}
