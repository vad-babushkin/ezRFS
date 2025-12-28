package com.iconiux.ezrfs.tray.ui;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatInspector;
import com.formdev.flatlaf.extras.FlatUIDefaultsInspector;
import com.formdev.flatlaf.util.SystemInfo;

import javax.swing.*;

public class FlatLafUtils {
	public static void setup() {

		// macOS
		if (SystemInfo.isMacOS) {
			// enable screen menu bar
			// (moves menu bar from JFrame window to top of screen)
			System.setProperty("apple.laf.useScreenMenuBar", "true");

			// application name used in screen menu bar
			// (in first menu after the "apple" menu)
			System.setProperty("apple.awt.application.name", "Искатель");

			// appearance of window title bars
			// possible values:
			//   - "system": use current macOS appearance (light or dark)
			//   - "NSAppearanceNameAqua": use light appearance
			//   - "NSAppearanceNameDarkAqua": use dark appearance
			System.setProperty("apple.awt.application.appearance", "system");
		}

//		if( SystemInfo.isLinux ) {
//			// enable custom window decorations
//			JFrame.setDefaultLookAndFeelDecorated( true );
//			JDialog.setDefaultLookAndFeelDecorated( true );
//		}

		if (!SystemInfo.isJava_9_orLater && System.getProperty("flatlaf.uiScale") == null) {
			System.setProperty("flatlaf.uiScale", "1.15x");
		}

		SwingUtilities.invokeLater(() -> {
			// application specific UI defaults
			FlatLaf.registerCustomDefaultsSource("com.iconiux.ezrfs.gui");

			// set look and feel
			FlatLightLaf.setup();
//			FlatIntelliJLaf.setup();
//			FlatGrayIJTheme.setup();
//			FlatSolarizedLightIJTheme.setup();
//			FlatAtomOneLightContrastIJTheme.setup();
//			FlatSolarizedLightContrastIJTheme.setup();

//			// install inspectors
			FlatInspector.install("ctrl shift alt X");
			FlatUIDefaultsInspector.install("ctrl shift alt Y");
		});
	}
}
