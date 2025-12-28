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

import static com.iconiux.ezlfs.util.PathUtils.cuidToPath;
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
	 * @param fileCuid .
	 * @return .
	 */
	@Override
	public Path getFilePath(String fileCuid) throws IOException {
		if (isBlank(fileCuid)) {
			throw new IOException("fileId cannot be blank");
		}

		String filePath = basePath + cuidToPath(fileCuid);

		return Paths.get(filePath);
	}

	/**
	 * @param fileCuid .
	 * @return .
	 */
	@Override
	public Path makeParentFilePath(String fileCuid) throws IOException {
		if (isBlank(fileCuid)) {
			throw new IOException("fileCuid cannot be blank");
		}

		String filePath = basePath + cuidToPath(fileCuid);
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
	 * @param fileCuid .
	 * @return .
	 */
	@Override
	public Boolean existFile(String fileCuid) throws IOException {
		if (isBlank(fileCuid)) {
			throw new IOException("fileId cannot be blank");
		}

		String filePath = basePath + cuidToPath(fileCuid);
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
		Path fileMetadataPath = getFileMetadataPath(fileMetadata.getFileCuid());
		makeParentFilePath(fileMetadataPath);

		FileUtils.writeStringToFile(fileMetadataPath.toFile(), objectMapper.writeValueAsString(fileMetadata), StandardCharsets.UTF_8);

		return fileMetadataPath.toString();
	}

	/**
	 * @param fileCuid .
	 * @return .
	 */
	@Override
	public Path getFileMetadataPath(String fileCuid) {
		String fileMetadataPath = basePath + cuidToPath(fileCuid) + METADATA_FILE_EXT;

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
		Path filePath = getFilePath(fileMetadata.getFileCuid());

		makeParentFilePath(filePath);

		FileUtils.writeByteArrayToFile(filePath.toFile(), bytes);
		saveMetadata(fileMetadata);

		return fileMetadata.getFileCuid();
	}

	/**
	 * @param fileCuid .
	 * @return .
	 * @throws IOException .
	 */
	@Override
	public byte[] getFileBody(String fileCuid) throws IOException {
		if (isBlank(fileCuid)) {
			throw new IllegalArgumentException("fileId cannot be blank");
		}

		Path filePath = getFilePath(fileCuid);

		if (!filePath.toFile().exists()) {
			throw new FileNotFoundException();
		}

		return FileUtils.readFileToByteArray(filePath.toFile());
	}

	/**
	 *
	 * @param fileCuid .
	 * @return .
	 * @throws IOException .
	 */
	@Override
	public FileMetadata getFileMetadata(String fileCuid) throws IOException {
		Path fileMetadataPath = getFileMetadataPath(fileCuid);

		FileMetadata fileMetadata = null;
		if (fileMetadataPath.toFile().exists()) {
			fileMetadata = objectMapper.readValue(FileUtils.readFileToString(fileMetadataPath.toFile(), StandardCharsets.UTF_8), FileMetadata.class);
		}

		return fileMetadata;
	}

	/**
	 * @param fileCuid .
	 * @return .
	 * @throws IOException .
	 */
	@Override
	public boolean delete(String fileCuid) throws IOException {
		if (isBlank(fileCuid)) {
			throw new IllegalArgumentException("fileId cannot be blank");
		}

		Path filePath = getFilePath(fileCuid);

		if (!filePath.toFile().exists()) {
			throw new FileNotFoundException();
		}

		return FileUtils.deleteQuietly(getFilePath(fileCuid).toFile());
	}
}
