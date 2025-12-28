package com.iconiux.ezrfs.gui.clipboard;// Source - https://stackoverflow.com/a
// Posted by AdrZ
// Retrieved 2025-12-22, License - CC BY-SA 3.0

import java.awt.*;
import java.awt.datatransfer.*;

public class NewClass implements FlavorListener, ClipboardOwner {
	private Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
	public NewClass() {
		System.out.println("NewClass constructor");
		clip.setContents(clip.getContents(null), this);
		clip.addFlavorListener(this);
		try {
			Thread.sleep(100000L);
		}
		catch (InterruptedException e) {

		}
	}

	@Override
	public void flavorsChanged(FlavorEvent e) {
		System.out.println("ClipBoard Changed!!!");
		clip.removeFlavorListener(this);
		clip.setContents(clip.getContents(null), this);
		clip.addFlavorListener(this);

	}

	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
		System.out.println("ownership losted");
	}
}
