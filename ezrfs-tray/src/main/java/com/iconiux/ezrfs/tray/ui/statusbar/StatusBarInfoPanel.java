/*
 * Created by JFormDesigner on Wed Dec 29 00:13:47 YEKT 2021
 */

package com.iconiux.ezrfs.tray.ui.statusbar;

import com.google.common.base.Stopwatch;
import com.iconiux.ezrfs.tray.eventbus.InfoMessageBean;
import com.iconiux.ezrfs.tray.ui.UIHelper;
import lombok.extern.slf4j.Slf4j;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

import static com.iconiux.ezrfs.tray.ui.UIHelper.NULL_ICON_16;
import static com.iconiux.ezrfs.tray.ui.UIHelper.buildCountIcon;

/**
 * @author 1
 */
@Slf4j
public class StatusBarInfoPanel extends JPanel {
	private final Stopwatch stopwatch = Stopwatch.createUnstarted();
	private JLabel showLabel;
	private ProgressPanel progressPanel;
//	private StatusBarPopup popup;

	/**
	 * @param infoMessageBean .
	 */
	public void logMessage(InfoMessageBean infoMessageBean) {
		if (infoMessageBean == null) {
			logMessage(StateEnum.WARN, "Пустое сообщение", null);
			return;
		}
		switch (infoMessageBean.getType()) {
			case DEBUG -> logMessage(StateEnum.OFF, infoMessageBean.getMessage(), infoMessageBean.getCount());
			case INFO -> logMessage(StateEnum.INFO, infoMessageBean.getMessage(), infoMessageBean.getCount());
			case PROCESS -> logMessage(StateEnum.PROCESS, infoMessageBean.getMessage(), infoMessageBean.getCount());
			case ERROR -> logMessage(StateEnum.ERROR, infoMessageBean.getMessage(), infoMessageBean.getCount());
			case WARN -> logMessage(StateEnum.WARN, infoMessageBean.getMessage(), infoMessageBean.getCount());
			default -> log.info("неожиданный type {}", infoMessageBean.getType());
		}
	}

	/**
	 *
	 * @param messge .
	 * @return .
	 */
	private String toHtml(String messge) {
		return "<html>" + messge + "</html>";
	}

	/**
	 * @param state    .
	 * @param aMessage .
	 * @param count    .
	 */
	public void logMessage(final StateEnum state, final String aMessage, Integer count) {
		EventQueue.invokeLater(() -> {
			switch (state) {
				case ERROR:
					showLabel.setIcon(UIHelper.STATE_ERROR_ICON);
					showLabel.setForeground(Color.RED);
					showLabel.setText(toHtml(aMessage));
					finishUpdate();
					progressPanel.getCountLabel().setIcon(buildCountIcon(count));
					progressPanel.getProgressBar().setIndeterminate(false);
					break;
				case WARN:
					showLabel.setIcon(UIHelper.STATE_WARNING_ICON);
					showLabel.setForeground(Color.YELLOW.darker());
					showLabel.setText(toHtml(aMessage));
					finishUpdate();
					progressPanel.getCountLabel().setIcon(buildCountIcon(count));
					progressPanel.getProgressBar().setIndeterminate(false);
					break;
				case PROCESS:
					stopwatch.reset();
					stopwatch.start();
					showLabel.setIcon(UIHelper.STATE_PROCESS_ICON);
					showLabel.setForeground(Color.BLUE.darker());
					showLabel.setText(toHtml(aMessage));
					progressPanel.setEnabled(true);
					progressPanel.setVisible(true);
					progressPanel.getProgressBar().setIndeterminate(true);
					break;
				case INFO:
					showLabel.setIcon(UIHelper.STATE_OK_ICON);
					showLabel.setForeground(Color.GREEN.darker());
					showLabel.setText(toHtml(aMessage));
					finishUpdate();
					progressPanel.getCountLabel().setIcon(buildCountIcon(count));
					progressPanel.getProgressBar().setIndeterminate(false);
					break;
				case OFF:
					progressPanel.getCountLabel().setIcon(NULL_ICON_16);
					showLabel.setIcon(UIHelper.STATE_OFF_ICON);
					showLabel.setForeground(Color.GRAY);
					showLabel.setText(toHtml(aMessage));
					progressPanel.setEnabled(false);
					progressPanel.setVisible(false);
					progressPanel.getCountLabel().setIcon(buildCountIcon(count));
					progressPanel.getProgressBar().setIndeterminate(false);
					break;
				default:
			}
		});
	}

	private void finishUpdate() {
		progressPanel.getCountLabel().setIcon(NULL_ICON_16);
		progressPanel.getProgressBar().setEnabled(false);
		progressPanel.getProgressBar().setVisible(false);
		showLabel.setToolTipText("Время работы " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
		if (stopwatch.isRunning()) {
			stopwatch.stop();
		}
	}

//	private void showLabelTextMouseClicked(MouseEvent e) {
//		popup.setOwner((JLabel) e.getSource());
//		popup.setVisible(true);
//	}

	public StatusBarInfoPanel() {
		initComponents();
		eventBusInit();
//		showLabel.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				log.info("{}", e);
//				showLabelTextMouseClicked(e);
//			}
//		});
	}

	/**
	 *
	 */
	private void initComponents() {
		showLabel = new JLabel();

		progressPanel = new ProgressPanel();
		progressPanel.getProgressBar().setStringPainted(false);

		//======== this ========
		setName("this");
		setLayout(new BorderLayout());

		//---- showLabel ----
		showLabel.setText("");
		showLabel.setIcon(new ImageIcon(getClass().getResource("/img/24x24/x-collection-shadow/bullet_ball_glass_gray.png")));
		showLabel.setName("showLabel");
		add(showLabel, BorderLayout.CENTER);

		//---- progressPanel ----
		progressPanel.setName("progressPanel");
		add(progressPanel, BorderLayout.EAST);

//		//---- popup ----
//		popup = new StatusBarPopup();
	}

	/**
	 * подписываем компонент на сообщения от EventBus
	 */
	public void eventBusInit() {
		AnnotationProcessor.process(this); //this line can be avoided with a compile-time tool or an Aspect
	}

	/**
	 * @param infoMessageBean .
	 */

	@EventSubscriber(eventClass = InfoMessageBean.class)
	public void onInfoMessageBean(InfoMessageBean infoMessageBean) {
		log.info("onInfoMessageBean {}", infoMessageBean);
		logMessage(infoMessageBean);
	}
}
