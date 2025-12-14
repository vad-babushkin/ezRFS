package com.iconiux.ezlfs.service;

import com.iconiux.ezlfs.model.FileMetadata;

import java.io.IOException;
import java.nio.file.Path;

public interface IFileStorage {
	/**
	 * @param hash .
	 * @return .
	 * @throws IOException .
	 */
	Path getFilePath(String hash) throws IOException;

	/**
	 * @param hash .
	 * @return .
	 * @throws IOException .
	 */
	Path makeParentFilePath(String hash) throws IOException;

	Path makeParentFilePath(Path path) throws IOException;

	/**
	 * @param hash .
	 * @return .
	 * @throws IOException .
	 */
	Boolean existFile(String hash) throws IOException;

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

	Path getFileMetadataPath(String hash);

	/**
	 * @param fileMetadata .
	 * @param bytes        .
	 * @return .
	 * @throws IOException .
	 */
	String saveFile(FileMetadata fileMetadata, byte[] bytes) throws IOException;

	/**
	 * @param hash .
	 * @return .
	 * @throws IOException .
	 */
	byte[] getFileBody(String hash) throws IOException;

	/**
	 * @param hash .
	 * @return .
	 * @throws IOException .
	 */
	FileMetadata getFileMetadata(String hash) throws IOException;

	/**
	 * @param hash .
	 * @return .
	 * @throws IOException .
	 */
	boolean delete(String hash) throws IOException;
}
