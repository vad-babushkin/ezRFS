//package com.iconiux.ezrfs.gui.clipboard;
//
//// Source - https://stackoverflow.com/a
//// Posted by Jananath Banuka
//// Retrieved 2025-12-22, License - CC BY-SA 3.0
//
//import java.awt.AWTEvent;
//import java.awt.EventQueue;
//import java.awt.Toolkit;
//import java.awt.datatransfer.Clipboard;
//import java.awt.datatransfer.ClipboardOwner;
//import java.awt.datatransfer.DataFlavor;
//import java.awt.datatransfer.StringSelection;
//import java.awt.datatransfer.Transferable;
//import java.awt.datatransfer.UnsupportedFlavorException;
//import java.awt.event.ActionEvent;
//import java.awt.event.InputEvent;
//import java.io.IOException;
//import java.util.Observable;
//import java.util.Observer;
//
//import javax.swing.JFrame;
//
//public final class ClipboardMonitor extends Observable implements ClipboardOwner {
//	private static ClipboardMonitor monitor = null;
//
//	public ClipboardMonitor() {
//		gainOwnership();
//	}
//
//	private void gainOwnership() {
//		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
//		try {
//			Transferable content = clip.getContents(null);
//			DataFlavor[] f = content.getTransferDataFlavors();
//			boolean imageDetected = false;
//			for (int i = 0; i < f.length; i++) {
//				//                        System.out.println("Name: " + f[i].getHumanPresentableName());
//				//                        System.out.println("MimeType: " + f[i].getMimeType());
//				//                        System.out.println("PrimaryType: " + f[i].getPrimaryType());
//				//                        System.out.println("SubType: " + f[i].getSubType());
//				if (f[i].equals(DataFlavor.imageFlavor)) {
//					imageDetected = true;
//					break;
//				}
//			}
//			if (imageDetected) {
//				System.out.println("Image content detected");
//				Transferable t = new Transferable() {
//					public DataFlavor[] getTransferDataFlavors() {
//						return new DataFlavor[]{DataFlavor.stringFlavor};
//					}
//
//					public boolean isDataFlavorSupported(DataFlavor flavor) {
//						return false;
//					}
//
//					public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
//						return "dummy text instead of snapshot image";
//					}
//				};
//				clip.setContents(t, this);
//			} else {
//				clip.setContents(content, this);
//			}
//			setChanged();
//			notifyObservers(content);
//		} catch (IllegalArgumentException istateexception) {
//			istateexception.printStackTrace();
//		} catch (Exception ioexception) {
//			ioexception.printStackTrace();
//		}
//	}
//
//	private int getCurrentEventModifiers() {
//		int modifiers = 0;
//		AWTEvent currentEvent = EventQueue.getCurrentEvent();
//		if (currentEvent instanceof InputEvent) {
//			modifiers = ((InputEvent) currentEvent).getModifiers();
//		} else if (currentEvent instanceof ActionEvent) {
//			modifiers = ((ActionEvent) currentEvent).getModifiers();
//		}
//		return modifiers;
//	}
//
//	public void lostOwnership(Clipboard clipboard, Transferable contents) {
//		System.out.println("Ownership lost ...");
//		new Thread(new Runnable() {
//			public void run() {
//				try {
//					Thread.sleep(200);
//					gainOwnership();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	public void flushClipboard() {
//		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(""), null);
//	}
//
//	public static final ClipboardMonitor getMonitor() {
//		if (monitor == null)
//			monitor = new ClipboardMonitor();
//		return (monitor);
//	}
//
//	public static void main(String[] args) {
//		JFrame f = new JFrame();
//		ClipboardMonitor monitor = ClipboardMonitor.getMonitor();
//		monitor.addObserver(new Observer() {
//			public void update(Observable o, Object arg) {
//				System.out.println("Clipboard has been regained!");
//			}
//		});
//
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.setSize(500, 100);
//		f.setVisible(true);
//	}
//}
