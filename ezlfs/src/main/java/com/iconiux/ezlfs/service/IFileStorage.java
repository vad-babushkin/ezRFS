package com.iconiux.ezlfs.service;

import com.iconiux.ezlfs.model.FileMetadata;

import java.io.IOException;
import java.nio.file.Path;

public interface IFileStorage {
	/**
	 * @param fileCuid .
	 * @return .
	 * @throws IOException .
	 */
	Path getFilePath(String fileCuid) throws IOException;

	/**
	 * @param fileCuid .
	 * @return .
	 * @throws IOException .
	 */
	Path makeParentFilePath(String fileCuid) throws IOException;

	Path makeParentFilePath(Path path) throws IOException;

	/**
	 * @param fileCuid .
	 * @return .
	 * @throws IOException .
	 */
	Boolean existFile(String fileCuid) throws IOException;

//	/**
//	 * @param bytes .
//	 * @return .
//	 * @throws IOException .
//	 */
//	String saveFile(byte[] bytes) throws IOException;

	/**
	 * @param fileMetadata .
	 * @return .
	 */
	String saveMetadata(FileMetadata fileMetadata) throws IOException;

	Path getFileMetadataPath(String fileCuid);

	/**
	 * @param fileMetadata .
	 * @param bytes        .
	 * @return .
	 * @throws IOException .
	 */
	String saveFile(FileMetadata fileMetadata, byte[] bytes) throws IOException;

	/**
	 * @param fileCuid .
	 * @return .
	 * @throws IOException .
	 */
	byte[] getFileBody(String fileCuid) throws IOException;

	/**
	 * @param fileCuid .
	 * @return .
	 * @throws IOException .
	 */
	FileMetadata getFileMetadata(String fileCuid) throws IOException;

	/**
	 * @param fileCuid .
	 * @return .
	 * @throws IOException .
	 */
	boolean delete(String fileCuid) throws IOException;
}
