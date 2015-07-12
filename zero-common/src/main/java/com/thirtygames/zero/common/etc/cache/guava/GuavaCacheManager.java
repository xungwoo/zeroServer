package com.thirtygames.zero.common.etc.cache.guava;

import java.util.Collection;
import java.util.Collections;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.util.StringUtils;

import com.google.common.cache.CacheBuilder;

/**
 * {@link CacheManager} implementation backed by {@link GuavaCache}.
 * @author Omar Irbouh
 * @since 1.0
 */
public class GuavaCacheManager extends AbstractTransactionSupportingCacheManager {

	private Collection<GuavaCache> caches;

	private String spec;

	private volatile CacheBuilder<Object, Object> cacheBuilder;

	private boolean allowNullValues = true;

	public GuavaCacheManager() {
	}

	public void setCaches(Collection<GuavaCache> caches) {
		this.caches = caches;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getSpec() {
		return spec;
	}

	public void setAllowNullValues(boolean allowNullValues) {
		this.allowNullValues = allowNullValues;
	}

	public boolean isAllowNullValues() {
		return allowNullValues;
	}

	@Override
	protected Collection<? extends Cache> loadCaches() {
		return (caches != null) ? caches : Collections.<GuavaCache>emptyList();
	}

	@Override
	public Cache getCache(String name) {
		Cache cache = super.getCache(name);
		if (cache == null) {
			// create a new cache
			cache = createGuavaCache(name);

			// add to collection of available caches
			addCache(cache);
		}
		return cache;
	}

	private GuavaCache createGuavaCache(String name) {
		// create GuavaCache
		return new GuavaCache(name, getCacheBuilder(), allowNullValues);
	}

	private CacheBuilder<Object, Object> getCacheBuilder() {
		if (cacheBuilder == null) {
			synchronized (this) {
				if (cacheBuilder == null) {
					if (StringUtils.hasText(spec)) {
						cacheBuilder = CacheBuilder.from(spec);
					}
					else {
						cacheBuilder = CacheBuilder.newBuilder();
					}
				}
				notify();
			}
		}

		return cacheBuilder;
	}

}
