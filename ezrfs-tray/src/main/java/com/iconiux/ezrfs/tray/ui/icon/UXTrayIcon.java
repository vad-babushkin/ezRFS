package com.iconiux.ezrfs.tray.ui.icon;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UXTrayIcon extends java.awt.TrayIcon {

	private JPopupMenu jPopupMenu;
	private Window window;

	public UXTrayIcon(Image image) {
		super(image);

		this.jPopupMenu = new JPopupMenu();
		this.window = new JFrame();
	}

	public UXTrayIcon(Image image, String title) {
		super(image, title);

		this.jPopupMenu = new JPopupMenu();
		this.window = new JFrame();
	}

	public UXTrayIcon(Image image, String title, JPopupMenu jPopupMenu) {
		super(image, title);
		this.jPopupMenu = jPopupMenu;
		this.window = new JFrame();
	}

	public UXTrayIcon(Image image, String title, JPopupMenu jPopupMenu, Window window) {
		super(image, title);
		this.jPopupMenu = jPopupMenu;
		if (window == null) {
			window = new JFrame();
		}
		this.window = window;
	}

	public void setup() {
		setImageAutoSize(true);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
//				if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2) {
				if (evt.getButton() == MouseEvent.BUTTON1) {
					window.setVisible(!window.isVisible());
				} else if (evt.getButton() == MouseEvent.BUTTON3 && evt.getClickCount() == 1) {
					Rectangle bounds = getSafeScreenBounds(evt.getPoint());
					Point point = evt.getPoint();
					int x = point.x, y = point.y;

					if (y < bounds.y) {
						y = bounds.y;
					} else if (y > bounds.y + bounds.height) {
						y = bounds.y + bounds.height;
					}

					if (x < bounds.x) {
						x = bounds.x;
					} else if (x > bounds.x + bounds.width) {
						x = bounds.x + bounds.width;
					}

					if (x + jPopupMenu.getPreferredSize().width > bounds.x + bounds.width) {
						x = (bounds.x + bounds.width) - jPopupMenu.getPreferredSize().width;
					}

					if (y + jPopupMenu.getPreferredSize().height > bounds.y + bounds.height) {
						y = (bounds.y + bounds.height) - jPopupMenu.getPreferredSize().height;
					}

					jPopupMenu.setLocation(x, y);
					jPopupMenu.setInvoker(jPopupMenu);
					jPopupMenu.setVisible(true);
				}
			}
		});
	}

	public static Rectangle getSafeScreenBounds(Point pos) {

		Rectangle bounds = getScreenBoundsAt(pos);
		Insets insets = getScreenInsetsAt(pos);

		bounds.x += insets.left;
		bounds.y += insets.top;
		bounds.width -= (insets.left + insets.right);
		bounds.height -= (insets.top + insets.bottom);

		return bounds;

	}

	public static Insets getScreenInsetsAt(Point pos) {
		GraphicsDevice gd = getGraphicsDeviceAt(pos);
		Insets insets = null;
		if (gd != null) {
			insets = Toolkit.getDefaultToolkit().getScreenInsets(gd.getDefaultConfiguration());
		}
		return insets;
	}

	public static Rectangle getScreenBoundsAt(Point pos) {
		GraphicsDevice gd = getGraphicsDeviceAt(pos);
		Rectangle bounds = null;
		if (gd != null) {
			bounds = gd.getDefaultConfiguration().getBounds();
		}
		return bounds;
	}

	public static GraphicsDevice getGraphicsDeviceAt(Point pos) {

		GraphicsDevice device = null;

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice lstGDs[] = ge.getScreenDevices();

		ArrayList<GraphicsDevice> lstDevices = new ArrayList<GraphicsDevice>(lstGDs.length);

		for (GraphicsDevice gd : lstGDs) {

			GraphicsConfiguration gc = gd.getDefaultConfiguration();
			Rectangle screenBounds = gc.getBounds();

			if (screenBounds.contains(pos)) {
				lstDevices.add(gd);
			}
		}

		if (!lstDevices.isEmpty()) {
			device = lstDevices.get(0);
		} else {
			device = ge.getDefaultScreenDevice();
		}

		return device;
	}
}
