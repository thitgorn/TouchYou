package touchyou.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;

import touchyou.Command;
import touchyou.gui.add.ComponentMover;
import touchyou.gui.add.ComponentResizer;
import touchyou.util.Controller;

/**
 * 
 * @author Thitiwat Thongbor
 *
 */
public class MobilePanel extends JLayeredPane {

    private MouseAdapter commandMouseAdapter = new CommandMouseAdapter();
    private ComponentMover mover;
    private ComponentResizer resizer;

    /**
     * 
     */
    private static final long serialVersionUID = 3593230878415293635L;

    protected MobilePanel() {
	mover = new ComponentMover();
	mover.setSnapSize(new Dimension(4, 4));
	mover.setDragInsets(new Insets(10, 10, 10, 10));

	resizer = new ComponentResizer();
	resizer.setSnapSize(new Dimension(4, 4));
	this.setOpaque(true);
	setBackground(Color.white);
	setLayout(null); // make it movable , no layout

	/**
	 * pixel phone has 1080 x 1776 resolution.
	 */

	int mobileWidth = 1080 / 3;
	int mobileHeight = 1776 / 3;
	setMobileSize(mobileWidth, mobileHeight);

	// setBorder(new CompoundBorder(GuiUtil.getBorder(), new EmptyBorder(0,
	// sideGap, 0, sideGap)));

    }

    public void setMobileSize(int width, int height) {
	setPreferredSize(new Dimension(width, height));
	this.validate();
    }

    public void addCommand(Command command) {
	JButton commandBtn = new JButton();
	/* Set JButton's behavior */
	commandBtn.setActionCommand(String.valueOf(command.getId()));
	commandBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	commandBtn.setOpaque(true);
	commandBtn.setFocusPainted(false);
//	commandBtn.setContentAreaFilled(false);
	commandBtn.setBackground(new Color(250,250,250));
	commandBtn.setPreferredSize(new Dimension((int) command.getWidth(), (int) command.getHeight()));
	commandBtn.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
	commandBtn.addMouseListener(commandMouseAdapter);
	commandBtn.addComponentListener(new ComponentAdapter() {
	    @Override
	    public void componentResized(ComponentEvent e) {
		Controller.getInstance().updateCurrentCommand();
	    }
	});
	commandBtn.setBounds((int) command.getX(), (int) command.getY(), (int) command.getWidth(),
		(int) command.getHeight());
	commandBtn.setHorizontalTextPosition(SwingConstants.CENTER);
	commandBtn.setFont(new Font("Arial", 0, 24));
	mover.registerComponent(commandBtn);
	resizer.registerComponent(commandBtn);
	command.setWidth(commandBtn.getWidth());
	command.setHeight(commandBtn.getHeight());
	command.setX(commandBtn.getX());
	command.setY(commandBtn.getY());
	this.add(commandBtn, new Integer(command.getId()));
	Controller.getInstance().update(command);
    }

    public void update(Command command) {
	int id = command.getId();
	Component[] comps = getComponents();
	for (Component c : comps) {
	    JButton button = (JButton) c;
	    button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	    if (Integer.parseInt(button.getActionCommand()) == id) {
		button.setBorder(BorderFactory.createLineBorder(Color.red, 2));
		button.setIcon(new ImageIcon(command.getImage().getScaledInstance(button.getWidth(), button.getHeight(),
			Image.SCALE_SMOOTH)));
		button.setText(command.getLabel());
	    }
	}
    }

    private final class CommandMouseAdapter extends MouseAdapter {
	@Override
	public void mousePressed(MouseEvent e) {
	    JButton source = (JButton) e.getSource();
	    String id = source.getActionCommand();
	    Controller.getInstance().update(Controller.getInstance().getCommandById(id));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	    JButton source = (JButton) e.getSource();
	    Controller.getInstance().getCurrentCommand().setWidth(source.getWidth());
	    Controller.getInstance().getCurrentCommand().setHeight(source.getHeight());
	    Controller.getInstance().getCurrentCommand().setX(source.getX());
	    Controller.getInstance().getCurrentCommand().setY(source.getY());
	}
    }

    public void clear() {
	this.removeAll();
	this.repaint();
    }

    public void removeCommand(Command currentCommand) {
	int id = currentCommand.getId();
	Component[] comps = getComponents();
	for (Component com : comps) {
	    JButton button = (JButton) com;
	    if (button.getActionCommand().equals(String.valueOf(id))) {
		this.remove(button);
	    }
	}
	this.repaint();
    }
}