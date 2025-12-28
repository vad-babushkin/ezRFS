package com.iconiux.ezrfs.tray.ui.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InfiniteProgressPanel extends JComponent implements MouseListener {
	/**
	 * Notifies whether the animation is running or not.
	 */
	protected boolean started = false;
	/**
	 * Alpha level of the veil, used for fade in/out.
	 */
	protected int alphaLevel = 0;
	/**
	 * Alpha level of the veil.
	 */
	protected float shield = 0.70f;
	/**
	 * Rendering hints to set anti aliasing.
	 */
	protected RenderingHints hints = null;

	private JLabel processLabel;

	/**
	 * Creates a new progress panel with default values:<br /> <ul> <li>No message</li> <li>14 bars</li> <li>Veil's
	 * alpha level is 70%</li> <li>15 frames per second</li> <li>Fade in/out last 300 ms</li> </ul>
	 */
	public InfiniteProgressPanel() {
		initComponents();
		this.shield = shield >= 0.0f ? shield : 0.0f;
		this.hints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		this.hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.hints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}

	/**
	 * Starts the waiting animation by fading the veil in, then rotating the shapes. This method handles the visibility
	 * of the glass pane.
	 */
	synchronized public void start() {
		addMouseListener(this);
		setVisible(true);
	}

	/**
	 * Stops the waiting animation by stopping the rotation of the circular shape and then by fading out the veil. This
	 * methods sets the panel invisible at the end.
	 */
	synchronized public void stop() {
		removeMouseListener(this);
		setVisible(false);
	}

	public JComponent getComponent() {
		return this;
	}


	public void paintComponent(Graphics g) {
		if (started) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHints(hints);

			g2.setColor(new Color(255, 255, 255, (int) (alphaLevel * shield)));
			g2.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	private void initComponents() {
		processLabel = new JLabel();
		setLayout(new BorderLayout());
		processLabel.setIcon(new ImageIcon(getClass().getResource("/img/indicator_waitanim.gif")));
		processLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(processLabel, BorderLayout.CENTER);
	}
}
