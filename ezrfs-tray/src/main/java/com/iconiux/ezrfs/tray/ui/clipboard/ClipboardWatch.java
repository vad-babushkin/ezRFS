package com.iconiux.ezrfs.tray.ui.clipboard;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

@Slf4j
public class ClipboardWatch implements FlavorListener, ClipboardOwner {
	public final static Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();

	static final DataFlavor PICT = DataFlavor.imageFlavor;
	static final DataFlavor LIST = DataFlavor.javaFileListFlavor;
	static final DataFlavor TEXT = DataFlavor.stringFlavor;

	String htm, txt;
	Transferable data; //clipboard contents -- not used
	String[] flavors;

	public ClipboardWatch() {
		clip.addFlavorListener(this);
	}

	public void flavorsChanged(FlavorEvent e) {
		log.info("flavorsChanged");
		Transferable t = clip.getContents(null);
		if (t != null) {
			log.info("Change Owner ");
			clip.setContents(t, this);
			pasteData(t);
		}
	}

	public void lostOwnership(Clipboard cb, Transferable t) {
		log.info("lostOwnership ");
	}

	public static Object getData(Transferable t, DataFlavor f) {
		try {
			//if (t.isDataFlavorSupported(f))
			return t.getTransferData(f);
		} catch (Exception x) { //UnsupportedFlavorException
			return null;
		}
	}

	public boolean pasteData(Transferable t) {
		DataFlavor[] a = t.getTransferDataFlavors();
		String s = a.length + " flavors";  //\n";
		System.out.println("pasteData "+s);
		data = t; //save for inspection -- not used
		txt = (String) getData(t, TEXT);
		log.info("text {}", txt);
		return true;
	}
}
