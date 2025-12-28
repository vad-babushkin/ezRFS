package com.iconiux.ezrfs.tray.ui.panel;


import org.jdesktop.swingx.JXPanel;

import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;

/**
 * <p>
 * A panel that draws an image. The standard mode is to draw the specified image
 * centered and unscaled. The component&amp;s preferred size is based on the
 * image, unless explicitly set by the user.
 * </p>
 *
 * @author rbair
 */
public class CustomImagePanel extends JXPanel {

	/**
	 * The image to draw
	 */
	private Image img = null;

	/**
	 * Sets the image to use for the background of this panel. This image is
	 * painted whether the panel is opaque or translucent.
	 *
	 * @param image if null, clears the image. Otherwise, this will set the
	 *              image to be painted. If the preferred size has not been explicitly
	 *              set, then the image dimensions will alter the preferred size of
	 *              the panel.
	 */
	void setImage(Image image) {
		if (image != img) {
			Image oldImage = img;
			img = image;
			firePropertyChange("image", oldImage, img);
			invalidate();
			repaint();
		}
	}

	/**
	 * @return the image used for painting the background of this panel
	 */
	Image getImage() {
		return img;
	}

	/**
	 * {@inheritDoc}
	 * The old property value in PCE fired by this method might not be always correct!
	 */
	@Override
	public Dimension getPreferredSize() {
		if (!isPreferredSizeSet() && img != null) {
			// it has not been explicitly set, so return the width/height of
			// the image
			int width = img.getWidth(null);
			int height = img.getHeight(null);
			if (width == -1 || height == -1) {
				return super.getPreferredSize();
			}
			Insets insets = getInsets();
			width += insets.left + insets.right;
			height += insets.top + insets.bottom;
			return new Dimension(width, height);
		}
		return super.getPreferredSize();
	}

	/**
	 * Overridden to paint the image on the panel
	 *
	 * @param g
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (img == null)
			return;
		int imgWidth = img.getWidth(null);
		int imgHeight = img.getHeight(null);
		if (imgWidth == -1 || imgHeight == -1) {
			// image hasn't completed loading, return
			return;
		}

		Insets insets = getInsets();
		int pw = getWidth() - insets.left - insets.right;
		int ph = getHeight() - insets.top - insets.bottom;

		Rectangle clipRect = g2.getClipBounds();
		int imageX = (pw - imgWidth) / 2 + insets.left;
		int imageY = (ph - imgHeight) / 2 + insets.top;
		Rectangle r = SwingUtilities.computeIntersection(imageX, imageY, imgWidth, imgHeight, clipRect);
		if (r.x == 0 && r.y == 0 && (r.width == 0 || r.height == 0))
			return;
		// since I have the intersection, all I need to do is adjust the
		// x & y values for the image
		int txClipX = clipRect.x - imageX;
		int txClipY = clipRect.y - imageY;
		int txClipW = clipRect.width;
		int txClipH = clipRect.height;

		g2.drawImage(
			img, clipRect.x, clipRect.y, clipRect.x + clipRect.width, clipRect.y + clipRect.height,
			txClipX, txClipY, txClipX + txClipW, txClipY + txClipH, null
		);
	}
}
