/*
 * Created by JFormDesigner on Wed Dec 29 00:37:16 YEKT 2021
 */

package com.iconiux.ezrfs.tray.ui.statusbar;

import com.iconiux.ezrfs.tray.eventbus.InfoMessageBean;
import com.iconiux.ezrfs.tray.ui.UIHelper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jdesktop.swingx.JXLabel;

import javax.swing.*;
import java.awt.*;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author 1
 */
@Slf4j
@Getter
public class StatePanel extends JPanel {
	private JXLabel label;

	private StateEnum stateEnum = StateEnum.OFF;
	private String indicatorName = "";

	public StatePanel() {
		initComponents();
	}

	private void initComponents() {
		label = new JXLabel();
		setName("this");
		setLayout(new BorderLayout());
		label.setIcon(new ImageIcon(getClass().getResource("/img/16x16/x-collection-shadow/bullet_ball_grey.png")));
		label.setName("label");
		add(label, BorderLayout.CENTER);
	}

	public StatePanel setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
		return this;
	}

	public StatePanel toState(StateEnum stateEnum) {
		return toState(stateEnum, "");
	}

	public StatePanel toState(StateEnum stateEnum, String message) {
		this.stateEnum = stateEnum;
		EventQueue.invokeLater(() -> {
			if (isNotBlank(message)) {
				label.setToolTipText((indicatorName + ": " + message).trim());
			} else {
				label.setToolTipText((indicatorName).trim());
			}
			switch (stateEnum) {
				case ERROR:
					label.setEnabled(true);
					label.setIcon(UIHelper.STATE_ERROR_ICON);
					break;
				case WARN:
					label.setEnabled(true);
					label.setIcon(UIHelper.STATE_WARNING_ICON);
					break;
				case PROCESS:
					label.setEnabled(true);
					label.setIcon(UIHelper.STATE_PROCESS_ICON);
					break;
				case INFO:
					label.setEnabled(true);
					label.setIcon(UIHelper.STATE_OK_ICON);
					break;
				case OFF:
					label.setEnabled(false);
					label.setIcon(UIHelper.STATE_OFF_ICON);
					break;
				default:
					log.error("неизвестное свойство {}", stateEnum);
			}
		});

		return this;
	}

	/**
	 *
	 * @param messageBean .
	 */
	public void toState(InfoMessageBean messageBean) {
		StateEnum stateEnum1 = StateEnum.OFF;
		switch(messageBean.getType()) {
//			case DEBUG -> stateEnum1 = StateEnum.OFF;
			case PROCESS -> stateEnum1 = StateEnum.PROCESS;
			case INFO -> stateEnum1 = StateEnum.INFO;
			case WARN -> stateEnum1 = StateEnum.WARN;
			case ERROR -> stateEnum1 = StateEnum.ERROR;
		}
		toState(stateEnum1, messageBean.getMessage());
	}
}
