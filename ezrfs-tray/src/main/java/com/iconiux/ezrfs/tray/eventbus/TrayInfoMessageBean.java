package com.iconiux.ezrfs.tray.eventbus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@FieldNameConstants
public class TrayInfoMessageBean extends InfoMessageBean {
	public TrayInfoMessageBean() {
		super();
	}

	public TrayInfoMessageBean(InfoMessageType type, String message) {
		super(type, message);
	}

	public TrayInfoMessageBean(InfoMessageType type, String message, Integer count) {
		super(type, message, count);
	}

	public TrayInfoMessageBean(InfoMessageBean infoMessageBean) {
		super(infoMessageBean.getType(), infoMessageBean.getMessage(), infoMessageBean.getCount());
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static TrayInfoMessageBean createDebug(String message) {
		return new TrayInfoMessageBean(InfoMessageBean.createDebug(message));
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static TrayInfoMessageBean createInfo(String message) {
		return new TrayInfoMessageBean(InfoMessageBean.createInfo(message));
	}

	/**
	 * @return .
	 */
	public static TrayInfoMessageBean createInfoOK() {
		return new TrayInfoMessageBean(InfoMessageBean.createInfoOK());
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static TrayInfoMessageBean createError(String message) {
		return new TrayInfoMessageBean(InfoMessageBean.createError(message));
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static TrayInfoMessageBean createWarn(String message) {
		return new TrayInfoMessageBean(InfoMessageBean.createWarn(message));
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static TrayInfoMessageBean createProcess(String message) {
		return new TrayInfoMessageBean(InfoMessageBean.createProcess(message));
	}
}
