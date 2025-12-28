package com.iconiux.ezrfs.tray.eventbus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@FieldNameConstants
public class RFSInfoMessageBean extends InfoMessageBean {
	public RFSInfoMessageBean() {
		super();
	}

	public RFSInfoMessageBean(InfoMessageType type, String message) {
		super(type, message);
	}

	public RFSInfoMessageBean(InfoMessageType type, String message, Integer count) {
		super(type, message, count);
	}

	public RFSInfoMessageBean(InfoMessageBean infoMessageBean) {
		super(infoMessageBean.getType(), infoMessageBean.getMessage(), infoMessageBean.getCount());
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static RFSInfoMessageBean createDebug(String message) {
		return new RFSInfoMessageBean(InfoMessageBean.createDebug(message));
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static RFSInfoMessageBean createInfo(String message) {
		return new RFSInfoMessageBean(InfoMessageBean.createInfo(message));
	}

	/**
	 * @return .
	 */
	public static RFSInfoMessageBean createInfoOK() {
		return new RFSInfoMessageBean(InfoMessageBean.createInfoOK());
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static RFSInfoMessageBean createError(String message) {
		return new RFSInfoMessageBean(InfoMessageBean.createError(message));
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static RFSInfoMessageBean createWarn(String message) {
		return new RFSInfoMessageBean(InfoMessageBean.createWarn(message));
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static RFSInfoMessageBean createProcess(String message) {
		return new RFSInfoMessageBean(InfoMessageBean.createProcess(message));
	}
}
