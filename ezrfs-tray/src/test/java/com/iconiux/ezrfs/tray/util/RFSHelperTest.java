package com.iconiux.ezrfs.tray.util;

import junit.framework.TestCase;

import java.net.MalformedURLException;

import static com.iconiux.ezrfs.tray.util.RFSHelper.*;

public class RFSHelperTest extends TestCase {

	public void testView2FileUrl() {
		String s1 = "http://example.com/api/v1/view/f1jrbdd1seclxsla5s3scetu3660zoms4zs7";
		String s2 = "http://example.com/api/v1/download/file/f1jrbdd1seclxsla5s3scetu3660zoms4zs7";
		assertEquals(s2, view2FileUrl(s1));
	}

	public void testFile2ViewUrl() {
		String s1 = "http://example.com/api/v1/download/file/f1jrbdd1seclxsla5s3scetu3660zoms4zs7";
		String s2 = "http://example.com/api/v1/view/f1jrbdd1seclxsla5s3scetu3660zoms4zs7";
		assertEquals(s2, file2ViewUrl(s1));
	}

	public void testFile2MetaUrl() {
		String s1 = "http://example.com/api/v1/download/file/f1jrbdd1seclxsla5s3scetu3660zoms4zs7";
		String s2 = "http://example.com/api/v1/download/meta/f1jrbdd1seclxsla5s3scetu3660zoms4zs7";
		assertEquals(s2, file2MetaUrl(s1));
	}

	public void testCutCuidFromUrl() throws MalformedURLException {
		String s1 = "http://example.com/api/v1/download/file/f1jrbdd1seclxsla5s3scetu3660zoms4zs7";
		String s2 = "f1jrbdd1seclxsla5s3scetu3660zoms4zs7";
		assertEquals(s2, cutCuidFromUrl(s1));
	}
}
