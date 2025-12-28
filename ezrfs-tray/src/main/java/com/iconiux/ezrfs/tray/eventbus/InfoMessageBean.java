package com.iconiux.ezrfs.tray.eventbus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class InfoMessageBean {
	private InfoMessageType type = InfoMessageType.INFO;
	private String message;
	private Integer count = null;

	public InfoMessageBean(InfoMessageType type, String message) {
		this.type = type;
		this.message = message;
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static InfoMessageBean createDebug(String message) {
		return new InfoMessageBean().setType(InfoMessageType.DEBUG).setMessage(message);
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static InfoMessageBean createInfo(String message) {
		return new InfoMessageBean().setType(InfoMessageType.INFO).setMessage(message);
	}

	/**
	 * @return .
	 */
	public static InfoMessageBean createInfoOK() {
		return new InfoMessageBean().setType(InfoMessageType.INFO).setMessage("OK");
	}

	/**
	 * @return .
	 */
	public static InfoMessageBean createInfoDone() {
		return new InfoMessageBean().setType(InfoMessageType.INFO).setMessage("Done");
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static InfoMessageBean createError(String message) {
		return new InfoMessageBean().setType(InfoMessageType.ERROR).setMessage(message);
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static InfoMessageBean createWarn(String message) {
		return new InfoMessageBean().setType(InfoMessageType.WARN).setMessage(message);
	}

	/**
	 * @param message .
	 * @return .
	 */
	public static InfoMessageBean createProcess(String message) {
		return new InfoMessageBean().setType(InfoMessageType.PROCESS).setMessage(message);
	}
}
