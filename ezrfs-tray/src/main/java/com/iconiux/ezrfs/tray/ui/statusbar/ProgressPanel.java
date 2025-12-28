/*
 * Created by JFormDesigner on Fri Apr 18 02:09:51 YEKT 2025
 */

package com.iconiux.ezrfs.tray.ui.statusbar;

import com.iconiux.ezrfs.tray.eventbus.ProgressInfoMessageBean;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;

import javax.swing.*;
import java.awt.*;

/**
 * @author vad
 */
@Getter
@Slf4j
public class ProgressPanel extends JPanel {
	public ProgressPanel() {
		initComponents();

		AnnotationProcessor.process(this);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
		progressBar = new JProgressBar();
		countLabel = new JLabel();

		//======== this ========
		setName("this");
		setLayout(new FormLayout(
			"default, $lcgap, default",
			"default:grow, fill:default, default:grow"));

		//---- progressBar ----
		progressBar.setBorderPainted(false);
		progressBar.setPreferredSize(new Dimension(148, 10));
		progressBar.setMaximumSize(new Dimension(148, 10));
		progressBar.setMinimumSize(new Dimension(10, 10));
		progressBar.setStringPainted(true);
		progressBar.setFocusable(false);
		progressBar.setName("progressBar");
		add(progressBar, CC.xy(1, 2));

		//---- countLabel ----
		countLabel.setFont(countLabel.getFont().deriveFont(countLabel.getFont().getStyle() | Font.BOLD));
		countLabel.setName("countLabel");
		add(countLabel, CC.xy(3, 2));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
	private JProgressBar progressBar;
	private JLabel countLabel;
	// JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

	@EventSubscriber(eventClass = ProgressInfoMessageBean.class)
	public void onProgressInfoMessageBean(ProgressInfoMessageBean messageBean) {
		log.info("onProgressInfoMessageBean {}", messageBean);
		progressBar.setIndeterminate(false);

		if (messageBean.getProgress() == 100) {
			progressBar.setStringPainted(false);
			progressBar.setValue(messageBean.getProgress());
			progressBar.setVisible(false);
		} else {
			progressBar.setStringPainted(true);
			progressBar.setValue(messageBean.getProgress());
			progressBar.setVisible(true);
		}
	}
}
