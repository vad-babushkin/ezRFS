package com.iconiux.ezrfs.daemon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iconiux.ezlfs.service.EzLocalFileStorage;
import com.iconiux.ezlfs.service.IFileStorage;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
//@PropertySource("classpath:/application-storage.properties")
public class EzStorageStorageConfiguration {
	@Value("${ezlfs.storage.base.path}")
	private String basePath;

	@Autowired
	private ObjectMapper objectMapper;

	@PostConstruct
	private void postConstruct() {
		log.info("basePath: {}", basePath);
	}

	@Bean
	public IFileStorage getStorageService() {
		try {
			return new EzLocalFileStorage(basePath, objectMapper);
		} catch (Exception e) {
			log.error(null, e);
		}

		return null;
	}
}
