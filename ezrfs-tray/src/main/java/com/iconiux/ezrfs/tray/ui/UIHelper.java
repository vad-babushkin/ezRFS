package com.iconiux.ezrfs.tray.ui;

import com.iconiux.ezrfs.tray.ui.icon.CompositeIcon;
import com.iconiux.ezrfs.tray.ui.icon.CompoundIcon;
import com.iconiux.ezrfs.tray.ui.icon.IconCache;
//import com.jgoodies.looks.BorderStyle;
//import com.jgoodies.looks.HeaderStyle;
//import com.jgoodies.looks.Options;
//import com.jgoodies.looks.plastic.PlasticLookAndFeel;
//import com.jgoodies.looks.windows.WindowsLookAndFeel;
import com.jgoodies.uif.component.UIFCheckBoxMenuItem;
import com.jgoodies.uif.util.NullIcon;
import com.jidesoft.swing.JideSplitButton;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.swing.SwingUtilities.invokeLater;

@Slf4j
public final class UIHelper {

	// пустая иконка
	public static final Icon NULL_ICON_8 = new NullIcon(8, 8);
	public static final Icon NULL_ICON_16 = new NullIcon(16, 16);
	public static final Icon NULL_ICON_24 = new NullIcon(24, 24);
	public static final Icon NULL_ICON_32 = new NullIcon(32, 32);

	// иконки цифр
	public static final Icon DIGIT_ICON_0_16 = IconCache.fetch("/img/16x16/x-collection-shadow/square_black_0.png");
	public static final Icon DIGIT_ICON_1_16 = IconCache.fetch("/img/16x16/x-collection-shadow/square_red_1.png");
	public static final Icon DIGIT_ICON_2_16 = IconCache.fetch("/img/16x16/x-collection-shadow/square_orange_2.png");
	public static final Icon DIGIT_ICON_3_16 = IconCache.fetch("/img/16x16/x-collection-shadow/square_yellow_3.png");
	public static final Icon DIGIT_ICON_4_16 = IconCache.fetch("/img/16x16/x-collection-shadow/square_green_4.png");
	public static final Icon DIGIT_ICON_5_16 = IconCache.fetch("/img/16x16/x-collection-shadow/square_blue_5.png");
	public static final Icon DIGIT_ICON_6_16 = IconCache.fetch("/img/16x16/x-collection-shadow/square_deep_blue_6.png");
	public static final Icon DIGIT_ICON_7_16 = IconCache.fetch("/img/16x16/x-collection-shadow/square_violet_7.png");
	public static final Icon DIGIT_ICON_8_16 = IconCache.fetch("/img/16x16/x-collection-shadow/square_gray_8.png");
	public static final Icon DIGIT_ICON_9_16 = IconCache.fetch("/img/16x16/x-collection-shadow/square_white_9.png");

	public static final Icon DIGIT_ICON_0_24 = IconCache.fetch("/img/24x24/x-collection-shadow/square_black_0.png");
	public static final Icon DIGIT_ICON_1_24 = IconCache.fetch("/img/24x24/x-collection-shadow/square_red_1.png");
	public static final Icon DIGIT_ICON_2_24 = IconCache.fetch("/img/24x24/x-collection-shadow/square_orange_2.png");
	public static final Icon DIGIT_ICON_3_24 = IconCache.fetch("/img/24x24/x-collection-shadow/square_yellow_3.png");
	public static final Icon DIGIT_ICON_4_24 = IconCache.fetch("/img/24x24/x-collection-shadow/square_green_4.png");
	public static final Icon DIGIT_ICON_5_24 = IconCache.fetch("/img/24x24/x-collection-shadow/square_blue_5.png");
	public static final Icon DIGIT_ICON_6_24 = IconCache.fetch("/img/24x24/x-collection-shadow/square_deep_blue_6.png");
	public static final Icon DIGIT_ICON_7_24 = IconCache.fetch("/img/24x24/x-collection-shadow/square_violet_7.png");
	public static final Icon DIGIT_ICON_8_24 = IconCache.fetch("/img/24x24/x-collection-shadow/square_gray_8.png");
	public static final Icon DIGIT_ICON_9_24 = IconCache.fetch("/img/24x24/x-collection-shadow/square_white_9.png");

