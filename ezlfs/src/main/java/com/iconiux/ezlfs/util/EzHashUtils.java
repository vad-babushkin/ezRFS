package com.iconiux.ezlfs.util;

import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
public class EzHashUtils {
	public static String mzHash(Long l) {
		return mzHash(String.valueOf(l));
	}

	public static String mzHash(Integer i) {
		return mzHash(String.valueOf(i));
	}

	public static String mzHash(Double d) {
		return mzHash(String.valueOf(d));
	}

	public static String mzHash(String s) {
		return hash256(s.getBytes(UTF_8));
	}

	public static String mzHash(byte[] bytes) {
		return hash256(bytes);
	}

	public static String hash256(byte[] bytes) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(bytes);
			BigInteger big = new BigInteger(DatatypeConverter.printHexBinary(messageDigest.digest()), 16);
			return big.toString(36);
		} catch (NoSuchAlgorithmException e) {
//			log.error("", e);
			return null;
		}
	}
}
