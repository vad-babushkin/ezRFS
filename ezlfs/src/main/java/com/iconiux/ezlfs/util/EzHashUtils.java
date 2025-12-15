package com.iconiux.ezlfs.util;

import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;

//import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
public class EzHashUtils {
	public static String ezHash(Long l) {
		return ezHash(String.valueOf(l));
	}

	public static String ezHash(Integer i) {
		return ezHash(String.valueOf(i));
	}

	public static String ezHash(Double d) {
		return ezHash(String.valueOf(d));
	}

	public static String ezHash(String s) {
		return hash256(s.getBytes(UTF_8));
	}

	public static String ezHash(byte[] bytes) {
		return hash256(bytes);
	}

//	public static String hash256(byte[] bytes) {
//		try {
//			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
//			messageDigest.update(bytes);
//			BigInteger big = new BigInteger(DatatypeConverter.printHexBinary(messageDigest.digest()), 16);
//			return big.toString(36);
//		} catch (NoSuchAlgorithmException e) {
////			log.error("", e);
//			return null;
//		}
//	}

	public static String hash256(byte[] bytes) {
		String sha256hex = Hashing.sha256()
			.hashBytes(bytes)
			.toString();
		BigInteger big = new BigInteger(sha256hex, 16);
		return big.toString(36);
	}
}
