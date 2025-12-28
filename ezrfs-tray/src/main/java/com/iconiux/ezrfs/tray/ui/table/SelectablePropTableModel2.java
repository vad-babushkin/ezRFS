package com.iconiux.ezrfs.tray.ui.table;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.iconiux.ezrfs.tray.ui.vo.SelectablePropVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SelectablePropTableModel2 extends MzBeanTableModel<SelectablePropVO> {
	ListMultimap<String, String> uidListMultimap = ArrayListMultimap.create();

	public SelectablePropTableModel2() {
		this(false);
	}

	// конструктор позволяет задать возможность редактирования
	public SelectablePropTableModel2(boolean editable) {
		super(editable, SelectablePropVO.class);
//		// здесь мы будем хранить названия столбцов
//		columnNames = new String[]{
//			"puid",
//			"Тип",
//			"Свойство",
//			"+"
//		};
//
//		// список типов столбцов
//		columnTypes = new Class[]{
//			String.class,
//			GraphNodeTypeBean.class,
//			String.class,
//			Boolean.class
//		};
		columnVisibles = new boolean[]{false, true, true, true, true};
		columnEditables = new boolean[]{false, false, false, false, false};
	}

//	public static int getColNumByName(final SelectablePropVO.Fields field) {
//		switch (field) {
//			case Fields.cuid:
//				return 0;
//			case Fields.uploadedDateTime:
//				return 1;
//			case Fields.sizeHumanize:
//				return 2;
//			case Fields.fileName:
//				return 3;
//			case Fields.url:
//				return 4;
//			default:
//				throw new IllegalArgumentException("not implemented " + field);
//		}
//	}
}