	// иконки уровней мощности
	public static final Icon CARDINALITY_0_ICON = NULL_ICON_16;
	public static final Icon CARDINALITY_10_ICON = IconCache.fetch("/img/16x16/x-collection-shadow/flower_white.png");
	public static final Icon CARDINALITY_100_ICON = IconCache.fetch("/img/16x16/x-collection-shadow/flower_blue.png");
	public static final Icon CARDINALITY_1000_ICON = IconCache.fetch("/img/16x16/x-collection-shadow/flower_yellow.png");
	public static final Icon CARDINALITY_10000_ICON = IconCache.fetch("/img/16x16/x-collection-shadow/flower_red.png");
	public static final Icon CARDINALITY_OVER_10K_ICON = IconCache.fetch("/img/16x16/x-collection-shadow/stop.png");

	/*
	Иконки статус бара
	 */
	public static final Icon STATE_OFF_ICON = IconCache.fetch("/img/16x16/x-collection-shadow/bullet_ball_grey.png");
	// предупреждение
	public static final Icon STATE_WARNING_ICON = IconCache.fetch("/img/24x24/x-collection-shadow/bullet_ball_yellow.png");
	// Ошибка
	public static final Icon STATE_ERROR_ICON = IconCache.fetch("/img/24x24/x-collection-shadow/bullet_ball_red.png");
	// OK
	public static final Icon STATE_OK_ICON = IconCache.fetch("/img/24x24/x-collection-shadow/bullet_ball_green.png");
	// В процессе
	public static final Icon STATE_PROCESS_ICON = IconCache.fetch("/img/24x24/mezgir-shadow/bullet_ball_blue_breath-2.gif");
	public static final Icon STATE_PROCESS_ICON2 = IconCache.fetch("/img/24x24/x-collection-shadow/bullet_ball_blue.png");
	public static final Icon STATE_PROCESS_ICON3 = IconCache.fetch("/img/24x24/mezgir-shadow/bullet_ball_blue_fade.gif");

	public static final Icon APPLICATION_VERSION_ICON = IconCache.fetch("/img/16x16/x-collection-shadow/window_application.png");
	public static final Icon QUIT_ICON = IconCache.fetch("/img/16x16/x-collection-shadow/exit.png");


	public static final String HTML_BOLD_OPEN = "<b>";
	public static final String HTML_BOLD_CLOSE = "</b>";
	public static final String HTML_BODY_BEFORE = "<html>\n" +
		"  <head>\n" +
		"    <style type=\"text/css\">\n" +
		"      <!--\n" +
		"        body { margin-bottom: 0; font-size: 12pt; font-family: Tahoma; margin-left: 0; margin-right: 0; margin-top: 0 }\n" +
		"        a { margin-bottom: 0; font-family: Tahoma; font-size: 11pt; margin-left: 0; margin-right: 0; margin-top: 0 }\n" +
		"        p { margin-bottom: 0; font-family: Tahoma; font-size: 12pt; margin-left: 0; margin-right: 0; margin-top: 0 }\n" +
		"        li { margin-bottom: 0; font-family: Tahoma; font-size: 11pt; margin-left: 0; margin-right: 0; margin-top: 0 }\n" +
		"      -->\n" +
		"    </style>\n" +
		"  </head>\n" +
		"  <body>";
	public static String HTML_BODY_AFTER = "  </body>\n" +
		"</html>";

	public static final double ASPECT_MAX = 1.0 / 1.0;
	public static final double ASPECT_RATIO_1_2 = 1.0 / 2;
	public static final double ASPECT_RATIO_1_3 = 1.0 / 3;
	public static final double ASPECT_RATIO_1_4 = 1.0 / 4;
	public static final double ASPECT_RATIO_2_3 = 2.0 / 3;
	public static final double ASPECT_RATIO_3_4 = 3.0 / 4;
	public static final double GOLDEN_MEAN = 0.618d;
	public static final Double DEFAULT_DISPLAY_ASPECT = 2768.0 / 1440.0;

	public static final int SIZE_16 = 0;
	public static final int SIZE_24 = 1;

	// константы состояний
	final static public int STATE_OFF = 0;
	final static public int STATE_WARNING = 1;
	final static public int STATE_ERROR = 2;
	final static public int STATE_OK = 3;
	final static public int STATE_PROCESS = 4;
	final static public int STATE_INFO = 5;

	// универсальный заголовок для начала HTML текста
	public final static String HTML_TOP = "<html><p align=\"center\"><font face=\"Tahoma\" size=\"3\">";

