package com.iconiux.ezrfs.tray.eventbus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@FieldNameConstants
public class ServerInfoMessageBean extends InfoMessageBean{
	public ServerInfoMessageBean() {
		super();
	}

	public ServerInfoMessageBean(InfoMessageType type, String message) {
		super(type, message);
	}

	public ServerInfoMessageBean(InfoMessageType type, String message, Integer count) {
		super(type, message, count);
	}

	public ServerInfoMessageBean(InfoMessageBean infoMessageBean) {
		super(infoMessageBean.getType(), infoMessageBean.getMessage(), infoMessageBean.getCount());
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static ServerInfoMessageBean createDebug(String message) {
		return new ServerInfoMessageBean(InfoMessageBean.createDebug(message));
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static ServerInfoMessageBean createInfo(String message) {
		return new ServerInfoMessageBean(InfoMessageBean.createInfo(message));
	}

	/**
	 * @return .
	 */
	public static ServerInfoMessageBean createInfoOK() {
		return new ServerInfoMessageBean(InfoMessageBean.createInfoOK());
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static ServerInfoMessageBean createError(String message) {
		return new ServerInfoMessageBean(InfoMessageBean.createError(message));
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static ServerInfoMessageBean createWarn(String message) {
		return new ServerInfoMessageBean(InfoMessageBean.createWarn(message));
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static ServerInfoMessageBean createProcess(String message) {
		return new ServerInfoMessageBean(InfoMessageBean.createProcess(message));
	}
}
