package com.iconiux.ezrfs.daemon;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication(scanBasePackages = {"com.iconiux.ezrfs.daemon"})
@EntityScan(basePackages = "com.iconiux.ezrfs.daemon.entity")
@Slf4j
public class EzRFSApplication {

	@Value("${application.version}")
	private String applicationVersion;
	@Value("${application.title}")
	private String applicationTitle;

	@PostConstruct
	private void postConstruct() {
		log.info("=== [ on start {} / {} ] ===", applicationTitle, applicationVersion);
	}

	public static void main(String[] args) {
		SpringApplication.run(EzRFSApplication.class, args);
	}
}
