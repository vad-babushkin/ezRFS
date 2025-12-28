package com.iconiux.ezrfs.tray.ui.table;

import com.iconiux.ezrfs.tray.ui.ConstantHolderUICommon;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.TableColumnExt;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.Collection;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
public abstract class MzBeanTableModel<T extends TableAware> extends AbstractTableModel {

	protected Vector<T> dataVector = new Vector<T>();
	// флаг редактируемости
	protected boolean editable;

	protected Class<T> clazz;
	protected T fake;

	// здесь мы будем хранить названия столбцов
	protected String[] columnNames = new String[0];
	// список видимости столбцов
	protected boolean[] columnVisibles = new boolean[]{};
	// список редактируемости столбцов
	protected boolean[] columnEditables = new boolean[]{};

	@SneakyThrows
	public MzBeanTableModel(boolean editable, Class<T> clazz) {
		this.editable = editable;
		this.clazz = clazz;
		this.fake = clazz.newInstance();
	}

	/**
	 * @return .
	 */
	public String[] getColumnNames() {

		if (columnNames.length == 0) {
			columnNames = new String[getColumnCount()];
			for (int i = 0; i < getColumnCount(); i++) {
				columnNames[i] = fake.getColumnName(i);
			}
		}

		return columnNames;
	}

	/**
	 * @return .
	 */
	public boolean[] getColumnVisibles() {
		if (columnVisibles.length == 0) {
			columnVisibles = new boolean[getColumnCount()];
			for (int i = 0; i < getColumnCount(); i++) {
				columnVisibles[i] = true;
			}
		}
		return columnVisibles;
	}

	/**
	 * @return .
	 */
	public boolean[] getColumnEditables() {
		if (columnEditables.length == 0) {
			columnEditables = new boolean[getColumnCount()];
			for (int i = 0; i < getColumnCount(); i++) {
				columnVisibles[i] = false;
			}
		}
		return columnEditables;
	}

	/**
	 * Количество колонок
	 *
	 * @return .
	 */
	@Override
	public int getColumnCount() {
		return fake.getColumnCount();
	}

	/**
	 * тип данных столбца
	 *
	 * @param column .
	 * @return .
	 */
	@Override
	public Class getColumnClass(int column) {
		return fake.getColumnClass(column);
	}

	/**
	 * название столбца
	 *
	 * @param column .
	 * @return .
	 */
	@Override
	public String getColumnName(int column) {
		if (columnNames.length == 0) {
			return fake.getColumnName(column);
		} else {
			return columnNames[column];
		}
	}

	/**
	 * @return .
	 */
	@Override
	public int getRowCount() {
		return dataVector.size();
	}

	/**
	 * @param row .
	 * @return .
	 */
	public T getRow(int row) {
		return dataVector.get(row);
	}

	/**
	 * @param rowIndex    .
	 * @param columnIndex .
	 * @return .
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return dataVector.elementAt(rowIndex).getColumnValue(columnIndex);
	}

	/**
	 * замена значения ячейки
	 *
	 * @param value  .
	 * @param row    .
	 * @param column .
	 */
	@Override
	public void setValueAt(Object value, int row, int column) {
		dataVector.elementAt(row).setElementAt(value, column);
		fireTableCellUpdated(row, column);
	}

	/**
	 * @param row    .
	 * @param column .
	 * @return .
	 */
	public boolean isCellEditable(int row, int column) {
		if (!editable) {
			return false;
		} else {
			return columnEditables[column];
		}
	}

	/**
	 * @param t .
	 */
	public void addRow(T t) {
		insertRow(getRowCount(), t);
	}

	/**
	 * @param rowCount .
	 * @param t        .
	 */
	public void insertRow(int rowCount, T t) {
		log.info("insertRow {} {}", rowCount, t);
		dataVector.insertElementAt(t, rowCount);
		fireTableRowsInserted(rowCount, rowCount);
		log.info("dataVector {}", dataVector);
	}

	/**
	 * @param row .
	 */
	public void removeRow(int row) {
		log.info("remove {}", row);
		dataVector.removeElementAt(row);
//		fireTableRowsDeleted(row, row);
		fireTableDataChanged();
		log.info("dataVector {}", dataVector);
	}

