package com.quick.cache.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;

public class SelfCacheManager implements CacheManager {
	@Override
	public Cache getCache(String name) {
		return null;
	}

	@Override
	public Collection<String> getCacheNames() {
		return null;
	}
}
