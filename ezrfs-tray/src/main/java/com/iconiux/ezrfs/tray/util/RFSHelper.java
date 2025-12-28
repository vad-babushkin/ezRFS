package com.iconiux.ezrfs.tray.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class RFSHelper {

	/**
	 * @param viewUrl .
	 * @return .
	 */
	public static String view2FileUrl(String viewUrl) {
		return StringUtils.replace(viewUrl, "/view/", "/download/file/");
	}

	/**
	 * @param viewUrl .
	 * @return .
	 */
	public static String file2ViewUrl(String viewUrl) {
		return StringUtils.replace(viewUrl, "/download/file/", "/view/");
	}

	/**
	 * @param viewUrl .
	 * @return .
	 */
	public static String file2MetaUrl(String viewUrl) {
		return StringUtils.replace(viewUrl, "/download/file/", "/download/meta/");
	}

	/**
	 * @param urlStr .
	 * @return .
	 */
	public static String cutCuidFromUrl(String urlStr) throws MalformedURLException {
		URL url = new URL(urlStr);
		String path = url.getPath();

		return StringUtils.substringAfterLast(path, "/");
	}

	/**
	 * @param urlStr .
	 * @return .
	 */
	public static String cutCuidFromUrlSafe(String urlStr) {
		try {
			return cutCuidFromUrl(urlStr);
		} catch (MalformedURLException e) {
			log.error(null, e);
		}
		return "---";
	}

	/**
	 * @param urlStr .
	 * @return .
	 */
	public static String cutPathFromUrl(String urlStr) throws MalformedURLException {
		URL url = new URL(urlStr);

		return url.getPath();
	}

	/**
	 * @param urlStr .
	 * @return .
	 */
	public static String cutPathFromUrlSafe(String urlStr) {
		try {
			return cutPathFromUrl(urlStr);
		} catch (MalformedURLException e) {
			log.error(null, e);
		}

		return "---";
	}
}
