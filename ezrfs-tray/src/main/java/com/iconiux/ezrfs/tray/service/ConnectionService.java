package com.iconiux.ezrfs.tray.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.mizosoft.methanol.*;
import com.iconiux.ezlfs.model.FileMetadata;
import com.iconiux.ezrfs.tray.eventbus.ProgressInfoMessageBean;
import com.iconiux.ezrfs.tray.service.task.RefreshInterNetStatePanelTask;
import com.iconiux.ezrfs.tray.service.task.RefreshRFSStatePanelTask;
import com.iconiux.ezrfs.tray.service.task.RefreshServerStatePanelTask;
import com.iconiux.ezrfs.tray.ui.vo.StorageItem;
import com.iconiux.ezrfs.tray.util.RFSHelper;
import io.github.thibaultmeyer.cuid.CUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bushe.swing.event.EventBus;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import static com.github.mizosoft.methanol.MutableRequest.GET;
import static com.iconiux.ezrfs.tray.ConstantHolder.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Getter
public class ConnectionService {

	public static final int WARNING_DELAY = 60 * 1000;
	public static final int ERROR_DELAY = 10 * 60 * 1000;

	private Configuration applicationConfiguration;
	private ObjectMapper objectMapper;

	String username;
	String password;

	String host;
	String uploadBaseUrl;
	String viewAliveBaseUrl;
	String viewBaseUrl;
	String downloadFileBaseUrl;
	String downloadMetaBaseUrl;

	Methanol client = Methanol.create();

	public ConnectionService(Configuration applicationConfiguration) {
		this.applicationConfiguration = applicationConfiguration;

		this.username = applicationConfiguration.getString(RSF_SERVER_UPLOAD_USERNAME);
		this.password = applicationConfiguration.getString(RSF_SERVER_UPLOAD_PASSWORD);

		this.host = applicationConfiguration.getString(RSF_SERVER_HOST);
		this.uploadBaseUrl = HTTP_PREFIX + host + applicationConfiguration.getString(RSF_SERVER_UPLOAD_URL);
		this.viewBaseUrl = HTTP_PREFIX + host + applicationConfiguration.getString(RSF_SERVER_VIEW_URL);
		this.viewAliveBaseUrl = HTTP_PREFIX + host + applicationConfiguration.getString(RSF_SERVER_VIEW_ALIVE_URL);
		this.downloadFileBaseUrl = HTTP_PREFIX + host + applicationConfiguration.getString(RSF_SERVER_DOWNLOAD_FILE_URL);
		this.downloadMetaBaseUrl = HTTP_PREFIX + host + applicationConfiguration.getString(RSF_SERVER_DOWNLOAD_META_URL);

		this.objectMapper = createObjectMapper();
	}

	/**
	 * @return .
	 */
	public ObjectMapper createObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		objectMapper.findAndRegisterModules();

