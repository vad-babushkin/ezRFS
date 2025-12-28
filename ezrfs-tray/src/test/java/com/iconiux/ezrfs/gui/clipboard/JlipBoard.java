package com.iconiux.ezrfs.gui.clipboard;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

class JlipBoard extends Thread implements ClipboardOwner {
	Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
	boolean bEnough = false;

	public void run() {
		Transferable trans = sysClip.getContents(this);
		regainOwnership(trans);
		System.out.println("Listening to board...");
		while (true) {
			if (isitEnough()) break;
		}
		System.out.println("No more Listening...");
	}

	public void itisEnough() {
		bEnough = true;
	}

	public void itisNotEnough() {
		bEnough = false;
	}

	boolean isitEnough() {
		return bEnough;
	}

	public void lostOwnership(Clipboard c, Transferable t) {
		try {
			sleep(200);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		try {
			Transferable contents = c.getContents(this); //EXCEPTION
			//processContents(contents);
			regainOwnership(contents);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void processContents(Transferable t) {
		if (isitEnough()) return;
		DataFlavor[] flavors = t.getTransferDataFlavors();
		for (int i = flavors.length - 1; i >= 0; i--) {
			try {
				Object o = t.getTransferData(flavors[i]);
				//System.out.println("Flavor "+i+" gives "+o.getClass().getName());
				if (o instanceof String) {
					///System.out.println("String="+(String)o);
					System.out.println(((String) o).toLowerCase());
					break;
				}
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}
		///System.out.println("Processing: ");
	}

	void regainOwnership(Transferable t) {
		sysClip.setContents(t, this);
		processContents(t);
	}

	public static void main(String[] args) {
		JlipBoard b = new JlipBoard();
		b.itisNotEnough();
		b.start();
	}
}