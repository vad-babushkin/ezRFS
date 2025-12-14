package com.iconiux.ezrfs.daemon.service;

import com.iconiux.ezlfs.model.FileMetadata;
import com.iconiux.ezlfs.service.IFileStorage;
import com.iconiux.ezlfs.util.EzHashUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Path;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@Service
@Slf4j
public class TFileService {
	@Autowired
	private IFileStorage storageService;

	/**
	 * @param file .
	 * @return .
	 */
	public ResponseEntity<String> uploadFile(MultipartFile file) {
		String fileName = file.getName();
		try {
			String hash = EzHashUtils.mzHash(file.getBytes());

			FileMetadata fileMetadata = new FileMetadata();
			fileMetadata.setFileHash(hash);
			fileMetadata.setOriginalFileName(file.getOriginalFilename());
			fileMetadata.setFileName(fileName);
			fileMetadata.setFileContentType(file.getContentType());
			fileMetadata.setFileContentLength(file.getSize());
			fileMetadata.setFileExt(FilenameUtils.getExtension(file.getOriginalFilename()));
			String fileHash = storageService.saveFile(fileMetadata, file.getBytes());

			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/v1/download/")
				.path(fileHash)
				.toUriString();

			log.info("Successfully uploaded file: {}", fileDownloadUri);
			return ResponseEntity.ok(fileDownloadUri);

		} catch (IOException ex) {
			log.error("Could not store file {}. Please try again!", fileName, ex);
			return ResponseEntity.internalServerError().body("Could not store file " + fileName + ". Please try again!");
		}
	}

	/**
	 * @param fileHash .
	 * @param request  .
	 * @return .
	 */
	public ResponseEntity<org.springframework.core.io.Resource> downloadFile(String fileHash, HttpServletRequest request) {
		try {
			Path filePath = storageService.getFilePath(fileHash);
			if (!filePath.toFile().exists()) {
				throw new FileNotFoundException();
			}

			org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());
			FileMetadata fileMetadata = storageService.getFileMetadata(fileHash);

			if (resource.exists() && resource.isReadable() && fileMetadata != null) {
//				String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
				String contentType = fileMetadata.getFileContentType();
				if (isBlank(contentType)) {
					contentType = "application/octet-stream";
				}
				if (isNotBlank(fileMetadata.getOriginalFileName())) {
					log.info("Downloading file: {}, Content-Type: {}", fileMetadata.getOriginalFileName(), contentType);

					String fileName = null;
					try {
						fileName = URLEncoder.encode(fileMetadata.getOriginalFileName(), "UTF-8").replaceAll("\\+", "%20");
					} catch (UnsupportedEncodingException e) {
						log.error("{}", e.getMessage(), e);
					}
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
				log.warn("File not found for download: {}", fileHash);
				return ResponseEntity.notFound().build();
			}
		} catch (MalformedURLException ex) {
			log.error("Malformed URL for file: {}", fileHash, ex);
			return ResponseEntity.badRequest().build();
		} catch (IOException ex) {
			log.error("Could not determine file type for file: {}", fileHash, ex);
			return ResponseEntity.internalServerError().build();
		}
	}
}