		return objectMapper;
	}

	/**
	 * @param file .
	 * @return .
	 * @throws IOException          .
	 * @throws InterruptedException .
	 */
	HttpResponse<String> uploadFileWithProgress(File file) throws IOException, InterruptedException {
		MultipartBodyPublisher multipartBody = MultipartBodyPublisher.newBuilder()
			.textPart("title", "no_name")
			.filePart("file", file.toPath(), MediaType.APPLICATION_OCTET_STREAM)
			.build();
		ProgressTracker progressTracker = ProgressTracker.newBuilder()
			.bytesTransferredThreshold(500 * 1024) // 500 kB
			.build();

		HttpRequest.BodyPublisher trackingRequestBody = progressTracker.tracking(multipartBody, this::onProgress);

		MutableRequest mutableRequest = MutableRequest
			.POST(uploadBaseUrl, trackingRequestBody)
			.header("Authorization", getBasicAuthenticationHeader(this.username, this.password));

		return client.send(mutableRequest, HttpResponse.BodyHandlers.ofString());
	}

	/**
	 * @param progress .
	 */
	private void onProgress(ProgressTracker.Progress progress) {
		if (progress.determinate()) { // Overall progress can be measured
			double percent = 100 * progress.value();
			log.info("Uploaded {} from {} bytes ({})", progress.totalBytesTransferred(), progress.contentLength(), percent);
			EventBus.publish(new ProgressInfoMessageBean((int) percent));
		} else {
			log.info("Uploaded " + progress.totalBytesTransferred());
		}

		if (progress.done()) {
			EventBus.publish(new ProgressInfoMessageBean(100));
		}
	}

	/**
	 * @param storageItem .
	 * @return .
	 * @throws IOException          .
	 * @throws InterruptedException .
	 */
	public byte[] downloadFile(StorageItem storageItem) throws IOException, InterruptedException {
		MutableRequest mutableRequest = GET(storageItem.getUrl()).header("Accept", "application/json");
		HttpResponse<byte[]> response = client.send(mutableRequest, HttpResponse.BodyHandlers.ofByteArray());
		return response.body();
	}

	/**
	 * @param storageItem .
	 * @return .
	 * @throws IOException          .
	 * @throws InterruptedException .
	 */
	public byte[] downloadFileWithProgress(StorageItem storageItem) throws IOException, InterruptedException {
		MutableRequest mutableRequest = GET(storageItem.getUrl()).header("Accept", "application/json");
		ProgressTracker progressTracker = ProgressTracker.newBuilder()
			.bytesTransferredThreshold(500 * 1024) // 500 kB
			.build();

		HttpResponse.BodyHandler<byte[]> downloadingBodyHandler = HttpResponse.BodyHandlers.ofByteArray();
		HttpResponse.BodyHandler<byte[]> trackingRequestBody = progressTracker.tracking(downloadingBodyHandler, this::onProgress);
		HttpResponse<byte[]> response = client.send(mutableRequest, trackingRequestBody);

		return response.body();
	}

	/**
	 * @param storageItem .
	 * @return .
	 * @throws IOException          .
	 * @throws InterruptedException .
	 */
	public FileMetadata downloadMeta(StorageItem storageItem) throws IOException, InterruptedException {
		String metaUrl = RFSHelper.file2MetaUrl(storageItem.getUrl());
		if (isNotBlank(metaUrl)) {
			HttpResponse<String> response = client.send(
				GET(metaUrl).header("Accept", "application/json"),
				HttpResponse.BodyHandlers.ofString());

			return objectMapper.readValue(response.body(), FileMetadata.class);
		} else {
			throw new IOException("Ошибка парсинга метаурла");
		}
	}

	/**
	 * @return .
	 * @throws IOException          .
	 * @throws InterruptedException .
	 */
	public Boolean checkAlive() throws IOException, InterruptedException {
		HttpResponse<String> response = client.send(GET(viewAliveBaseUrl).header("Accept", "application/json"), HttpResponse.BodyHandlers.ofString());
		if (isNotBlank(response.body())) {
			return StringUtils.contains(response.body(), "true");
		} else {
			return false;
		}
	}

	/**
	 * @param username .
	 * @param password .
	 * @return .
	 */
	private static String getBasicAuthenticationHeader(String username, String password) {
		String valueToEncode = username + ":" + password;
		return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
	}

	/**
	 * Проверка доступа к интернет
	 */
	public void checkInternet(String host, int port) {
		try {
			(new RefreshInterNetStatePanelTask(host, port)).execute();
		} catch (Exception e) {
			log.error(null, e);
		}
	}

	/**
	 *
	 */
	public void checkServer(String host, int port) {
		try {
			(new RefreshServerStatePanelTask(host, port)).execute();
		} catch (Exception e) {
			log.error(null, e);
		}
	}

	/**
	 *
	 */
	public void checkRFS() {
		try {
			(new RefreshRFSStatePanelTask(this)).execute();
		} catch (Exception e) {
			log.error(null, e);
		}
	}

	public String upload(File uploadFile) throws IOException, InterruptedException {
		return uploadFileWithProgress(uploadFile).body();
	}

	/**
	 * @param storageItem .
	 * @return .
	 * @throws IOException          .
	 * @throws InterruptedException .
	 */
	public Pair<Integer, String> download(StorageItem storageItem) throws IOException, InterruptedException {
		byte[] bytes = downloadFileWithProgress(storageItem);
		File file = resolveFile(storageItem);
		FileUtils.writeByteArrayToFile(file, bytes);

		return Pair.of(bytes.length, file.getName());
	}

	/**
	 * @param storageItem .
	 * @return .
	 */
	private File resolveFile(StorageItem storageItem) throws IOException {
		Path downloadPath = Paths.get(applicationConfiguration.getString(DOWNLOADS_PATH_KEY));
		log.info("downloadPath {}", downloadPath);

		File downloadFile = Paths.get(downloadPath.toString(), storageItem.getFileName()).toFile();
		log.info("downloadFile {}", downloadFile);

		int cnt = 1;
		while (downloadFile.exists()) {
			downloadFile = Paths.get(downloadPath.toString(), CUID.randomCUID2(cnt) + "_" + storageItem.getFileName()).toFile();
			log.info("попытка смена имени: cnt {} downloadFile {}", cnt, downloadFile);
			cnt++;

			if (cnt > 20) {
				throw new IOException("Статистика периеменования против нас");
			}
		}

		return downloadFile;
	}

	/**
	 * @param viewUrl .
	 * @return .
	 */
	public Boolean isDownloadFileUrl(String viewUrl) throws MalformedURLException {
		return StringUtils.startsWith(viewUrl, downloadFileBaseUrl);
	}

	/**
	 * @param viewUrl .
	 * @return .
	 */
	public Boolean isDownloadFileUrlSafe(String viewUrl) {
		try {
			return isDownloadFileUrl(viewUrl);
		} catch (MalformedURLException e) {
			log.error(null, e);
			return false;
		}
	}

	/**
	 * @param viewUrl .
	 * @return .
	 */
	public Boolean isViewUrl(String viewUrl) throws MalformedURLException {
		return StringUtils.startsWith(viewUrl, viewBaseUrl);
	}

	/**
	 * @param fileCuid .
	 * @return .
	 */
	public String buildDownloadFileUrl(String fileCuid) {
		return downloadFileBaseUrl + "/" + fileCuid;
	}

	/**
	 * @param fileCuid .
	 * @return .
	 */
	public String buildDownloadMetaUrl(String fileCuid) {
		return downloadMetaBaseUrl + "/" + fileCuid;
	}

	/**
	 * @param metaUrl .
	 * @return .
	 */
	public boolean checkExist(String metaUrl) {
		HttpResponse<String> response = null;
		try {
			response = client.send(GET(metaUrl).header("Accept", "application/json"), HttpResponse.BodyHandlers.ofString());
			return response.statusCode() == 200;
		} catch (IOException | InterruptedException e) {
			log.error(null, e);
			return false;
		}
	}
}
