package touchyou.gui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import touchyou.gui.add.MouseOver;
import touchyou.util.Controller;

/**
 * Let's do the action in this Class.
 * 
 * @author Thitiwat Thongbor
 *
 */
public class SyncButton extends JButton {

	/**
	 * serial ID
	 */
	private static final long serialVersionUID = 2648304339901249894L;

	/**
	 * Construct Sync Button
	 * 
	 * @param width
	 * @param height
	 */
	public SyncButton(int width, int height) {
		super("SYNC");
		this.setPreferredSize(new Dimension(width, height));
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		this.setFont(new Font(this.getFont().getFontName(), 0, 24));
		this.addActionListener((e) -> Controller.getInstance().sendSyncRequest());
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setOpaque(true);
		this.addMouseListener(new MouseOver(this));
	}
}
