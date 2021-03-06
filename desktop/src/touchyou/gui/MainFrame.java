package touchyou.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

import touchyou.util.Controller;
import touchyou.util.GUIUtil;

/**
 * 
 * @author Thitiwat Thongbor
 *
 */
public class MainFrame extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -8718047311675995150L;

    // get the width and height from Util class.
    private final int Width = GUIUtil.WIDTH;
    private final int Height = GUIUtil.HEIGHT;

    /**
     * construct MainFrame.
     */
    public MainFrame() {
	addMouseListener(new MouseAdapter() {
	    @Override
	    public void mousePressed(MouseEvent e) {
		requestFocus();
	    }
	});
	setTitle("Remec " + GUIUtil.version);
	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	setSize(1004, 635);
	setJMenuBar(new MenuBar());

	JPanel workingPanel = new JPanel();
	workingPanel.setBackground(GUIUtil.getBackgroundColor());

	int model_width = Width * 5 / 10;
	int pane_width = Width * 2 / 10;
	GridBagLayout gbl_workingPanel = new GridBagLayout();
	gbl_workingPanel.columnWidths = new int[] {272, 580, 203, 0};
	gbl_workingPanel.rowHeights = new int[] {610, 0};
	gbl_workingPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
	gbl_workingPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
	workingPanel.setLayout(gbl_workingPanel);

	SettingPanel settingPanel = new SettingPanel();
	settingPanel.setMinimumSize(new Dimension(200, 227));
	GridBagConstraints gbc_settingPanel = new GridBagConstraints();
	gbc_settingPanel.fill = GridBagConstraints.BOTH;
	gbc_settingPanel.insets = new Insets(5, 5, 0, 5);
	gbc_settingPanel.gridx = 0;
	gbc_settingPanel.gridy = 0;
	workingPanel.add(settingPanel, gbc_settingPanel);
	getContentPane().add(workingPanel);
	JPanel mobilePadding = new JPanel();
	mobilePadding.setPreferredSize(new Dimension(model_width, Height));
	// int sideGap = (int) ((GuiUtil.HEIGHT - mobileWidth) / 2.0);
	mobilePadding.setBorder(new EmptyBorder(0, 0, 0, 0));
	mobilePadding.setBackground(Color.decode("#282828")); // almost black;
	mobilePadding.setOpaque(true);

	MobilePanel mobilePanel = new MobilePanel();
	mobilePadding.add(mobilePanel);
	GridBagConstraints gbc_mobilePadding = new GridBagConstraints();
	gbc_mobilePadding.insets = new Insets(0, 0, 0, 0);
	gbc_mobilePadding.fill = GridBagConstraints.BOTH;
	gbc_mobilePadding.gridx = 1;
	gbc_mobilePadding.gridy = 0;
	workingPanel.add(mobilePadding, gbc_mobilePadding);

	WidgetPanel widgetPanel = new WidgetPanel(pane_width, Height);
	GridBagConstraints gbc_widgetPanel = new GridBagConstraints();
	gbc_widgetPanel.fill = GridBagConstraints.BOTH;
	gbc_widgetPanel.gridx = 2;
	gbc_widgetPanel.gridy = 0;
	workingPanel.add(widgetPanel, gbc_widgetPanel);

	StatusPanel statusPanel = new StatusPanel();
	statusPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(128, 128, 128), null, null, null));
	GridBagLayout gridBagLayout = (GridBagLayout) statusPanel.getLayout();
	gridBagLayout.columnWeights = new double[] { 0.0, 1.0 };
	getContentPane().add(statusPanel, BorderLayout.SOUTH);

	Controller controller = Controller.getInstance();
	controller.setMainFrame(this);
	controller.setMobilePanel(mobilePanel);
	controller.setSettingPanel(settingPanel);
	controller.setWidgetPanel(widgetPanel);
	controller.setStatusPanel(statusPanel);

	this.addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent e) {
		if (!Controller.getInstance().getIsSave()) {
		    Object[] options = { "Yes", "No" };
		    int result = JOptionPane.showOptionDialog(null, "Do you want to save before exit?", "Exit Remec",
			    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
		    if (result == JOptionPane.OK_OPTION) {
			Controller.getInstance().save();
		    }
		    if (result == JOptionPane.CLOSED_OPTION) {
			return;
		    }
		    System.exit(0);
		} else
		    System.exit(0);
	    }
	});
	this.pack();
	this.setMinimumSize(this.getSize());
	statusPanel.requestFocusInWindow();
	setLocationRelativeTo(null);
	controller.clear();
	controller.loadProfile();
    }

}