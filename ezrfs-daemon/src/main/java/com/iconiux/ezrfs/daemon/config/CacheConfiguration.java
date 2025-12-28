package com.iconiux.ezrfs.daemon.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Configuration
public class CacheConfiguration {
	@Bean
	public LoadingCache<String, ReentrantLock> fileCuidCache() {
		return CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES).build(new CacheLoader<String, ReentrantLock>() {
			@Override
			public ReentrantLock load(String key) {
				return new ReentrantLock();
			}
		});
	}
}
