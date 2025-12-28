package com.iconiux.ezrfs.tray.ui.table;

public interface TableAware {
	int getColumnCount();

	String getColumnName(int columnIndex);

	Class<?> getColumnClass(int columnIndex);

	Object getColumnValue(int columnIndex);

	String getKey();

	void setElementAt(Object value, int column);
}
