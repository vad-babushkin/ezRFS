package com.iconiux.ezrfs.tray.ui;

import com.google.common.collect.Sets;

import java.util.Set;

public class ConstantHolderUICommon {
	public static final int COLUMN_FIX_WIDTH_50 = 50;
	public static final int COLUMN_FIX_WIDTH_75 = 100;
	public static final int COLUMN_FIX_WIDTH_100 = 150;
	public static final int COLUMN_SYMBOL_WIDTH = 32;

	public static final Set<String> SYMBOL_HEADERS = Sets.newHashSet(
		"!", // замечания
		"#", // редакция
		"+", // checkbox
		"√", // checkbox
		"✔", // checkbox
		"=", // отношение
		"$", // статус
		"*", // иконка
		"∙", // identicon
		"⌂"  // место
	);

	public static final Set<String> FIX_WIDTH_HEADERS_50 = Sets.newHashSet(
		"Тип",
		"uid",
		"puid",
		"luid",
		"Хоп"
//		"url",
//		"Размер",
//		"Время"
	);
	public static final Set<String> FIX_WIDTH_HEADERS_75 = Sets.newHashSet(
		"size"
	);
	public static final Set<String> FIX_WIDTH_HEADERS_100 = Sets.newHashSet(
		"⚡"  // мощность
	);
}
