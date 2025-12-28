package com.iconiux.ezrfs.daemon.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.LoadingCache;
import com.iconiux.ezlfs.model.FileMetadata;
import com.iconiux.ezlfs.service.IFileStorage;
import io.github.thibaultmeyer.cuid.CUID;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.locks.ReentrantLock;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@Slf4j
public class TFileService {
	@Autowired
	private IFileStorage storageService;
	@Autowired
	private LoadingCache<String, ReentrantLock> fileCuidCache;
	@Autowired
	private ObjectMapper objectMapper;

	@Value("${ezrfs.api.url}")
	private String baseUrl;

	/**
	 * @param file .
	 * @return .
	 */
	public ResponseEntity<String> uploadFile(MultipartFile file) {
		String fileName = file.getName();
		try {
			String fileCuid = CUID.randomCUID2(36).toString();

			ReentrantLock lock = fileCuidCache.getUnchecked(fileCuid);
			lock.lock();
			try {
				FileMetadata fileMetadata = new FileMetadata();
				fileMetadata.setFileCuid(fileCuid);
//				fileMetadata.setFileHash(hash);
				fileMetadata.setOriginalFileName(file.getOriginalFilename());
				fileMetadata.setFileName(fileName);
				fileMetadata.setFileContentType(file.getContentType());
				fileMetadata.setFileContentLength(file.getSize());
				fileMetadata.setFileExt(FilenameUtils.getExtension(file.getOriginalFilename()));
				String fileHash = storageService.saveFile(fileMetadata, file.getBytes());
				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/api/v1/view/")
					.path(fileCuid)
					.toUriString();

				log.info("Successfully uploaded file: {}", fileDownloadUri);
				return ResponseEntity.ok(fileDownloadUri);
			} finally {
				lock.unlock();
			}
		} catch (IOException ex) {
			log.error("Could not store file {}. Please try again!", fileName, ex);
			return ResponseEntity.internalServerError().body("Could not store file " + fileName + ". Please try again!");
		}
	}

	/**
	 * @param fileCuid .
	 * @param request  .
	 * @return .
	 */
	public ResponseEntity<org.springframework.core.io.Resource> downloadFile(String fileCuid, HttpServletRequest request) {
		try {
			Path filePath = storageService.getFilePath(fileCuid);
			if (!filePath.toFile().exists()) {
				throw new FileNotFoundException();
			}

			org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());
			FileMetadata fileMetadata = storageService.getFileMetadata(fileCuid);

			if (resource.exists() && resource.isReadable() && fileMetadata != null) {
//				String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
				String contentType = fileMetadata.getFileContentType();
				if (isBlank(contentType)) {
					contentType = "application/octet-stream";
				}
				if (isNotBlank(fileMetadata.getOriginalFileName())) {
					log.info("Downloading file: {}, Content-Type: {}", fileMetadata.getOriginalFileName(), contentType);

					String fileName = null;
					fileName = URLEncoder.encode(fileMetadata.getOriginalFileName(), StandardCharsets.UTF_8).replaceAll("\\+", "%20");
					return ResponseEntity.ok()
						.contentType(MediaType.parseMediaType(contentType))
//						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileMetadata.getOriginalFileName().replace(" ", "_") + "\"")
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + fileName)
						.body(resource);
				} else {
					log.info("Downloading file: {}, Content-Type: {}", resource.getFilename(), contentType);
					return ResponseEntity.ok()
						.contentType(MediaType.parseMediaType(contentType))
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
						.body(resource);
				}

			} else {
				log.warn("File not found for download: {}", fileCuid);
				return ResponseEntity.notFound().build();
			}
		} catch (MalformedURLException ex) {
			log.error("Malformed URL for file: {}", fileCuid, ex);
			return ResponseEntity.badRequest().build();
		} catch (IOException ex) {
			log.error("Could not determine file type for file: {}", fileCuid, ex);
			return ResponseEntity.internalServerError().build();
		}
	}

	/**
	 * @return .
	 */
	public ResponseEntity<Boolean> checkAlive() {
		return ResponseEntity.ok()
//			.contentType(MediaType.TEXT_PLAIN)
			.body(true);
	}

	/**
	 * @param fileCuid .
	 * @return .
	 */
	public ResponseEntity<FileMetadata> getFileMeta(String fileCuid) {
		try {
			Path filePath = storageService.getFilePath(fileCuid);
			if (!filePath.toFile().exists()) {
				return ResponseEntity.notFound().build();
			}

			org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());
			FileMetadata fileMetadata = storageService.getFileMetadata(fileCuid);

			if (resource.exists() && resource.isReadable() && fileMetadata != null) {
				return ResponseEntity.ok()
//					.contentType(MediaType.APPLICATION_JSON)
					.body(fileMetadata);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (MalformedURLException ex) {
			log.error("Malformed URL for file: {}", fileCuid, ex);
			return ResponseEntity.badRequest().build();
		} catch (IOException ex) {
			log.error("Could not determine file type for file: {}", fileCuid, ex);
			return ResponseEntity.internalServerError().build();
		}
	}

	/**
	 * @param model    .
	 * @param fileCuid .
	 * @return .
	 */
	public ModelAndView addInfo(ModelAndView model, String fileCuid) {
		try {
			model.addObject("fileCuid", fileCuid);
			Path filePath = storageService.getFilePath(fileCuid);
			if (!filePath.toFile().exists()) {
				model.addObject("notFound", true);
			} else {
				org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());
				FileMetadata fileMetadata = storageService.getFileMetadata(fileCuid);

				if (resource.exists() && resource.isReadable() && fileMetadata != null) {
					model.addObject("contentType", fileMetadata.getFileContentType());
					model.addObject("originalFileName", fileMetadata.getOriginalFileName());
					model.addObject("fileName", fileMetadata.getFileName());
					model.addObject("fileExt", fileMetadata.getFileExt());
					model.addObject("fileMimeType", fileMetadata.getFileMimeType());

					model.addObject("isError", false);
				} else {
					model.addObject("notFound", true);
				}
			}
		} catch (MalformedURLException ex) {
			log.error("Malformed URL for file: {}", fileCuid, ex);
			model.addObject("isError", true);
		} catch (IOException ex) {
			log.error("Could not determine file type for file: {}", fileCuid, ex);
			model.addObject("isError", true);
		}

		return model;
	}
}
