package com.thirtygames.zero.common.etc.datasource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {
	@Override
	protected Object determineCurrentLookupKey() {
		log.debug("############# RDS::: " + ContextHolder.getDataSource());
		return ContextHolder.getDataSource();
	}
}