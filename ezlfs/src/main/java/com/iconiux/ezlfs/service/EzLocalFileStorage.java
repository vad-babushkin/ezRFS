package com.iconiux.ezlfs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iconiux.ezlfs.model.FileMetadata;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.iconiux.ezlfs.util.PathUtils.hashToPath;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@NoArgsConstructor
@Setter
@Getter
public class EzLocalFileStorage implements IFileStorage {
	private static final String METADATA_FILE_EXT = ".file_metadata";

	private String basePath;
	private ObjectMapper objectMapper;

	/**
	 * @param basePath .
	 * @throws IOException .
	 */
	public EzLocalFileStorage(String basePath, ObjectMapper objectMapper) throws IOException {
		this.basePath = basePath;
		this.objectMapper = objectMapper;

		if (isBlank(basePath)) {
			throw new IllegalArgumentException("BasePath cannot be null");
		}

		if (!basePath.endsWith(File.separator)) {
			basePath += File.separator;
		}

		File basePathFile = new File(basePath);

		if (!basePathFile.exists()) {
			if (!basePathFile.mkdirs()) {
				throw new IOException("Ошибка создания базовой директории");
			}
		}

		if (!basePathFile.isDirectory() || !basePathFile.canRead() || !basePathFile.canWrite()) {
			throw new IllegalArgumentException("BasePath must be a directory with read and write permissions");
		}

		this.basePath = basePath;

	}

	/**
	 * @param hash .
	 * @return .
	 */
	@Override
	public Path getFilePath(String hash) throws IOException {
		if (isBlank(hash)) {
			throw new IOException("fileId cannot be blank");
		}

		String filePath = basePath + hashToPath(hash);

		return Paths.get(filePath);
	}

	/**
	 * @param hash .
	 * @return .
	 */
	@Override
	public Path makeParentFilePath(String hash) throws IOException {
		if (isBlank(hash)) {
			throw new IOException("hash cannot be blank");
		}

		String filePath = basePath + hashToPath(hash);
		Path path = Paths.get(filePath);

		if (!path.getParent().toFile().exists()) {
			if (!path.getParent().toFile().mkdirs()) {
				throw new IOException("Ошибка сохранения файла");
			}
		}

		return path.getParent();
	}

	/**
	 * @param path .
	 * @return .
	 */
	@Override
	public Path makeParentFilePath(Path path) throws IOException {
		if (path == null) {
			throw new IOException("filePath cannot be blank");
		}

		if (!path.getParent().toFile().exists()) {
			if (!path.getParent().toFile().mkdirs()) {
				throw new IOException("Ошибка сохранения файла");
			}
		}

		return path.getParent();
	}

	/**
	 * @param hash .
	 * @return .
	 */
	@Override
	public Boolean existFile(String hash) throws IOException {
		if (isBlank(hash)) {
			throw new IOException("fileId cannot be blank");
		}

		String filePath = basePath + hashToPath(hash);
		Path path = Paths.get(filePath);

		return path.toFile().exists();
	}

//	/**
//	 * @param bytes .
//	 * @return .
//	 * @throws IOException .
//	 */
//	@Override
//	public String saveFile(byte[] bytes) throws IOException {
//		String hash = EzHashUtils.mzHash(bytes);
//
//		makeParentFilePath(hash);
//
//		FileUtils.writeByteArrayToFile(filePath.toFile(), bytes);
//
//		return hash;
//	}

	@Override
	public String saveMetadata(FileMetadata fileMetadata) throws IOException {
		Path fileMetadataPath = getFileMetadataPath(fileMetadata.getFileHash());
		makeParentFilePath(fileMetadataPath);

		FileUtils.writeStringToFile(fileMetadataPath.toFile(), objectMapper.writeValueAsString(fileMetadata), StandardCharsets.UTF_8);

		return fileMetadataPath.toString();
	}

	/**
	 * @param hash .
	 * @return .
	 */
	@Override
	public Path getFileMetadataPath(String hash) {
		String fileMetadataPath = basePath + hashToPath(hash) + METADATA_FILE_EXT;

		return Paths.get(fileMetadataPath);
	}

	/**
	 * @param fileMetadata .
	 * @param bytes        .
	 * @return .
	 * @throws IOException .
	 */
	@Override
	public String saveFile(FileMetadata fileMetadata, byte[] bytes) throws IOException {
		Path filePath = getFilePath(fileMetadata.getFileHash());

		makeParentFilePath(filePath);

		FileUtils.writeByteArrayToFile(filePath.toFile(), bytes);
		saveMetadata(fileMetadata);

		return fileMetadata.getFileHash();
	}

	/**
	 * @param hash .
	 * @return .
	 * @throws IOException .
	 */
	@Override
	public byte[] getFileBody(String hash) throws IOException {
		if (isBlank(hash)) {
			throw new IllegalArgumentException("fileId cannot be blank");
		}

		Path filePath = getFilePath(hash);

		if (!filePath.toFile().exists()) {
			throw new FileNotFoundException();
		}

		return FileUtils.readFileToByteArray(filePath.toFile());
	}

	/**
	 * @param hash .
	 * @return .
	 * @throws IOException .
	 */
	@Override
	public FileMetadata getFileMetadata(String hash) throws IOException {
		Path fileMetadataPath = getFileMetadataPath(hash);

		FileMetadata fileMetadata = null;
		if (fileMetadataPath.toFile().exists()) {
			fileMetadata = objectMapper.readValue(FileUtils.readFileToString(fileMetadataPath.toFile(), StandardCharsets.UTF_8), FileMetadata.class);
		}

		return fileMetadata;
	}

	/**
	 * @param hash .
	 * @return .
	 * @throws IOException .
	 */
	@Override
	public boolean delete(String hash) throws IOException {
		if (isBlank(hash)) {
			throw new IllegalArgumentException("fileId cannot be blank");
		}

		Path filePath = getFilePath(hash);

		if (!filePath.toFile().exists()) {
			throw new FileNotFoundException();
		}

		return FileUtils.deleteQuietly(getFilePath(hash).toFile());
	}
}
