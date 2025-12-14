package com.iconiux.ezlfs.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class FileMetadata implements Serializable {
	private static final long serialVersionUID = 1L;

	private String fileHash;
	private String fileMimeType;
//	private String contentEncoding;
	private String fileContentType;
	private Long fileContentLength;
	private String originalFileName;
	private String fileName;
	private String fileExt;
}
