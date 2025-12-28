package com.iconiux.ezrfs.gui;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.components.FlatPopupMenu;
import com.formdev.flatlaf.icons.FlatClearIcon;
import com.formdev.flatlaf.icons.FlatRevealIcon;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Demo {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			FlatLaf.setup(new FlatLightLaf());
			JFrame frame = new JFrame();
			frame.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (SwingUtilities.isRightMouseButton(e)) {
						final FlatPopupMenu popupMenu = new FlatPopupMenu();
						for (int i = 0; i < 10; i++) {
							final JMenu menu = new JMenu("Test " + (i + 1));
							menu.setIcon(new FlatClearIcon());
							for (int j = 0; j < 10; j++) {
								menu.add("Test " + (j + 1)).setIcon(new FlatRevealIcon());
							}
							popupMenu.add(menu);
						}
						popupMenu.show(frame, e.getX(), e.getY());
					}
				}
			});
			frame.setSize(500, 500);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
