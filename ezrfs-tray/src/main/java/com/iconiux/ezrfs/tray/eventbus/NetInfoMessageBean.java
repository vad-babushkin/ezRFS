package com.iconiux.ezrfs.tray.eventbus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@FieldNameConstants
public class NetInfoMessageBean extends InfoMessageBean{
	public NetInfoMessageBean() {
		super();
	}

	public NetInfoMessageBean(InfoMessageType type, String message) {
		super(type, message);
	}

	public NetInfoMessageBean(InfoMessageType type, String message, Integer count) {
		super(type, message, count);
	}

	public NetInfoMessageBean(InfoMessageBean infoMessageBean) {
		super(infoMessageBean.getType(), infoMessageBean.getMessage(), infoMessageBean.getCount());
	}


	/**
	 * @param message .
	 * @return .
	 */
	public static NetInfoMessageBean createDebug(String message) {
		return new NetInfoMessageBean(InfoMessageBean.createDebug(message));
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static NetInfoMessageBean createInfo(String message) {
		return new NetInfoMessageBean(InfoMessageBean.createInfo(message));
	}

	/**
	 * @return .
	 */
	public static NetInfoMessageBean createInfoOK() {
		return new NetInfoMessageBean(InfoMessageBean.createInfoOK());
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static NetInfoMessageBean createError(String message) {
		return new NetInfoMessageBean(InfoMessageBean.createError(message));
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static NetInfoMessageBean createWarn(String message) {
		return new NetInfoMessageBean(InfoMessageBean.createWarn(message));
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static NetInfoMessageBean createProcess(String message) {
		return new NetInfoMessageBean(InfoMessageBean.createProcess(message));
	}
}
