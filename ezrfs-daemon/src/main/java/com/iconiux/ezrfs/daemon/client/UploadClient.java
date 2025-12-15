//package com.iconiux.ezrfs.daemon.client;
//
//import com.iconiux.ezrfs.daemon.config.CoreFeignConfiguration;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.multipart.MultipartFile;
//
//@FeignClient(value = "uploadClient",
//	url = "${ezrfs.api.url}",
//	configuration = CoreFeignConfiguration.class)
//public interface UploadClient {
//	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	String fileUpload(@RequestPart(value = "file") MultipartFile file);
//}
