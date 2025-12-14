package com.iconiux.ezrfs.daemon.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Component
@Data
public class OriginService {
	@Autowired
	private BuildProperties buildProperties;

	@PostConstruct
	public void init() {
		// Artifact's name from the pom.xml file
		log.info("{}", buildProperties.getName());
		// Artifact version
		log.info("{}", buildProperties.getVersion());
		// Date and Time of the build
		log.info("{}", buildProperties.getTime());
		// Artifact ID from the pom file
		log.info("{}", buildProperties.getArtifact());
		// Group ID from the pom file
		log.info("{}", buildProperties.getGroup());
	}
}
