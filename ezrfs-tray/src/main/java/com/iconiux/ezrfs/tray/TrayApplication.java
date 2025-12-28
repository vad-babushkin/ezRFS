package com.iconiux.ezrfs.tray;

import com.iconiux.ezrfs.tray.eventbus.TrayInfoMessageBean;
import com.iconiux.ezrfs.tray.service.ConnectionService;
import com.iconiux.ezrfs.tray.ui.FlatLafUtils;
import com.iconiux.ezrfs.tray.ui.UIHelper;
import com.iconiux.ezrfs.tray.ui.icon.UXTrayIcon;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.Configuration;
import org.bushe.swing.event.EventBus;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.prefs.Preferences;

import static com.iconiux.ezrfs.tray.ConstantHolder.PREFS_ROOT_PATH_KEY;
import static com.iconiux.ezrfs.tray.ConstantHolder.RSF_SERVER_HOST;
import static com.iconiux.ezrfs.tray.ui.UIHelper.ASPECT_RATIO_1_2;
import static com.iconiux.ezrfs.tray.ui.UIHelper.buildCompositeImageIcon;
import static com.iconiux.ezrfs.tray.util.IOHelper.loadApplicationConfiguration;


@Slf4j
@Getter
public class TrayApplication {
	private final Configuration applicationConfiguration = loadApplicationConfiguration();
	private Preferences preferences = Preferences.userNodeForPackage(TrayApplication.class);

	//	private static Preferences state;

	public static void main(String[] args) {

//		state = Preferences.userRoot().node(applicationConfiguration.getString(PREFS_ROOT_PATH_KEY));
		TrayApplication trayApplication = new TrayApplication();
		trayApplication.configure();
	}

	/**
	 *
	 */
	private void configure() {
//		JGoodiesLafUtils.setup();
		FlatLafUtils.setup();
//		try {
//			// Set System L&F
////			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
//		} catch (Exception e) {
//			log.error("", e);
//		}
		final ConnectionService connectionService = new ConnectionService(applicationConfiguration);
		buildMainFrame(applicationConfiguration, connectionService);

		connectionService.checkInternet("ya.ru", 443);
		connectionService.checkServer(connectionService.getHost(), 80);
		connectionService.checkRFS();
	}

	private void buildMainFrame(Configuration applicationConfiguration, ConnectionService connectionService) {
		SwingUtilities.invokeLater(() -> {
			AppMainForm mainForm = new AppMainForm(connectionService);
			mainForm.setApplicationConfiguration(applicationConfiguration);
//			connectionService.setMainForm(mainForm);

			mainForm.setTitle("ezRFS Tray Monitor");

			// Check if system tray is supported
			boolean trayEnable = false;
			if (!SystemTray.isSupported()) {
				log.error("System tray not supported");
				mainForm.setTrayEnabled(false);
				EventBus.publish(TrayInfoMessageBean.createError("System tray not supported"));
			} else {
				trayEnable = buildAndSetSysTray(mainForm);
				mainForm.setTrayEnabled(trayEnable);
			}
			mainForm.loadStorageItemsFromDisk();

			mainForm.pack();
			mainForm.setLocationRelativeTo(null);

			SwingUtilities.updateComponentTreeUI(mainForm);

			// выставляем размеры
//			UIHelper.centerFrameOnScreen(mainForm, GOLDEN_MEAN);
			UIHelper.centerFrameOnScreen(mainForm, ASPECT_RATIO_1_2);

			mainForm.toFront();
			mainForm.setVisible(preferences.getBoolean("showFormOnStartCheckBoxMenuItem", true));

//		BalloonTip balloonTip = new BalloonTip(null, "Hello world");
//// Now make the balloon tip disappear in 3000 milliseconds
//		TimingUtils.showTimedBalloon(balloonTip, 10000);
//		balloonTip.setVisible (true);
//			Arrays.stream(SystemTray.getSystemTray().getTrayIcons()).findFirst().get()
//			.displayMessage("", "ezRFS Tray Monitor", TrayIcon.MessageType.NONE);

//			Runtime.getRuntime().addShutdownHook(new Thread() {
//				@Override
//				public void run() {
////					guiSearchService.cleanSessionData();
//				}
//			});
		});
	}

	/**
	 * @param mainForm .
	 * @return .
	 */
	private boolean buildAndSetSysTray(AppMainForm mainForm) {
		// Create a popup menu for the tray icon
		String title = applicationConfiguration.getString("application.title", "Облачный Диск");
		JPopupMenu popup = new JPopupMenu();
//		popup.setFont(new Font(popup.getFont().getName(), Font.BOLD, 40));
		JMenuItem titleItem = new JMenuItem(title);
//		titleItem.setFont(new Font(titleItem.getFont().getName(), Font.BOLD, 40));
//		titleItem.setIcon(IconCache.fetch("/img/24x24/x-collection-shadow/window_application.png"));
		titleItem.setIcon(new ImageIcon(Objects.requireNonNull(TrayApplication.class.getResource("/img/24x24/x-collection-shadow/window_application.png"))));
		titleItem.setEnabled(false);
		popup.add(titleItem);
		popup.addSeparator();

		JCheckBoxMenuItem showFormOnStartCheckBoxMenuItem = new JCheckBoxMenuItem("Окно на старте");
		showFormOnStartCheckBoxMenuItem.setName("showFormOnStartCheckBoxMenuItem");
		showFormOnStartCheckBoxMenuItem.setSelected(preferences.getBoolean(showFormOnStartCheckBoxMenuItem.getName(), true));
		showFormOnStartCheckBoxMenuItem.addActionListener(e -> {
			preferences.putBoolean(showFormOnStartCheckBoxMenuItem.getName(), showFormOnStartCheckBoxMenuItem.isSelected());
		});
		popup.add(showFormOnStartCheckBoxMenuItem);

		popup.addSeparator();

		JMenuItem exitItem = new JMenuItem("Выход");
//		exitItem.setIcon(IconCache.fetch("/img/24x24/x-collection-shadow/launch_exit"));
		exitItem.setIcon(new ImageIcon(Objects.requireNonNull(TrayApplication.class.getResource("/img/24x24/x-collection-shadow/exit.png"))));
		exitItem.addActionListener(e -> {
//			mainForm.saveStorageItemsToDisk();
			mainForm.setVisible(false);
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
			System.exit(0);
		});
		popup.add(exitItem);

//		Image image = Toolkit.getDefaultToolkit().getImage(TrayApplication.class.getResource("/img/24x24/x-collection-shadow/cloud.png"));
//		Image image = Toolkit.getDefaultToolkit().getImage(TrayApplication.class.getResource("/img/24x24/x-collection-shadow/data.png"));
		Image image = buildCompositeImageIcon();
		UXTrayIcon uxTrayIcon = new UXTrayIcon(image, title, popup, mainForm);
		uxTrayIcon.setImageAutoSize(true);
		uxTrayIcon.setToolTip("ezRFS Tray Monitor");
		uxTrayIcon.setup();

		// Add the icon to the system tray
		try {
			SystemTray.getSystemTray().add(uxTrayIcon);
			return true;
		} catch (AWTException e) {
			log.error("TrayIcon could not be added.");
			return false;
		}
	}
}
