package com.iconiux.ezrfs.tray.service.task;

import com.iconiux.ezrfs.tray.eventbus.ServerInfoMessageBean;
import com.iconiux.ezrfs.tray.util.NetworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.bushe.swing.event.EventBus;

import javax.swing.*;
import java.awt.*;

/**
 * Проверка доступа к серверу
 */
@Slf4j
public class RefreshServerStatePanelTask extends SwingWorker<Pair<Boolean, String>, Object> {
	private String host;
	private int port;

	public RefreshServerStatePanelTask(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public Pair<Boolean, String> doInBackground() {
		log.info("ping {}:{}", host, port);
		Pair<Boolean, String> reachable = NetworkUtils.connectToHost(host, port);
		log.info("=> {}", reachable);

		return reachable;
	}

	@Override
	protected void done() {
		try {
			Pair<Boolean, String> pair = get();
			EventQueue.invokeLater(() -> {
				if (pair.getKey()) {
					EventBus.publish(ServerInfoMessageBean.createInfoOK());
//						EventBus.publish(InfoMessageBean.createInfoOK());
				} else {
					EventBus.publish(ServerInfoMessageBean.createError(pair.getValue()));
//						EventBus.publish(InfoMessageBean.createError(pair.getValue()));
				}
			});
		} catch (Exception e) {
			log.error("", e);
		}
	}
}

