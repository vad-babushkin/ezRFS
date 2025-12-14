package com.iconiux.ezlfs.util;

import java.io.File;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class PathUtils {
	/**
	 *
	 * @param hash .
	 * @return .
	 */
	public static  String hashToPath(String hash) {
		String s = hash;
		if (isBlank(s)) {
			return "";
		}
		String path = "";

		if (s.length() < 3) {
			return path + "___" + File.separator + hash;
		}
		path = path + s.substring(0, 3) + File.separator;
		s = s.substring(3);

		if (s.length() < 3) {
			return path + "___" + File.separator + hash;
		}
		path = path + s.substring(0, 3) + File.separator;
		s = s.substring(3);

		if (s.length() < 3) {
			return path + "___" + File.separator + hash;
		}
		path = path + s.substring(0, 3) + File.separator;

		return path  + hash;
	}
}
