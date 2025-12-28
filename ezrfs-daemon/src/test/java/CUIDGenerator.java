import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class CUIDGenerator {
	private static final char[] BASE36_CHARS = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
	private static final AtomicInteger COUNTER = new AtomicInteger(0);
	private static long lastTimestamp = -1L;

	public static String generateCUID() {
		long timestamp = System.currentTimeMillis();

		int counterValue;
		synchronized (CUIDGenerator.class) {
			if (timestamp == lastTimestamp) {
				counterValue = COUNTER.incrementAndGet();
			} else {
				lastTimestamp = timestamp;
				COUNTER.set(0);
				counterValue = COUNTER.get();
			}
		}

		String timestampBase36 = encodeBase36(timestamp);
		String counterBase36 = encodeBase36(counterValue);
		String fingerprint = getMachineFingerprint();
		String randomString = getRandomString(4);

		return "c" + timestampBase36 + counterBase36 + fingerprint + randomString;
	}

	private static String encodeBase36(long value) {
		StringBuilder result = new StringBuilder();
		while (value > 0) {
			result.insert(0, BASE36_CHARS[(int) (value % 36)]);
			value /= 36;
		}
		return result.toString();
	}

	private static String getMachineFingerprint() {
		try {
			InetAddress ip = InetAddress.getLocalHost();
			String hostname = ip.getHostName();
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hashBytes = md.digest(hostname.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : hashBytes) {
				sb.append(BASE36_CHARS[(b & 0xFF) % 36]);
			}
			return sb.toString().substring(0, 4);
		} catch (UnknownHostException | NoSuchAlgorithmException e) {
			throw new RuntimeException("Failed to get machine fingerprint", e);
		}
	}

	private static String getRandomString(int length) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(BASE36_CHARS[random.nextInt(36)]);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(generateCUID());
	}
}
