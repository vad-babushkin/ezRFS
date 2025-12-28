package com.iconiux.ezrfs.tray.ui.icon;

import com.jgoodies.uif.util.NullIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * CompositeIcon is an Icon implementation which draws two icons with a specified relative position:
 * LEFT, RIGHT, TOP, BOTTOM specify how icon1 is drawn relative to icon2
 * CENTER: icon1 is drawn first, icon2 is drawn over it
 * <p/>
 * and with horizontal and vertical orientations within the alloted space
 * <p/>
 * It's useful with VTextIcon when you want an icon with your text:
 * if icon1 is the graphic icon and icon2 is the VTextIcon, you get a similar effect
 * to a JLabel with a graphic icon and text
 */
public class CompositeIcon implements Icon, SwingConstants {
	Icon firstIcon, secondIcon;
	int position, horizontalOrientation, verticalOrientation;

	public CompositeIcon() {
		this(new NullIcon(16, 16), new NullIcon(16, 16), CENTER);
	}

	/**
	 * Create a CompositeIcon from the specified Icons,
	 * using the default relative position (icon1 above icon2)
	 * and orientations (centered horizontally and vertically)
	 */
	public CompositeIcon(Icon firstIcon, Icon secondIcon) {
		this(firstIcon, secondIcon, CENTER);
	}

	/**
	 * Create a CompositeIcon from the specified Icons,
	 * using the specified relative position
	 * and default orientations (centered horizontally and vertically)
	 */
	public CompositeIcon(Icon firstIcon, Icon secondIcon, int position) {
		this(firstIcon, secondIcon, position, RIGHT, BOTTOM);
	}

	/**
	 * Create a CompositeIcon from the specified Icons,
	 * using the specified relative position
	 * and orientations
	 */
	public CompositeIcon(Icon firstIcon, Icon secondIcon, int position, int horizontalOrientation,
	                     int verticalOrientation) {
		this.firstIcon = firstIcon;
		this.secondIcon = secondIcon;
		this.position = position;
		this.horizontalOrientation = horizontalOrientation;
		this.verticalOrientation = verticalOrientation;
	}

	/**
	 * Draw the icon at the specified location.  Icon implementations
	 * may use the Component argument to get properties useful for
	 * painting, e.g. the foreground or background color.
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) {
		int width = getIconWidth();
		int height = getIconHeight();
		if (position == LEFT || position == RIGHT) {
			Icon leftIcon, rightIcon;
			if (position == LEFT) {
				leftIcon = firstIcon;
				rightIcon = secondIcon;
			} else {
				leftIcon = secondIcon;
				rightIcon = firstIcon;
			}
			// "Left" orientation, because we specify the x position
			paintIcon(c, g, leftIcon, x, y, width, height, LEFT, verticalOrientation);
			paintIcon(c, g, rightIcon, x + leftIcon.getIconWidth(), y, width, height, LEFT, verticalOrientation);
		} else if (position == TOP || position == BOTTOM) {
			Icon topIcon, bottomIcon;
			if (position == TOP) {
				topIcon = firstIcon;
				bottomIcon = secondIcon;
			} else {
				topIcon = secondIcon;
				bottomIcon = firstIcon;
			}
			// "Top" orientation, because we specify the y position
			paintIcon(c, g, topIcon, x, y, width, height, horizontalOrientation, TOP);
			paintIcon(c, g, bottomIcon, x, y + topIcon.getIconHeight(), width, height, horizontalOrientation, TOP);
		} else {
			paintIcon(c, g, firstIcon, x, y, width, height, horizontalOrientation, verticalOrientation);
			paintIcon(c, g, secondIcon, x, y, width, height, horizontalOrientation, verticalOrientation);
		}
	}

	/* Paints one icon in the specified rectangle with the given orientations
	 */
	void paintIcon(Component c, Graphics g, Icon icon, int x, int y, int width, int height,
	               int horizontalOrientation, int verticalOrientation) {

		int xIcon, yIcon;
		switch (horizontalOrientation) {
			case LEFT:
				xIcon = x;
				break;
			case RIGHT:
				xIcon = x + width - icon.getIconWidth();
				break;
			default:
				xIcon = x + (width - icon.getIconWidth()) / 2;
				break;
		}
		switch (verticalOrientation) {
			case TOP:
				yIcon = y;
				break;
			case BOTTOM:
				yIcon = y + height - icon.getIconHeight();
				break;
			default:
				yIcon = y + (height - icon.getIconHeight()) / 2;
				break;
		}
		icon.paintIcon(c, g, xIcon, yIcon);
	}

	/**
	 * Returns the icon's width.
	 *
	 * @return an int specifying the fixed width of the icon.
	 */
	public int getIconWidth() {
		if (position == LEFT || position == RIGHT) {
			// обходим вариант неподачи иконок
			if (firstIcon == null || secondIcon == null) {
				return 32;
			} else {
				return firstIcon.getIconWidth() + secondIcon.getIconWidth();
			}
		}

		return Math.max(firstIcon.getIconWidth(), secondIcon.getIconWidth());
	}

	/**
	 * Returns the icon's height.
	 *
	 * @return an int specifying the fixed height of the icon.
	 */
	public int getIconHeight() {
		if (position == TOP || position == BOTTOM) {
			if (firstIcon == null || secondIcon == null) {
				return 32;
			} else {
				return firstIcon.getIconHeight() + secondIcon.getIconHeight();
			}
		}
		return Math.max(firstIcon.getIconHeight(), secondIcon.getIconHeight());
	}

	public Icon getFirstIcon() {
		return firstIcon;
	}

	public void setFirstIcon(Icon firstIcon) {
		this.firstIcon = firstIcon;
	}

	public int getHorizontalOrientation() {
		return horizontalOrientation;
	}

	public void setHorizontalOrientation(int horizontalOrientation) {
		this.horizontalOrientation = horizontalOrientation;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Icon getSecondIcon() {
		return secondIcon;
	}

	public void setSecondIcon(Icon secondIcon) {
		this.secondIcon = secondIcon;
	}

	public int getVerticalOrientation() {
		return verticalOrientation;
	}

	public void setVerticalOrientation(int verticalOrientation) {
		this.verticalOrientation = verticalOrientation;
	}

	public Image getImage() {
		// new RGB image with transparency channel
		BufferedImage image = new BufferedImage(firstIcon.getIconWidth(), firstIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

		// create new graphics and set anti-aliasing hint
		Graphics2D graphics = (Graphics2D) image.getGraphics().create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.paintIcon(null, graphics, 0, 0);
		return image;
	}

	public static Image createImage(Icon icon) {
		if (icon instanceof ImageIcon) {
			return ((ImageIcon) icon).getImage();
		} else {
			BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			// create new graphics and set anti-aliasing hint
			Graphics2D graphics = (Graphics2D) image.getGraphics().create();
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			icon.paintIcon(null, graphics, 0, 0);
			return image;
		}
	}
}