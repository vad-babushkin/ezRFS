//package com.iconiux.ezlfs.service;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FileUtils;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
//import static org.apache.commons.lang3.StringUtils.isBlank;
//
//@Slf4j
//public class DiskFileStorageService implements IFileStorage {
//	private static final String METADATA_FILE_EXT = ".fsm";
//	private static final String FILE_EXT = "";
//
//	/**
//	 * Base local path for file storage
//	 */
//	private String basePath;
//
//	/**
//	 * @param basePath .
//	 */
//	public DiskFileStorageService(String basePath) {
//		try {
//			setBasePath(basePath);
//			this.objectMapper = new ObjectMapper()
//					.setSerializationInclusion(JsonInclude.Include.ALWAYS)
//					.configure(SerializationFeature.INDENT_OUTPUT, true)
//					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//					.findAndRegisterModules();
//
//		} catch (IOException e) {
//			log.error("", e);
//		}
//	}
//
//	/**
//	 * @param basePath .
//	 * @throws IOException .
//	 */
//	private void setBasePath(String basePath) throws IOException {
//		if (basePath == null) {
//			throw new IllegalArgumentException("BasePath cannot be null");
//		}
//
//		if (!basePath.endsWith(File.separator)) {
//			basePath += File.separator;
//		}
//
//		File basePathFile = new File(basePath);
//
//		if (!basePathFile.exists()) {
//			if (!basePathFile.mkdirs()) {
//				throw new IOException("Ошибка создания базовой директории");
//			}
//		}
//
//		if (!basePathFile.isDirectory() || !basePathFile.canRead() || !basePathFile.canWrite()) {
//			throw new IllegalArgumentException("BasePath must be a directory with read and write permissions");
//		}
//
//		this.basePath = basePath;
//	}
//
//	/**
//	 * @param hash .
//	 * @return .
//	 */
//	@Override
//	public File getFilePath(String hash) {
//		String filePath = basePath + hashToPath(hash) + FILE_EXT;
//		return new File(filePath);
//	}
//
//	/**
//	 *
//	 * @param hash .
//	 * @return .
//	 */
//	@Override
//	public Boolean checkAllExists(String hash){
//		return getFilePath(hash).exists() && getFileMetadataPath(hash).exists();
//	}
//
//	/**
//	 *
//	 * @param hash .
//	 * @return .
//	 */
//	@Override
//	public String getRelativeFilePath(String hash) {
//		return hashToPath(hash) + FILE_EXT;
//	}
//
//	/**
//	 * @param hash .
//	 * @return .
//	 */
//	@Override
//	public File getFileMetadataPath(String hash) {
//		String fileMetadataPath = basePath + hashToPath(hash) + METADATA_FILE_EXT;
//		return new File(fileMetadataPath);
//	}
//
//	/**
//	 * @param fileStorageItem .
//	 * @return .
//	 * @throws IOException .
//	 */
//	@Override
//	public String save(FileStorageItem fileStorageItem) throws IOException {
//		String hash = MzHashUtils.mzHash(fileStorageItem.getBytes());
//		saveFile(fileStorageItem.getBytes(), hash);
//		saveMetadata(fileStorageItem.getFileMetadata(), hash);
//
//		return hash;
//	}
//
//	/**
//	 * @param bytes .
//	 * @param hash  .
//	 * @throws IOException .
//	 */
//	public void saveFile(byte[] bytes, String hash) throws IOException {
//		File savedFile = getFilePath(hash);
//		FileUtils.writeByteArrayToFile(savedFile, bytes);
//	}
//
//	/**
//	 * @param fileMetadata .
//	 * @param hash         .
//	 * @throws IOException .
//	 */
//	public void saveMetadata(FileMetadata fileMetadata, String hash) throws IOException {
//		File savedFile = getFileMetadataPath(hash);
////		FileUtils.writeByteArrayToFile(savedFile, SerializationUtils.serialize(fileMetadata));
//		FileUtils.writeStringToFile(savedFile, objectMapper.writeValueAsString(fileMetadata), StandardCharsets.UTF_8);
//	}
//
//	/**
//	 * @param hash .
//	 * @return .
//	 */
//	@Override
//	public FileStorageItem get(String hash) {
//		try {
//			return new FileStorageItem(getFile(hash), getFileMetadata(hash));
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	/**
//	 * @param hash .
//	 * @return .
//	 */
//	@Override
//	public byte[] getFile(String hash) throws IOException {
//		File savedFile = getFilePath(hash);
//
//		if (!savedFile.exists()) {
//			throw new FileNotFoundException();
//		}
//
//		return FileUtils.readFileToByteArray(savedFile);
//	}
//
//	/**
//	 * @param hash .
//	 * @return .
//	 */
//	@Override
//	public FileMetadata getFileMetadata(String hash) throws IOException {
//		File savedFile = getFileMetadataPath(hash);
//
//		FileMetadata fileMetadata = null;
//		if (savedFile.exists()) {
////			fileMetadata = (FileMetadata) SerializationUtils.deserialize(FileUtils.readFileToByteArray(savedFile));
//			fileMetadata = objectMapper.readValue(FileUtils.readFileToByteArray(savedFile), FileMetadata.class);
//		}
//
//		return fileMetadata;
//	}
//
//	/**
//	 * @param hash .
//	 * @return .
//	 */
//	@Override
//	public boolean delete(String hash) {
//
//		if (isBlank(hash)) {
//			throw new IllegalArgumentException("fileId cannot be blank");
//		}
//
//		boolean deleted = FileUtils.deleteQuietly(getFilePath(hash));
//		FileUtils.deleteQuietly(getFileMetadataPath(hash));
//		return deleted;
//	}
//}