	/**
	 * @param text .
	 * @return .
	 */
	public static JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setFocusPainted(true);
		button.setBorderPainted(true);
		button.setContentAreaFilled(true);
		return button;
	}

	public static void centerOnScreen(Component component) {
		Dimension paneSize = component.getSize();
		Dimension screenSize = component.getToolkit().getScreenSize();
		component.setLocation(
			(screenSize.width - paneSize.width) / 2,
			(screenSize.height - paneSize.height) / 2);
	}

	public static void center600x600OnScreen(Component component) {
		Dimension screenSize = component.getToolkit().getScreenSize();
		component.setBounds(
			(screenSize.width - 600) / 2,
			(screenSize.height - 600) / 2, 600, 600);
	}

	public static void center800x600OnScreen(Component component) {
		Dimension screenSize = component.getToolkit().getScreenSize();
		component.setBounds(
			(screenSize.width - 800) / 2,
			(screenSize.height - 600) / 2, 800, 600);
	}

	/**
	 * Центрируем окно, и выставляем размеры
	 *
	 * @param dialog            .
	 * @param widthAspectRatio  .
	 * @param heightAspectRatio .
	 */
	public static void centerDialogOnScreen(JDialog dialog, double widthAspectRatio, double heightAspectRatio) {
		// выставляем размеры
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setSize((int) (dim.getWidth() * widthAspectRatio), (int) (dim.getHeight() * heightAspectRatio));
		dialog.setLocationRelativeTo(null);
	}

	public static void centerDialogOnScreen(JDialog dialog, double aspectRatio) {
		centerDialogOnScreen(dialog, aspectRatio, aspectRatio);
	}

	/**
	 * Центрируем окно, и выставляем размеры
	 *
	 * @param frame             .
	 * @param widthAspectRatio  .
	 * @param heightAspectRatio .
	 */
	public static void centerFrameOnScreen(JFrame frame, double widthAspectRatio, double heightAspectRatio) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		GraphicsDevice[] devices = ge.getScreenDevices();
//		for (GraphicsDevice device : devices) {
//			DisplayMode mode = device.getDisplayMode();
//			int screenWidth = mode.getWidth();
//			int screenHeight = mode.getHeight();
//			System.out.println("Screen Resolution: " + screenWidth + "x" + screenHeight);
//		}
		// выставляем размеры
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		log.info("{} {}", (int) (dim.getWidth() * widthAspectRatio), (int) (dim.getHeight() * heightAspectRatio));
		int realHeight = (int) (dim.getHeight() * heightAspectRatio);
		int realWidth = (int) (dim.getHeight() * DEFAULT_DISPLAY_ASPECT * heightAspectRatio);
//		frame.setSize((int) (dim.getWidth() * widthAspectRatio), (int) (dim.getHeight() * heightAspectRatio));
		frame.setSize(realWidth, realHeight);
		log.info("{} {}", realWidth, realHeight);
		frame.setLocationRelativeTo(null);
