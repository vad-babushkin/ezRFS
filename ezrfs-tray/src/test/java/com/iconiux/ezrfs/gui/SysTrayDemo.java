package com.iconiux.ezrfs.gui;

import com.iconiux.ezrfs.common.ui.icon.*;
import com.iconiux.ezrfs.tray.ui.FlatLafUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SysTrayDemo {
	public static void main(String[] args) throws Exception {
		// Ensure the code runs on the Swing event dispatch thread
		SwingUtilities.invokeLater(() -> {
			createAndShowGUI();
		});
	}

	private static void createAndShowGUI() {
		// Check if system tray is supported
		if (!SystemTray.isSupported()) {
			System.out.println("System tray not supported");
			return;
		}

		FlatLafUtils.setup();
//		JGoodiesLafUtils.setup();


		final JFrame frame = new JFrame("System Tray Demo");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Hide window, don't exit app
		frame.setSize(300, 200);

		JButton hideButton = new JButton("Click to hide in tray");
		hideButton.addActionListener(e -> frame.setVisible(false));
		frame.add(hideButton, BorderLayout.CENTER);
		frame.setVisible(true);

		// --- System Tray Logic ---

		// Load an image for the tray icon (replace with a valid image path)
		// Ensure image is in the classpath or use an absolute path
//		Image image = Toolkit.getDefaultToolkit().getImage("path/to/your/icon.png");
//		ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(SysTrayDemo.class.getResource("/img/24x24/x-collection-shadow/data.png")));
		Image image = Toolkit.getDefaultToolkit().getImage(SysTrayDemo.class.getResource("/img/24x24/x-collection-shadow/data.png"));

		// Create a listener to show the window when the icon is clicked
		ActionListener showListener = e -> {
			frame.setVisible(true);
			frame.setState(JFrame.NORMAL); // Restore from minimized state if applicable
		};

		// Create a popup menu for the tray icon
		String title = "Облачный Диск";
		JPopupMenu popup = new JPopupMenu();
//		popup.setFont(new Font(popup.getFont().getName(), Font.BOLD, 40));
		JMenuItem titleItem = new JMenuItem(title);
//		titleItem.setFont(new Font(titleItem.getFont().getName(), Font.BOLD, 40));
		titleItem.setEnabled(false);
		popup.add(title);
		popup.addSeparator();

		JMenuItem showItem = new JMenuItem("Show Window");
		showItem.addActionListener(showListener);
		popup.add(showItem);

		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(e -> System.exit(0)); // Terminate the application
		popup.add(exitItem);

		// Create the TrayIcon with the image, tooltip, and popup menu
//		TrayIcon trayIcon = new TrayIcon(image, "Java System Tray Demo", popup);
//		trayIcon.addActionListener(showListener); // Default action (double click)
//		UXTrayIcon uxTrayIcon = new UXTrayIcon(image, "тест", popup, frame);
//		uxTrayIcon.setup();
//		JXTrayIcon jxTrayIcon = new JXTrayIcon(image);
//		jxTrayIcon.setJPopuMenu(popup);
		TrayIcon3 jxTrayIcon = new TrayIcon3(image);
		jxTrayIcon.setJPopupMenu(popup);
		jxTrayIcon.setup();
//		jxTrayIcon.init();
//		createGui();
		// Add the icon to the system tray
		try {
			SystemTray.getSystemTray().add(jxTrayIcon);
		} catch (AWTException e) {
			System.err.println("TrayIcon could not be added.");
		}
	}
}
