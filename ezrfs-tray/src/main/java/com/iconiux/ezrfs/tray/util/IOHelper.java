package com.iconiux.ezrfs.tray.util;

import com.iconiux.ezrfs.tray.ConstantHolder;
import com.iconiux.ezrfs.tray.TrayApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.interpol.ConfigurationInterpolator;
import org.apache.commons.configuration2.interpol.ExprLookup;
import org.apache.commons.configuration2.interpol.Lookup;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
public class IOHelper {

	private IOHelper() {
		// utility class
	}

	/**
	 * Перекодировка входного стрима в массив байтов
	 *
	 * @param is входной поток
	 * @return массив байтов
	 * @throws java.io.IOException .
	 */
	public static byte[] streamToBytes(InputStream is) throws IOException {
		byte[] xferBuffer = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i;

		while ((i = is.read(xferBuffer)) > 0) {
			baos.write(xferBuffer, 0, i);
		}
		return baos.toByteArray();
	}

	/**
	 * Перекодировка входного стрима в объект
	 *
	 * @param is .
	 * @return .
	 * @throws java.io.IOException    .
	 * @throws ClassNotFoundException .
	 */
	public static Object streamToObject(InputStream is) throws IOException, ClassNotFoundException {
		return bytesToObject(streamToBytes(is));
	}

	/**
	 * Returns a byte array from the given object.
	 *
	 * @param object to convert .
	 * @return byte array from the object .
	 * @throws java.io.IOException .
	 */
	public static byte[] objectToBytes(Object object) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(baos);
		os.writeObject(object);
		return baos.toByteArray();
	}

	/**
	 * Returns a object from the given byte array.
	 *
	 * @param bytes array to convert
	 * @return object
	 * @throws java.io.IOException    .
	 * @throws ClassNotFoundException .
	 */
	public static Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream is = new ObjectInputStream(bais);
		return is.readObject();
	}

	/**
	 * Вернуть стрим, полученный из массива байтов.
	 * Лучше делать напрямую, а то засмеют
	 *
	 * @param bytes .
	 * @return .
	 * @throws IOException            .
	 * @throws ClassNotFoundException .
	 */
	public static InputStream bytesToStream(byte[] bytes) throws IOException, ClassNotFoundException {
		return new ByteArrayInputStream(bytes);
	}

	/**
	 * Return a file with the given filename creating the necessary
	 * directories if not present.
	 *
	 * @param destDir  .
	 * @param filename The file
	 * @return The created File instance
	 */
	public static File createFile(File destDir, String filename) throws IOException {
		File file = new File(destDir, filename);
		File parent = file.getParentFile();
		if (parent != null) parent.mkdirs();
		file.createNewFile();
		return file;
	}

	/**
	 * Strip a filename of its <i>last</i> extension (the portion
	 * immediately following the last dot character, if any)
	 *
	 * @param filename The filename
	 * @return The filename sans extension
	 * @deprecated To be removed in cocoon 2.3
	 */
	public static String baseName(String filename) {
		int i = filename.lastIndexOf('.');
		return (i > -1) ? filename.substring(0, i) : filename;
	}

	/**
	 * @param reader .
	 * @return .
	 * @throws IOException .
	 */
	public static String readerToString(Reader reader) throws IOException {
		StringBuffer sb = new StringBuffer();
		char[] b = new char[4096];
		int n;
		while ((n = reader.read(b)) > 0) {
			sb.append(b, 0, n);
		}

		return sb.toString();
	}

	public static String readerToStringWithoutCRLF(Reader reader) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(reader);
		StringBuffer sbuf = new StringBuffer();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			sbuf.append(line);
		}
		return sbuf.toString();
	}

	/**
	 * @param file .
	 * @return .
	 */
	public static Properties loadApplicationProperties(String file) {
		Properties properties = new Properties();
		FileInputStream sf = null;
		try {
			if (file == null) {
				sf = new FileInputStream(ConstantHolder.APPLICATION_PROPERTIES);
			} else {
				sf = new FileInputStream(file);
			}
			properties.load(sf);
		} catch (IOException e) {
			log.error(null, e);
		}
		return properties;
	}

	/**
	 * @return .
	 */
	public static Properties loadApplicationProperties() {
		Properties properties = new Properties();
		try {
			properties.load(TrayApplication.class.getClassLoader().getResourceAsStream(ConstantHolder.APPLICATION_PROPERTIES));
		} catch (IOException e) {
			log.error(null, e);
		}
		log.info("{}", properties);
		return properties;
	}

	/**
	 * @return .
	 */
	public static Configuration loadApplicationConfiguration() {
//		System.setProperty("user.home", "/usr/lib");
		Map<String, Lookup> lookups = new HashMap<>(ConfigurationInterpolator.getDefaultPrefixLookups());
		ExprLookup.Variables variables = new ExprLookup.Variables();
		variables.add(new ExprLookup.Variable("System", "Class:java.lang.System"));
		ExprLookup exprLookup = new ExprLookup(variables);
		exprLookup.setInterpolator(new ConfigurationInterpolator());
		lookups.put("expr", exprLookup);

		Configurations configs = new Configurations();
		Configuration config = new BaseConfiguration();
		try {
			config = configs.properties(TrayApplication.class.getClassLoader().getResource(ConstantHolder.APPLICATION_PROPERTIES));
			((PropertiesConfiguration) config).setPrefixLookups(lookups);
			// access configuration properties
		} catch (ConfigurationException cex) {
			log.error(null, cex);
		}
		return config;
	}

	/**
	 * сохранение  Properties в строку
	 *
	 * @param properties .
	 * @return .
	 */
	public static String propertiesToString(Properties properties) {
		if (properties == null) return "";
		StringWriter writer = new StringWriter();
		try {
			properties.store(writer, "---");
		} catch (IOException e) {
			log.error(null, e);
		}
		return writer.toString();
	}

	/**
	 * загрузка  строки  в Properties
	 *
	 * @param s .
	 * @return .
	 */
	public static Properties stringToProperties(String s) {
		Properties properties = new Properties();
		if (isBlank(s)) {
			return properties;
		}
		try {
			properties.load(new StringReader(s));
		} catch (IOException e) {
			log.error(null, e);
		}

		return properties;
	}

	/**
	 * загрузка  строки  в Properties
	 *
	 * @param s .
	 * @return .
	 */
	public static HashMap<String, Object> stringToMap(String s) {
		HashMap<String, Object> map = new HashMap<>();
		Properties properties = new Properties();
		if (isBlank(s)) {
			return map;
		}
		try {
			properties.load(new StringReader(s));
			Set<Map.Entry<Object, Object>> entries = properties.entrySet();
			for (Map.Entry<Object, Object> entry : entries) {
				map.put((String) entry.getKey(), entry.getValue());
			}
		} catch (IOException e) {
			log.error(null, e);
		}

		return map;
	}
}
