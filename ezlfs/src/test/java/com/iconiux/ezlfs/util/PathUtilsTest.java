package com.iconiux.ezlfs.util;

import org.assertj.core.api.Assertions;

class PathUtilsTest {
	String s1 = "12";
	String s2 = "123";
	String s3 = "1234";
	String s4 = "12345";
	String s5 = "123456";
	String s6 = "1234567";
	String s7 = "12345678";
	String s8 = "123456789";
	String s9 = "1234567890";

	@org.junit.jupiter.api.Test
	void hashToPathTest() {
		String path = PathUtils.hashToPath(s1);
		System.out.println(path);
		Assertions.assertThat(path).isNotBlank();

		path = PathUtils.hashToPath(s2);
		System.out.println(path);
		Assertions.assertThat(path).isNotBlank();

		path = PathUtils.hashToPath(s3);
		System.out.println(path);
		Assertions.assertThat(path).isNotBlank();

		path = PathUtils.hashToPath(s4);
		System.out.println(path);
		Assertions.assertThat(path).isNotBlank();

		path = PathUtils.hashToPath(s5);
		System.out.println(path);
		Assertions.assertThat(path).isNotBlank();

		path = PathUtils.hashToPath(s6);
		System.out.println(path);
		Assertions.assertThat(path).isNotBlank();

		path = PathUtils.hashToPath(s7);
		System.out.println(path);
		Assertions.assertThat(path).isNotBlank();

		path = PathUtils.hashToPath(s8);
		System.out.println(path);
		Assertions.assertThat(path).isNotBlank();

		path = PathUtils.hashToPath(s9);
		System.out.println(path);
		Assertions.assertThat(path).isNotBlank();
	}
}