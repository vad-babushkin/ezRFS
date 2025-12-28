package com.iconiux.ezrfs.daemon.controller;

import com.google.common.base.Stopwatch;
import com.iconiux.ezlfs.model.FileMetadata;
import com.iconiux.ezrfs.daemon.service.OriginService;
import com.iconiux.ezrfs.daemon.service.TFileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@NoArgsConstructor
public class FSController {

	@Autowired
	private TFileService fileService;

	@Autowired
	private OriginService originService;

	/**
	 * @return .
	 */
	@GetMapping(value = "/view/alive")
	public ResponseEntity<Boolean> checkAlive() {
		return fileService.checkAlive();
	}

	@GetMapping("/view/{fileCuid}")
	public ModelAndView info(@PathVariable String fileCuid, HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		model.addObject("version", originService.getBuildProperties().getVersion());

		model = fileService.addInfo(model, fileCuid);

		return model;
	}

	/**
	 * @param file .
	 * @return .
	 */
	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		return fileService.uploadFile(file);
	}

	/**
	 * @param fileCuid .
	 * @param request  .
	 * @return .
	 */
	@GetMapping("/download/file/{fileCuid}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileCuid, HttpServletRequest request) {
		return fileService.downloadFile(fileCuid, request);
	}

	/**
	 * @param fileCuid .
	 * @return .
	 */
	@GetMapping(value = "/download/meta/{fileCuid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FileMetadata> getFileMeta(@PathVariable String fileCuid) {
		return fileService.getFileMeta(fileCuid);
	}
}