	/**
	 * Выключаем столбцы, у которых прописано visible=false
	 */
	public static void setVisibleColumn(final MzBeanTableModel model,
	                                    final JXTable table,
	                                    final boolean packFlag) {
		EventQueue.invokeLater(() -> {
			boolean[] columnVisibleList = model.getColumnVisibles();
			if (columnVisibleList.length == 0) {
				return;
			}

			String[] columnNames = model.getColumnNames();
			for (int i = 0; i < columnVisibleList.length; i++) {
				TableColumnExt tableColumnExt = table.getColumnExt(columnNames[i]);
				// todo где то в Гноме глюк
				if (tableColumnExt != null) {
					tableColumnExt.setVisible(columnVisibleList[i]);
				}

				if (ConstantHolderUICommon.FIX_WIDTH_HEADERS_50.contains(columnNames[i])) {
					tableColumnExt = table.getColumnExt(columnNames[i]);
					if (tableColumnExt != null) {
						tableColumnExt.setMinWidth(ConstantHolderUICommon.COLUMN_FIX_WIDTH_50);
						tableColumnExt.setMaxWidth(ConstantHolderUICommon.COLUMN_FIX_WIDTH_50);
						tableColumnExt.setPreferredWidth(ConstantHolderUICommon.COLUMN_FIX_WIDTH_50);
					}
				} else if (ConstantHolderUICommon.FIX_WIDTH_HEADERS_75.contains(columnNames[i])) {
					tableColumnExt = table.getColumnExt(columnNames[i]);
					if (tableColumnExt != null) {
						tableColumnExt.setMinWidth(ConstantHolderUICommon.COLUMN_FIX_WIDTH_75);
						tableColumnExt.setMaxWidth(ConstantHolderUICommon.COLUMN_FIX_WIDTH_75);
						tableColumnExt.setPreferredWidth(ConstantHolderUICommon.COLUMN_FIX_WIDTH_75);
					}
				} else if (ConstantHolderUICommon.FIX_WIDTH_HEADERS_100.contains(columnNames[i])) {
					tableColumnExt = table.getColumnExt(columnNames[i]);
					if (tableColumnExt != null) {
						tableColumnExt.setMinWidth(ConstantHolderUICommon.COLUMN_FIX_WIDTH_100);
						tableColumnExt.setMaxWidth(ConstantHolderUICommon.COLUMN_FIX_WIDTH_100);
						tableColumnExt.setPreferredWidth(ConstantHolderUICommon.COLUMN_FIX_WIDTH_100);
					}
				} else if (ConstantHolderUICommon.SYMBOL_HEADERS.contains(columnNames[i])) {
					tableColumnExt = table.getColumnExt(columnNames[i]);
					if (tableColumnExt != null) {
						tableColumnExt.setMinWidth(ConstantHolderUICommon.COLUMN_SYMBOL_WIDTH);
						tableColumnExt.setMaxWidth(ConstantHolderUICommon.COLUMN_SYMBOL_WIDTH);
						tableColumnExt.setPreferredWidth(ConstantHolderUICommon.COLUMN_SYMBOL_WIDTH);
					}
				}
			}
			if (packFlag) {
				table.packAll();
			}
		});
	}

	/**
	 *
	 * @param dtos
	 */
	public void fillModel(Collection<T> dtos) {
		try {
			dataVector.clear();
			dataVector.addAll(dtos);
			fireTableDataChanged();
		} catch (Exception e) {
			log.error("", e);
		}
	}

	/**
	 *
	 * @param puid .
	 */
	public void findAndRemoveRowByKey(String puid) {
		if (isBlank(puid)) {
			return;
		}

		int row = 0;
		for (T t : dataVector) {
			if (puid.equalsIgnoreCase(t.getKey())) {
				removeRow(row);
				return;
			}
			row++;
		}
	}

	/**
	 *
	 * @param puid .
	 */
	public Integer findRowByKey(String puid) {
		if (isBlank(puid)) {
			return null;
		}

		int row = 0;
		for (T t : dataVector) {
			if (puid.equalsIgnoreCase(t.getKey())) {
				return row;
			}
			row++;
		}
		return null;
	}

	/**
	 *
	 * @param puid .
	 * @return .
	 */
	public T findBeanByKey(String puid) {
		if (isBlank(puid)) {
			return null;
		}

		int row = 0;
		for (T t : dataVector) {
			if (puid.equalsIgnoreCase(t.getKey())) {
				return t;
			}
			row++;
		}

		return null;
	}

	/**
	 *
	 * @return .
	 */
	public Set<String> keys() {
		return dataVector.stream()
			.map(TableAware::getKey)
			.collect(Collectors.toSet());
	}
}