//		if (Orion.checkInstance() != null && Boolean.valueOf(Orion.getInstance().getProgramProperies().getProperty("maximizedFrameOnStart", "false"))) {
//			frame.setExtendedState(JXFrame.MAXIMIZED_BOTH);
//		}
	}

	public static void centerFrameOnScreen(JFrame frame, double aspectRatio) {
		centerFrameOnScreen(frame, aspectRatio, aspectRatio);
	}

	/**
	 * Центрируем окно, и выставляем размеры
	 *
	 * @param frame .
	 */
	public static void centerFix600PxFrameOnScreen(JFrame frame, double width, double height) {
		// выставляем размеры
		frame.setSize((int) (width), (int) (height));
		frame.setLocationRelativeTo(null);
	}

	/**
	 * Центрируем окно, и выставляем размеры
	 *
	 * @param frame             .
	 * @param widthAspectRatio  .
	 * @param heightAspectRatio .
	 */
	public static void putFrameOnScreen(JFrame frame, int align, double widthAspectRatio, double heightAspectRatio,
	                                    boolean chekMaximize) {
		// выставляем размеры
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		switch (align) {
			case SwingConstants.LEFT:
				frame.setBounds(0,
					(int) (dim.getHeight() * (1 - heightAspectRatio) / 2),
					(int) (dim.getWidth() * widthAspectRatio),
					(int) (dim.getHeight() * heightAspectRatio)
				);
				break;
			case SwingConstants.RIGHT:
				frame.setBounds((int) (dim.getWidth() * (1 - widthAspectRatio)),
					(int) (dim.getHeight() * (1 - heightAspectRatio) / 2),
					(int) (dim.getWidth() * widthAspectRatio),
					(int) (dim.getHeight() * heightAspectRatio)
				);
				break;
			default:
				centerFrameOnScreen(frame, widthAspectRatio, heightAspectRatio);
		}
//        frame.setLocationRelativeTo(null);
//		if (chekMaximize && Orion.checkInstance() != null && Boolean.valueOf(Orion.getInstance().getProgramProperies().getProperty("maximizedFrameOnStart", "false"))) {
//			frame.setExtendedState(JXFrame.MAXIMIZED_BOTH);
//		}
	}

	public static void putFrameOnScreen(JFrame frame, int align, double widthAspectRatio, double heightAspectRatio) {
		putFrameOnScreen(frame, align, widthAspectRatio, heightAspectRatio, true);

	}

	public static void centerComponentOnScreen(JComponent component, double widthAspectRatio,
	                                           double heightAspectRatio) {
		// выставляем размеры
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension newDim = new Dimension((int) (dim.getWidth() * widthAspectRatio), (int) (dim.getHeight() * heightAspectRatio));
		component.setSize(newDim);
		component.setPreferredSize(newDim);
	}

	public static void centerComponentOnScreen(JComponent component, double aspectRatio) {
		centerComponentOnScreen(component, aspectRatio, aspectRatio);
	}

	public static Rectangle getOptimalSize(double aspectRatio) {
		// выставляем размеры
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) (dim.getHeight() * aspectRatio);
		int width = (int) (dim.getWidth() * aspectRatio);
		return new Rectangle((dim.width - width) / 2, (dim.height - height) / 2, width, height);
	}

	public static int getOptimalWidth(double aspectRatio) {
		// выставляем размеры
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		return (int) (dim.getWidth() * aspectRatio);
	}

	public static int getOptimalHeight(double aspectRatio) {
		// выставляем размеры
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		return (int) (dim.getHeight() * aspectRatio);
	}

	public static int getOptimalWidth(JFrame frame, double aspectRatio) {
		// выставляем размеры
		Dimension dim = frame.getSize();
		return (int) (dim.getWidth() * aspectRatio);
	}

	public static int getOptimalHeight(JFrame frame, double aspectRatio) {
		// выставляем размеры
		Dimension dim = frame.getSize();
		return (int) (dim.getHeight() * aspectRatio);
	}

	public static ImageIcon readImageIcon(String fileName) {
		Image image = readImage(fileName);
		if (image == null)
			return null;

		return new ImageIcon(image);
	}

	public static Image readImage(String fileName) {
		URL url = UIHelper.class.getResource("images/" + fileName);
		if (url == null)
			return null;

		return java.awt.Toolkit.getDefaultToolkit().getImage(url);
	}

//	public static void tune3D(JPanel panel) {
//		panel.putClientProperty(Options.HEADER_STYLE_KEY, HeaderStyle.BOTH);
//		panel.putClientProperty(PlasticLookAndFeel.IS_3D_KEY, Boolean.TRUE);
//		panel.putClientProperty(WindowsLookAndFeel.BORDER_STYLE_KEY, BorderStyle.SEPARATOR);
//		panel.putClientProperty(PlasticLookAndFeel.BORDER_STYLE_KEY, BorderStyle.SEPARATOR);
//	}
//
//	public static void tune3D(JToolBar toolBar) {
//		toolBar.putClientProperty(Options.HEADER_STYLE_KEY, HeaderStyle.BOTH);
//		toolBar.putClientProperty(PlasticLookAndFeel.IS_3D_KEY, Boolean.TRUE);
//		toolBar.putClientProperty(WindowsLookAndFeel.BORDER_STYLE_KEY, BorderStyle.SEPARATOR);
//		toolBar.putClientProperty(PlasticLookAndFeel.BORDER_STYLE_KEY, BorderStyle.SEPARATOR);
//	}
//
//	public static void tune3D(JMenuBar menuBar) {
//		menuBar.setBorderPainted(false);
//		menuBar.putClientProperty(Options.HEADER_STYLE_KEY, HeaderStyle.BOTH);
//		menuBar.putClientProperty(PlasticLookAndFeel.IS_3D_KEY, Boolean.TRUE);
//		menuBar.putClientProperty(WindowsLookAndFeel.BORDER_STYLE_KEY, BorderStyle.SEPARATOR);
//		menuBar.putClientProperty(PlasticLookAndFeel.BORDER_STYLE_KEY, BorderStyle.SEPARATOR);
//	}

	public static void run(Component component) {
		final JFrame jFrame = new JFrame();
		jFrame.getContentPane().setLayout(new BorderLayout());
		jFrame.getContentPane().add(component);
		jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		invokeLater(() -> {
			jFrame.pack();
			jFrame.setVisible(true);
		});
	}

