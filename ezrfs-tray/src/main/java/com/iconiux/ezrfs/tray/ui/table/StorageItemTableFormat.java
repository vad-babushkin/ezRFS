package com.iconiux.ezrfs.tray.ui.table;

import ca.odell.glazedlists.gui.TableFormat;
import com.iconiux.ezrfs.tray.ui.vo.StorageItem;

public class StorageItemTableFormat implements TableFormat {

	String[] columnNames = {"Время", "Имя", "Размер", "Адрес"};

	public int getColumnCount() {
		return columnNames.length;
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getColumnValue(Object baseObject, int column) {
		StorageItem storageItem = (StorageItem) baseObject;
		return switch (column) {
			case 0 -> storageItem.getUploadedDateTime();
			case 1 -> storageItem.getFileName();
			case 2 -> storageItem.getSizeHumanize();
			case 3 -> storageItem.getUrl();
			default -> throw new IllegalArgumentException("Expected column 0 or 1, got " + column);
		};
	}
}
