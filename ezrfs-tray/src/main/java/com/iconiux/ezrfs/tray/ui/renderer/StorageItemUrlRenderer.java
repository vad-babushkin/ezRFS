package com.iconiux.ezrfs.tray.ui.renderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Date;
import java.util.Objects;

import static com.iconiux.ezrfs.tray.util.RFSHelper.*;

public class StorageItemUrlRenderer extends DefaultTableCellRenderer {

	public StorageItemUrlRenderer() {
		super();
		setHorizontalAlignment(SwingConstants.LEADING);
		setVerticalAlignment(SwingConstants.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value == null) {
			return this;
		}
		if (!(value instanceof String url)) {
			return this;
		}

		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		label.setText(cutCuidFromUrlSafe(url));
		label.setToolTipText(url);
		label.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/16x16/x-collection-shadow/anchor2.png"))));

		if (isSelected) {
			this.setBackground(table.getSelectionBackground());
		} else {
			this.setBackground(table.getBackground());
		}

		return this;
	}
}
