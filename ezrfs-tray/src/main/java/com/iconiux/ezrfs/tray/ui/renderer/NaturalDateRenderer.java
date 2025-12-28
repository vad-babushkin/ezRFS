package com.iconiux.ezrfs.tray.ui.renderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Date;

import static humanize.Humanize.naturalTime;

public class NaturalDateRenderer extends DefaultTableCellRenderer {

	public NaturalDateRenderer() {
		super();
		setHorizontalAlignment(SwingConstants.LEADING);
		setVerticalAlignment(SwingConstants.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value == null) {
			return this;
		}
		if (!(value instanceof Date date)) {
			return this;
		}

		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		label.setText(naturalTime(date));
		label.setToolTipText(date.toString());

		if (isSelected) {
			this.setBackground(table.getSelectionBackground());
		} else {
			this.setBackground(table.getBackground());
		}

		return this;
	}
}
