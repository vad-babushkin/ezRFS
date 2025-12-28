package com.iconiux.ezrfs.tray.ui.clipboard;

import ca.odell.glazedlists.event.ListEventPublisher;
import com.iconiux.ezrfs.tray.eventbus.ClipboardBodyBean;
import com.iconiux.ezrfs.tray.util.RFSHelper;
import lombok.extern.slf4j.Slf4j;
import org.bushe.swing.event.EventBus;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.awt.Toolkit.getDefaultToolkit;
import static java.lang.Thread.sleep;

@Slf4j
public class ClipboardListener implements Runnable, ClipboardOwner {
	private volatile boolean alive = true;

	public void terminate() {
		this.alive = false;
	}

	/**
	 *
	 */
	public void run() {
		Transferable trans = getDefaultToolkit().getSystemClipboard().getContents(null);
		getDefaultToolkit().getSystemClipboard().setContents(trans, this);
		log.info("Listening to clipboard...");
		while (alive) {
			Thread.onSpinWait();
		}
		StringSelection data = new StringSelection("ClipboardListener off");
		getDefaultToolkit().getSystemClipboard().setContents(data, null);
		log.info("No more Listening...");
	}

	/**
	 *
	 * @param c the clipboard that is no longer owned .
	 * @param t the contents which this owner had placed on the
	 *         {@code clipboard} .
	 */
	public void lostOwnership(Clipboard c, Transferable t) {
		log.info("...");
		try {
			sleep(200);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		Transferable trans = getDefaultToolkit().getSystemClipboard().getContents(this); //EXCEPTION
		if (alive) {
			processContents(trans);
			getDefaultToolkit().getSystemClipboard().setContents(trans, this);
		} else {
			getDefaultToolkit().getSystemClipboard().setContents(trans, null);
		}
	}

	/**
	 *
	 * @param t .
	 */
	void processContents(Transferable t) {
		log.info("Processing: " + t);
		try {
			if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				String what = (String) (t.getTransferData(DataFlavor.stringFlavor));
				EventBus.publish(new ClipboardBodyBean(what));
				log.info("what: " + what);
			} else {
				log.info("other flavor {}", t.getTransferDataFlavors());
			}
		} catch (UnsupportedFlavorException | IOException e) {
			log.error(null, e);
		}
	}
}
