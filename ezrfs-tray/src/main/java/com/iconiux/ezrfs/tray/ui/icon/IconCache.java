package com.iconiux.ezrfs.tray.ui.icon;

import com.jgoodies.uif.util.NullIcon;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Slf4j
public class IconCache {
	// пустая иконка
	public static final Icon NULL_ICON_8 = new NullIcon(8, 8);
	public static final Icon NULL_ICON_16 = new NullIcon(16, 16);
	public static final Icon NULL_ICON_24 = new NullIcon(24, 24);
	public static final Icon NULL_ICON_32 = new NullIcon(32, 32);

	private static final IconCache INSTANCE = new IconCache();

	Map<String, Icon> map = new Hashtable<String, Icon>();

	public static IconCache getInstance() {
		return INSTANCE;
	}

	private IconCache() {
	}

	public int getSize() {
		return map.size();
	}

	public Icon getIcon(String url) {
		if (isBlank(url)) {
			return NULL_ICON_24;
		}

		Icon icon = map.get(url);
		try {
			if (icon == null) {
				URL url1 = IconCache.class.getResource(url);
				if (url1 != null) {
					icon = new ImageIcon(url1);
				} else {
					log.error("Ошибка загрузки изображения 2" + url);
					icon = NULL_ICON_24;
				}
				map.put(url, icon);
			}
		} catch (Exception e) {
			icon = NULL_ICON_24;
			map.put(url, icon);
			log.error("Ошибка загрузки изображения " + url);
		}
		return icon;
	}

	public static Icon fetch(String url) {
		return fetch(url, 24);
	}

	public static Icon fetch(String url, int size) {
		if (isBlank(url)) {
			return getNullIcon(size);
		}

		if (url.indexOf(",") > 0) {
			// если несколько путей через ','
			List<String> list = Arrays.asList(url.split(","));
			CompositeIcon compositeIcon = new CompositeIcon(fetch(list.get(0)),
				fetch(list.get(1)),
				SwingConstants.CENTER,
				SwingConstants.RIGHT,
				SwingConstants.BOTTOM);
//            if(list.size() == 2){
//                return compositeIcon;
//            }
			for (int i = 2; i < list.size(); i++) {
				compositeIcon = new CompositeIcon(compositeIcon,
					fetch(list.get(i)),
					SwingConstants.CENTER,
					SwingConstants.RIGHT,
					SwingConstants.BOTTOM);
			}
			return compositeIcon;
		} else {
			return IconCache.getInstance().getIcon(url);
		}
	}

	public static Icon fetch(List<String> urls) {
		return fetch(urls, 24);
	}

	public static Icon fetch(List<String> urls, int size) {
		if (urls.isEmpty()) {
			return getNullIcon(size);
		}
		Icon compositeIcon = (isNotBlank(urls.get(0))) ? fetch(urls.get(0)) : getNullIcon(size);
		for (int i = 1; i < urls.size(); i++) {
			if (isNotBlank(urls.get(i))) {
				compositeIcon = new CompositeIcon(compositeIcon,
					fetch(urls.get(i)),
					SwingConstants.CENTER,
					SwingConstants.RIGHT,
					SwingConstants.BOTTOM);
			}
		}
		return compositeIcon;
	}

	public static Icon fetch(String urlLeft, String urlRight) {
		return fetch(urlLeft, urlRight, 24);
	}

	public static Icon fetch(String urlLeft, String urlRight, int size) {
		if (isBlank(urlLeft) && isBlank(urlRight)) {
			return getNullIcon(size);
		}
		Icon compositeIcon = (isNotBlank(urlLeft) ? fetch(urlLeft) : getNullIcon(size));
		if (isNotBlank(urlRight)) {
			compositeIcon = new CompositeIcon(compositeIcon,
				fetch(urlRight),
				SwingConstants.CENTER,
				SwingConstants.RIGHT,
				SwingConstants.BOTTOM);
		}
		return compositeIcon;
	}

	public static Icon getNullIcon(int size) {
		switch (size) {
			case 8:
				return NULL_ICON_8;
			case 16:
				return NULL_ICON_16;
			case 24:
				return NULL_ICON_24;
			case 32:
				return NULL_ICON_32;
			default:
				return NULL_ICON_16;
		}
	}
}
