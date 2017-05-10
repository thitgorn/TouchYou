package touchyou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import touchyou.gui.WelcomeFrame;

/**
 * Touchyou model.
 * 
 * @author Kongpon Charanwattanakit
 *
 */
public class App {
    public static int PORT = 3000;
    public TCPServer server;
    public Profile profile = new Profile("Demo");

    /**
     * Initialize TouchYou server.
     */
    public App() {
	server = new TCPServer(PORT);
	System.out.println("Server Running on port: "+ PORT);
    }

    /**
     * Transfer profile data to mobile device.
     */
    public void sync() {
	// TODO sync commands to mobile device
	server.sendToAllClients("sync request");
	server.sendToAllClients(profile);
    }

    /**
     * Save profile data to .profile file.
     * 
     * @param path
     */
    public void save(String path) {
	// TODO write data to .profile file
	// TODO finished without testing
	try {
	    PrintWriter writer = null;
	    try {
		File file = new File(path);
		writer = new PrintWriter(file);
		writer.println("name=" + profile.getName());
		for (Command command : profile.getCommands()) {
		    writer.println("id=" + command.getId());
		    writer.println("com=" + command.getCombination());
		    writer.println("mo=" + command.getMode());
		    writer.println("img" + command.getImagePath());
		    writer.println("w=" + command.getWidth());
		    writer.println("h=" + command.getHeight());
		    writer.println("x=" + command.getX());
		    writer.println("y=" + command.getY());
		}
	    } finally {
		writer.close();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Set profile to a given .profile file path.
     * 
     * @param path
     *            is the .profile file path
     */
    public void open(String path) {
	profile = generateProfile(new File(path));
    }

    /**
     * Set profile to a given .profile file.
     * 
     * @param file
     *            is the .profile file
     */
    public void open(File file) {
	profile = generateProfile(file);
    }

    /**
     * Return Profile object from .profile file.
     * 
     * @param file
     *            is the .profile file
     * @return a Profile instance
     */
    private Profile generateProfile(File file) {
	// TODO generate Profile object from .profile file
	// TODO finished without testing
	BufferedReader reader = null;
	try {
	    try {
		CommandSetter[] methods = { (c, l) -> c.setId(Integer.parseInt(l)), (c, l) -> c.setCombination(l),
			(c, l) -> c.setMode(Integer.parseInt(l)), (c, l) -> c.setImagePath(l),
			(c, l) -> c.setWidth(Double.parseDouble(l)), (c, l) -> c.setHeight(Double.parseDouble(l)),
			(c, l) -> c.setX(Double.parseDouble(l)), (c, l) -> c.setY(Double.parseDouble(l)) };

		reader = new BufferedReader(new FileReader(file));
		Profile profile = new Profile(reader.readLine());
		String line;
		Command command = new Command();
		for (int i = 0; (line = reader.readLine()) != null; i++) {
		    methods[i].run(command, line);
		    if (i >= 7) {
			i = 0;
			profile.addCommand(command);
			command = new Command();
		    }
		}
	    } finally {
		reader.close();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return profile;
    }

    /**
     * Returns current profile.
     * 
     * @return profile instance
     */
    public Profile getProfile() {
	return profile;
    }

    /**
     * Start listening on the server's port.
     */
    private void run() {
	try {
	    server.listen();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Main method.
     * 
     * @param args
     *            is not used.
     */
    public static void main(String[] args) {
	try {
	    System.setProperty("apple.laf.useScreenMenuBar", "true");
	    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Test");
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
		| UnsupportedLookAndFeelException e) {
	    e.printStackTrace();
	}

	App app = new App();
	app.run();
	new WelcomeFrame(app).setVisible(true);
    }
}

/**
 * Helper interface for generating Command objects.
 * 
 * @author Kongpon Charanwattanakit
 *
 */
interface CommandSetter {
    /**
     * Manage the command with a given data.
     * 
     * @param c
     *            is the command to work with
     * @param line
     *            is the data to be used
     */
    public void run(Command c, String line);
}
