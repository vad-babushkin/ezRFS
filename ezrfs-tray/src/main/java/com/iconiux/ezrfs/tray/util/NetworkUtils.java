package com.iconiux.ezrfs.tray.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Slf4j
public class NetworkUtils {

	public static final int DEFAULT_TIMEOUT = 60000;

	/**
	 * @param host    .
	 * @param port    .
	 * @param timeout .
	 * @return .
	 */
	public static Pair<Boolean, String> connectToHost(String host, int port, int timeout) {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(host, port), timeout);
			log.info("{}:{} [{}] {} ", host, port, timeout, "OK");
			return Pair.of(true, "ok");
		} catch (IOException  e) {
			log.error("⚠ {}:{} [{}] {} ", host, port, timeout, e.getMessage());
			return Pair.of(false, e.getMessage());
		}
	}

	/**
	 *
	 * @param host .
	 * @param port .
	 * @return .
	 */
	public static Pair<Boolean, String> connectToHost(String host, int port) {
		return connectToHost(host, port, DEFAULT_TIMEOUT);
	}
}
