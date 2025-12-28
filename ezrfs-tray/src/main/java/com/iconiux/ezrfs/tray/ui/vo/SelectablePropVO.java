package com.iconiux.ezrfs.tray.ui.vo;

import com.iconiux.ezrfs.tray.ui.table.TableAware;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;


@Data
@Accessors(chain = true)
@FieldNameConstants(asEnum = true)
public class SelectablePropVO implements TableAware, Serializable, Comparable<SelectablePropVO> {
	protected String cuid;
	protected LocalDateTime uploadedDateTime;
	protected String fileName;
	protected String sizeHumanize;
	protected String url;
	private Boolean selected = false;

	public int getColumnCount() {
		return 5;
	}

	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return "#";
			case 1:
				return "Время";
			case 2:
				return "Файл";
			case 3:
				return "Размер";
			case 4:
				return "Адрес";
			default:
				throw new IllegalArgumentException("not implemented " + columnIndex);
		}
	}

	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 0:
			case 2:
			case 3:
			case 4:
				return String.class;
			case 1:
				return LocalDateTime.class;
			default:
				throw new IllegalArgumentException("not implemented " + columnIndex);
		}
	}

	@Override
	public Object getColumnValue(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return cuid;
			case 1:
				return uploadedDateTime;
			case 2:
				return fileName;
			case 3:
				return sizeHumanize;
			case 4:
				return url;
			default:
				throw new IllegalArgumentException("not implemented " + columnIndex);
		}
	}

	@Override
	public String getKey() {
		return cuid;
	}

	@Override
	public void setElementAt(Object value, int columnIndex) {
		switch (columnIndex) {
			case 0:
				this.cuid = (String) value;
				break;
			case 1:
				this.uploadedDateTime = (LocalDateTime) value;
				break;
			case 2:
				this.cuid = (String) value;
				break;
			case 3:
				this.cuid = (String) value;
				break;
			case 4:
				this.cuid = (String) value;
				break;
			default:
				throw new IllegalArgumentException("not implemented " + columnIndex);
		}
	}

//	public static Comparator<PropTypeBean> NULL_SAFE_PROP_TYPE_COMPARATOR = Comparator
//		.nullsFirst(Comparator.comparing(PropTypeBean::getType));

	protected static Comparator<SelectablePropVO> metadataComparator = Comparator.naturalOrder();
//		.thenComparing(SelectablePropVO::getUploadedDateTime, NULL_SAFE_LOCALDATETIME_COMPARATOR)
//		.thenComparing(SelectablePropVO::getFileName, NULL_SAFE_STRING_COMPARATOR)
//		.thenComparing(SelectablePropVO::getCuid, NULL_SAFE_STRING_COMPARATOR);

	@Override
	public int compareTo(@NotNull SelectablePropVO o) {
		return metadataComparator.compare(this, o);
	}
}
