package com.iconiux.ezrfs.tray.service;

import com.iconiux.ezrfs.tray.eventbus.ClipboardManagerCommandBean;
import com.iconiux.ezrfs.tray.eventbus.InfoMessageBean;
import com.iconiux.ezrfs.tray.ui.clipboard.ClipboardListener;
import lombok.extern.slf4j.Slf4j;
import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;

@Slf4j
public class ClipboardManagerService {
	private ClipboardListener clipboardListener = null;

	public ClipboardManagerService() {
		AnnotationProcessor.process(this);
		log.info("ClipboardManagerService initialized");
	}

	@EventSubscriber(eventClass = ClipboardManagerCommandBean.class)
	public void onClipboardManagerCommandBean(ClipboardManagerCommandBean messageBean) {
		log.info("onClipboardManagerCommandBean {}", messageBean);

		if (messageBean.getEnable()) {
			if (clipboardListener != null) {
				clipboardListener.terminate();
			}
			clipboardListener = new ClipboardListener();
			new Thread(clipboardListener).start();
			EventBus.publish(InfoMessageBean.createInfo("Начинаем следить за буфером обмена"));
		} else {
			clipboardListener.terminate();
			clipboardListener = null;
			System.gc();
			EventBus.publish(InfoMessageBean.createInfo("Закончили следить за буфером обмена"));
		}
	}
}
