//package com.iconiux.ezrfs.tray.ui;
//
//
//import com.jgoodies.looks.Options;
//import com.jgoodies.looks.plastic.PlasticLookAndFeel;
//import com.jgoodies.looks.plastic.theme.ExperienceGreen;
//import lombok.extern.slf4j.Slf4j;
//
//import javax.swing.*;
//import java.awt.*;
//import java.io.File;
//import java.util.Properties;
//
//@Slf4j
//public class JGoodiesLafUtils {
//	public final static String MEZGIR_SEARCH_GUI_PROPERTIES = "mezgir-search-gui.properties";
//
//	public static void setup() {
//
//		SwingUtilities.invokeLater(() -> {
////            ClearLookManager.setMode(ClearLookMode.DEBUG);
//
////            PlasticLookAndFeel.setPlasticTheme(new SkyBluer());
////            PlasticLookAndFeel.setPlasticTheme(new DesertBluer());
////              PlasticLookAndFeel.setPlasticTheme(new ExperienceRoyale());
//			PlasticLookAndFeel.setPlasticTheme(new ExperienceGreen());
////            PlasticLookAndFeel.setPlasticTheme(new SkyGreen());
//			PlasticLookAndFeel.setTabStyle(PlasticLookAndFeel.TAB_STYLE_METAL_VALUE);
//			PlasticLookAndFeel.setTabStyle("Metal");
//			PlasticLookAndFeel.setSelectTextOnKeyboardFocusGained(true);
//			PlasticLookAndFeel.set3DEnabled(true);
//			PlasticLookAndFeel.setHighContrastFocusColorsEnabled(true);
//			Properties properties = loadProgramProperties(null);
//			final int defaultFontSize = 20;
//			if (properties.containsKey("customFontPolicy") && properties.getProperty("customFontPolicy").equals("true")) {
//				if (properties.containsKey("customFontSize")) {
//					try {
//						final int finalFontSize = Integer.parseInt(String.valueOf(properties.get("customFontSize")));
//						PlasticLookAndFeel.setFontPolicy((lafName, table) -> new FontSet(finalFontSize));
//					} catch (Exception e) {
//						log.error("", e);
//					}
//				} else {
//					PlasticLookAndFeel.setFontPolicy((lafName, table) -> new FontSet(defaultFontSize));
//				}
//			} else {
//				PlasticLookAndFeel.setFontPolicy((lafName, table) -> new FontSet(defaultFontSize));
//			}
//
//			try {
//				UIManager.setLookAndFeel(new com.jgoodies.looks.plastic.Plastic3DLookAndFeel());
////			UIManager.setLookAndFeel(new com.jgoodies.looks.plastic.PlasticLookAndFeel());
////			UIManager.setLookAndFeel(new com.jgoodies.looks.plastic.PlasticXPLookAndFeel());
//				UIManager.put(Options.USE_SYSTEM_FONTS_APP_KEY, Boolean.TRUE);
////            Options.setsetGlobalFontSizeHints(FontSizeHints.MIXED);
//				Options.setDefaultIconSize(new Dimension(32, 32));
////        Options.setUseNarrowButtons(true);
////
////        // Global options
////        UIManager.put(Options.POPUP_DROP_SHADOW_ENABLED_KEY,true);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});
//	}
//
//	public static Properties loadProgramProperties(String file) {
//		Properties properties = new Properties();
//		properties.put("customFontPolicy", "true");
//		properties.put("customFontSize", "18");
//
////		FileInputStream sf = null;
////		try {
////			if (file == null) {
////				sf = new FileInputStream(MEZGIR_SEARCH_GUI_PROPERTIES);
////			} else {
////				sf = new FileInputStream(file);
////			}
////			properties.load(sf);
////		} catch (IOException e) {
////			log.error("",e);
////		}
//		return properties;
//	}
//
//	public static String getUserDataDirectory() {
//		return System.getProperty("user.home") + File.separator + ".config" + File.separator + "mezgir" + File.separator
//			+ getApplicationVersionString() + File.separator;
//	}
//
//	private static String getApplicationVersionString() {
//		return "1.0";       //fixme
//	}
//}
