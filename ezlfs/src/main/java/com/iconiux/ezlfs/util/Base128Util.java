package com.iconiux.ezlfs.util;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class Base128Util {

	/**
	 * All possible digits for representing a number as a String
	 * This is conservative and does not include 'special'
	 * characters since some browsers don't handle them right.
	 * The IE for instance seems to be case insensitive in class
	 * names for CSSs. Grrr.
	 */
	private final static char[] DIGITS = {
		'0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9', 'a', 'b',
		'c', 'd', 'e', 'f', 'g', 'h',
		'i', 'j', 'k', 'l', 'm', 'n',
		'o', 'p', 'q', 'r', 's', 't',
		'u', 'v', 'w', 'x', 'y', 'z',
		'A', 'B',
		'C', 'D', 'E', 'F', 'G', 'H',
		'I', 'J', 'K', 'L', 'M', 'N',
		'O', 'P', 'Q', 'R', 'S', 'T',
		'U', 'V', 'W', 'X', 'Y', 'Z',
		'а', 'б', 'в', 'г', 'д', 'е',
		'ё', 'ж', 'з', 'и', 'й', 'к',
		'л', 'м', 'н', 'о', 'п', 'р',
		'с', 'т', 'у', 'ф', 'х', 'ц',
		'ч', 'ш', 'щ', 'ь', 'ы', 'ъ',
		'э', 'ю', 'я',
		'А', 'Б', 'В', 'Г', 'Д', 'Е',
		'Ё', 'Ж', 'З', 'И', 'Й', 'К',
		'Л', 'М', 'Н', 'О', 'П', 'Р',
		'С', 'Т', 'У', 'Ф', 'Х', 'Ц',
		'Ч', 'Ш', 'Щ', 'Ь', 'Ы', 'Ъ',
		'Э', 'Ю', 'Я'
	};

	public static final int MAX_RADIX = DIGITS.length;  // 128

	/**
	 * Codes number up to radix 62.
	 * Note, this method is only public for backward compatiblity. don't
	 * use it.
	 *
	 * @param minDigits returns a string with a least minDigits digits
	 */
	public static String toString(long i, int radix, int minDigits) {
		char[] buf = new char[65];

		radix = Math.min(Math.abs(radix), MAX_RADIX);
		minDigits = Math.min(buf.length - 1, Math.abs(minDigits));


		int charPos = buf.length - 1;

		boolean negative = (i < 0);
		if (negative) {
			i = -i;
		}

		while (i >= radix) {
			buf[charPos--] = DIGITS[(int) (i % radix)];
			i /= radix;
		}
		buf[charPos] = DIGITS[(int) i];

		// if minimum length of the result string is set, pad it with the
		// zero-representation (that is: '0')
		while (charPos > buf.length - minDigits)
			buf[--charPos] = DIGITS[0];

		if (negative) {
			buf[--charPos] = '-';
		}

		return new String(buf, charPos, buf.length - charPos);
	}

	public static String genUID128() {
		return toYUID128(UUID.randomUUID());
	}

	public static String toUID128(Long l) {
		return Long.toString(l, 128);
	}

	public static String toUID128(Integer l) {
		return Long.toString(l, 128);
	}

	public static String toYUID128(UUID uuid) {
		long m1 = uuid.getLeastSignificantBits();
		long m2 = uuid.getMostSignificantBits();
		return toString(m1, 128, 0) + toString(m2, 128, 0);
	}

	public static String genUID62() {
		return toYUID62(UUID.randomUUID());
	}

	public static String toUID62(Long l) {
		return Long.toString(l, 62);
	}

	public static String toUID62(Integer l) {
		return Long.toString(l, 62);
	}

	public static String toYUID62(UUID uuid) {
		long m1 = uuid.getLeastSignificantBits();
		long m2 = uuid.getMostSignificantBits();
		return toString(m1, 62, 0) + toString(m2, 62, 0);
	}

	public static String genUID36() {
		return toYUID36(UUID.randomUUID());
	}

	public static String toUID36(Long l) {
		return Long.toString(l, 36);
	}

	public static String toUID36(Integer l) {
		return Long.toString(l, 36);
	}

	public static String toYUID36(UUID uuid) {
		long m1 = uuid.getLeastSignificantBits();
		long m2 = uuid.getMostSignificantBits();
		return toString(m1, 36, 0) + toString(m2, 36, 0);
	}

	public static String genUID() {
		return genUID36();
	}

	public static void main(String[] args) {
		System.out.println(genUID128());
		System.out.println(genUID62());
		System.out.println(genUID36());
		System.out.println(genUID());
	}
}
