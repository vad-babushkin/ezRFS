package com.iconiux.ezrfs.gui.clipboard;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

class BoardListener extends Thread implements ClipboardOwner {
	Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();

	public void run() {
		Transferable trans = sysClip.getContents(this);
		regainOwnership(trans);
		System.out.println("Listening to board...");
		while(true) {}
	}

	public void lostOwnership(Clipboard c, Transferable t) {
		Transferable contents = sysClip.getContents(this); //EXCEPTION
		processContents(contents);
		regainOwnership(contents);
	}

	void processContents(Transferable t) {
		System.out.println("Processing: " + t);
		try {
			String what = (String) (t.getTransferData(DataFlavor.stringFlavor));
			System.out.println("what: " + what);

		} catch (UnsupportedFlavorException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	void regainOwnership(Transferable t) {
		sysClip.setContents(t, this);
	}

	public static void main(String[] args) {
		BoardListener b = new BoardListener();
		b.start();
	}
}
