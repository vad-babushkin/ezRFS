//package com.iconiux.ezrfs.tray.ui;
//
//
//import javax.swing.plaf.FontUIResource;
//import java.awt.*;
//
//public class FontSet implements com.jgoodies.looks.FontSet {
//
//	private FontUIResource controlFont;
//	private FontUIResource titleFont;
//	private FontUIResource systemFont;
//	private FontUIResource smallFont;
//
//	private int baseFontSize;
//
//	public static final String FONT_NAME = "Noto Sans";
////	public static final  String FONT_NAME = "Dialog";
//
//	public FontSet(int baseFontSize) {
//		this.baseFontSize = baseFontSize;
////		if (baseFontSize > 10 && baseFontSize < 17) {
////			this.baseFontSize = baseFontSize;
////		} else {
////			this.baseFontSize = 32;
////		}
//	}
//
//	public FontUIResource getControlFont() {
//		if (controlFont == null) {
//			controlFont = new FontUIResource(new Font(FONT_NAME, Font.PLAIN, baseFontSize));
//		}
//		return controlFont;
//	}
//
//	public FontUIResource getMenuFont() {
//		return getControlFont();
//	}
//
//	public FontUIResource getTitleFont() {
//		if (titleFont == null) {
//			titleFont = new FontUIResource(getControlFont().deriveFont(Font.BOLD));
//		}
//		return titleFont;
//	}
//
//	public FontUIResource getSmallFont() {
//		if (smallFont == null) {
//			smallFont = new FontUIResource(new Font(FONT_NAME, Font.PLAIN, baseFontSize - 3));
//		}
//		return smallFont;
//	}
//
//	public FontUIResource getMessageFont() {
//		if (systemFont == null) {
//			systemFont = new FontUIResource(new Font(FONT_NAME, Font.PLAIN, baseFontSize));
//		}
//		return systemFont;
//	}
//
//	public FontUIResource getWindowTitleFont() {
//		return getTitleFont();
//	}
//
//	public int getBaseFontSize() {
//		return baseFontSize;
//	}
//
//	public void setBaseFontSize(int baseFontSize) {
//		this.baseFontSize = baseFontSize;
//	}
//}
