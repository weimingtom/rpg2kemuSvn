import java.applet.Applet;
import java.awt.*;

public class Controller
	extends Applet
	implements Runnable
{
	// fields
	private View VIEW;
	private Model MODEL;
	private Container CONTAINER;
	private Thread thisThread;
	private Graphics GRAPHICS;
	// constructers
	public Controller() {
		CONTAINER = new RPG2kFrame();
		thisThread = new Thread(this);
	}
	// thread
	public void run() {
	}
	// override of Applet class methods
	public void init() {
		CONTAINER = this;
		thisThread.start();
	}
	public void start() {
	}
	public void stop() {
	}
	public void destroy() {
	}
	// methods
	public boolean isApplet() {
		return (CONTAINER instanceof Applet);
	}
	public void drawScreen(Image img) {
	}
	public void 
	// main method
	public static void main(String[] args) {
		new this();
		thisThread.start();
	}
}

/*
	tag for running with Applet
	<applet code=Controller archive=RPG2k_Emu_J.jar width=320 height=240>
		<param name="SMALLEST" value="true">
		<param name="ALWAYS_ON_TOP" value="true">
		<param name="FULL_SCREEN" value="false">
		<param name="HIDE_TITLE" value="false">
		<param name="TEST_PLAY" value="false">
		<param name="RTP_DIRECTORY" value="./RTP/">
		<param name="GAME_DIRECTORY" value="./Game/">
	</applet>
*/