//	static Image iconToImage(Icon icon) {
//		if (icon instanceof ImageIcon) {
//			return ((ImageIcon)icon).getImage();
//		} else {
//			int w = icon.getIconWidth();
//			int h = icon.getIconHeight();
//			GraphicsEnvironment ge =
//				GraphicsEnvironment.getLocalGraphicsEnvironment();
//			GraphicsDevice gd = ge.getDefaultScreenDevice();
//			GraphicsConfiguration gc = gd.getDefaultConfiguration();
//			BufferedImage image = gc.createCompatibleImage(w, h);
//			Graphics2D g = image.createGraphics();
//			icon.paintIcon(null, g, 0, 0);
//			g.dispose();
//
//			return image;
//		}
//	}

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

	public static Icon buildCountIcon(Integer count) {
		if (count == null) {
			return NULL_ICON_16;
		}
		String sCount = String.valueOf(count);
		Icon[] icons = new Icon[sCount.length()];
		int pos = 0;
		for (String s : sCount.split("")) {
			switch (s) {
				case "0":
					icons[pos] = DIGIT_ICON_0_16;
					break;
				case "1":
					icons[pos] = DIGIT_ICON_1_16;
					break;
				case "2":
					icons[pos] = DIGIT_ICON_2_16;
					break;
				case "3":
					icons[pos] = DIGIT_ICON_3_16;
					break;
				case "4":
					icons[pos] = DIGIT_ICON_4_16;
					break;
				case "5":
					icons[pos] = DIGIT_ICON_5_16;
					break;
				case "6":
					icons[pos] = DIGIT_ICON_6_16;
					break;
				case "7":
					icons[pos] = DIGIT_ICON_7_16;
					break;
				case "8":
					icons[pos] = DIGIT_ICON_8_16;
					break;
				case "9":
					icons[pos] = DIGIT_ICON_9_16;
					break;
				default:
			}
			pos++;
		}
		return new CompoundIcon(CompoundIcon.Axis.X_AXIS, icons);
	}

	/**
	 * @return .
	 */
	public static Image buildCompositeImageIcon() {
		Icon cloud24 = IconCache.fetch("/img/32x32/x-collection-shadow/cloud.png");
		Icon data16 = IconCache.fetch("/img/16x16/x-collection-shadow/data.png");
		return new CompositeIcon(cloud24, data16).getImage();
	}

	/**
	 * @param path1 .
	 * @param path2 .
	 * @return .
	 */
	public static Image buildCompositeImageIcon(String path1, String path2) {
		Icon icon1 = IconCache.fetch(path1);
		Icon icon2 = IconCache.fetch(path2);
		return new CompositeIcon(icon1, icon2).getImage();
	}

	/**
	 * @param path1 .
	 * @param path2 .
	 * @return .
	 */
	public static Icon buildCompositeIcon(String path1, String path2) {
		Icon icon1 = IconCache.fetch(path1);
		Icon icon2 = IconCache.fetch(path2);
		return new CompositeIcon(icon1, icon2);
	}

	/**
	 * @return .
	 */
	public static Pair<Integer, Integer> getMainDisplaySize() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();

		return Pair.of(width, height);
	}

	public static Set<String> selectedNames(JideSplitButton splitButton) {
		return Arrays.stream(splitButton.getPopupMenu().getComponents())
			.map(component -> (UIFCheckBoxMenuItem) component)
			.filter(AbstractButton::isSelected)
			.map(Component::getName)
			.collect(Collectors.toSet());
	}

	/**
	 * @param s .
	 */
	public static String bold(String s) {
		return "<b>" + s + "</b>";
	}

	/**
	 * @param s .
	 */
	public static String italic(String s) {
		return "<i>" + s + "</i>";
	}

	/**
	 * @param s .
	 */
	public static String underline(String s) {
		return "<u>" + s + "</u>";
	}

	/**
	 * @param s .
	 */
	public static String strike(String s) {
		return "<s>" + s + "</s>";
	}
}
