package com.iconiux.ezrfs.tray.service.task;

import com.iconiux.ezrfs.tray.eventbus.RFSInfoMessageBean;
import com.iconiux.ezrfs.tray.service.ConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.bushe.swing.event.EventBus;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static com.iconiux.ezrfs.tray.ConstantHolder.OK;

/**
 * Проверка доступа к интернет
 */
@Slf4j
public class RefreshRFSStatePanelTask extends SwingWorker<Pair<Boolean, String>, Object> {
	private ConnectionService connectionService;

	public RefreshRFSStatePanelTask(ConnectionService connectionService) {
		this.connectionService = connectionService;
	}

	@Override
	public Pair<Boolean, String> doInBackground() {
		log.info("alive {}", connectionService.getHost());
		try {
			return Pair.of(connectionService.checkAlive(), OK);
		} catch (IOException | InterruptedException e) {
			return Pair.of(false, e.toString());
		}
	}

	@Override
	protected void done() {
		try {
			Pair<Boolean, String> pair = get();
			EventQueue.invokeLater(() -> {
				if (pair.getKey()) {
					EventBus.publish(RFSInfoMessageBean.createInfoOK());
//						EventBus.publish(InfoMessageBean.createInfoOK());
				} else {
					EventBus.publish(RFSInfoMessageBean.createError(pair.getValue()));
//						EventBus.publish(InfoMessageBean.createError(pair.getValue()));
				}
			});
		} catch (Exception e) {
			log.error("", e);
		}
	}
}

