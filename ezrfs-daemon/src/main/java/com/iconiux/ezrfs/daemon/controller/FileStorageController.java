package com.iconiux.ezrfs.daemon.controller;

import com.iconiux.ezrfs.daemon.service.TFileService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@NoArgsConstructor
public class FileStorageController {

	@Autowired
	private TFileService fileService;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		return fileService.uploadFile(file);
	}

	@GetMapping("/download/{fileHash}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileHash, HttpServletRequest request) {
		return fileService.downloadFile(fileHash, request);
	}
}