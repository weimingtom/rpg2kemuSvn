import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Vector;
import java.util.Properties;
import rpg2k.*;
import rpg2k.sprite.*;
import static java.lang.System.*;
import static rpg2k.analyze.Format.*;

public class Controller extends Applet
	implements Field, Runnable, ModeListener
{
	// アプレットのとき
	public Controller() {}
	public void init() {
		IS_APPLET = true;
//		this.enableEvents(AWTEvent.KEY_EVENT_MASK);
		// オプションの読み込み
		loadParameters();
		PRINT_X = 0; PRINT_Y = 0;
		// モデルの初期化
		GAME_DATA = new Model(this);
		// ビューの初期化
		VIEW = new View(this);
		SPRITE = VIEW.SPRITE;
		SPRITE.setModeListener(this);
		// イベント処理機構の初期化
		PROCESSER = new EventProcesser(SPRITE, GAME_DATA);
		// スレッドの開始！
		new Thread(this, GAME_DATA.GAME_NAME).start();
	}
	public void start() {
	}
	public void stop() {
	}
	public void destroy() {
	}
	protected void loadParameters() {
		try {
			ALWAYS_ON_TOP =
				getParameter(PARAM_ALWAYS).equalsIgnoreCase(VAL_TRUE);
		} catch(Exception e) {}
		try {
			FULL_SCREEN =
				getParameter(PARAM_FULL).equalsIgnoreCase(VAL_TRUE);
		} catch(Exception e) {}
		try {
			MAGNIFICATION =
				getParameter(PARAM_SMALL).equalsIgnoreCase(VAL_TRUE) ? 1 : 2;
		} catch(Exception e) {}
		try {
			TEST_PLAYING =
				getParameter(PARAM_TEST).equalsIgnoreCase(VAL_TRUE);
		} catch(Exception e) {}
		try {
			HIDE_TITLE =
				getParameter(PARAM_TITLE).equalsIgnoreCase(VAL_TRUE);
		} catch(Exception e) {}
		try {
			RTP_DIRECTORY = new URL(getParameter(PARAM_RTP));
		} catch(Exception e) {}
		try {
			GAME_DIRECTORY = new URL(getParameter(PARAM_GAME));
		} catch(Exception e) {}
	}
	
	// アプリケーションのとき
	public static void main(String[] args) {
		new Controller(args);
	}
	public Controller(String[] arg) {
		IS_APPLET = false;
		// オプションの読み込み
		loadProperties();
		FRAME = new Frame(GAME_DATA.GAME_NAME);
		FRAME.setBackground(Color.black);
		FRAME.pack();
		FRAME.setVisible(true);
		FRAME.setVisible(false);
		FRAME.pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		FRAME.setLocation((d.width-SCREEN_W)/2, (d.height-SCREEN_H)/2);
		int left, right, top, bottom;
		left = FRAME.getInsets().left;
		right = FRAME.getInsets().right;
		top = FRAME.getInsets().top;
		bottom = FRAME.getInsets().bottom;
		FRAME.setSize(SCREEN_W + left + right, SCREEN_H + top + bottom);
		FRAME.setResizable(false);
		FRAME.add(this);
		if(left != FRAME.getInsets().left
			|| right != FRAME.getInsets().right
			|| top != FRAME.getInsets().top
			|| bottom != FRAME.getInsets().bottom
		) {
			left = FRAME.getInsets().left;
			right = FRAME.getInsets().right;
			top = FRAME.getInsets().top;
			bottom = FRAME.getInsets().bottom;
			FRAME.setSize(SCREEN_W + left + right, SCREEN_H + top + bottom);
		}
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
			new BufferedImage(1 , 1, IMAGE_TYPE), new Point(), "");
		FRAME.setCursor(cursor);
		PRINT_X = left;
		PRINT_Y = top;
		FRAME.setVisible(true);
		switch(arg.length) {
			case 5: MAGNIFICATION
				= arg[4].equalsIgnoreCase(PARAM_SMALL) ? 1 : 2;
			case 4: ALWAYS_ON_TOP
				= arg[3].equalsIgnoreCase(PARAM_ALWAYS);
			case 3: FULL_SCREEN
				= arg[2].equalsIgnoreCase(PARAM_FULL);
			case 2: HIDE_TITLE
				= arg[1].equalsIgnoreCase(PARAM_TITLE);
			case 1: TEST_PLAYING
				= arg[0].equalsIgnoreCase(PARAM_TEST);
		}
		// モデルの初期化
		GAME_DATA = new Model(this);
		// ビューの初期化
		VIEW = new View(this);
		SPRITE = VIEW.SPRITE;
		SPRITE.setModeListener(this);
		// イベントの処理機構の初期化
		PROCESSER = new EventProcesser(SPRITE, GAME_DATA);
		// スレッド開始！
		this.enableEvents(AWTEvent.KEY_EVENT_MASK);
		new Thread(this, GAME_DATA.GAME_NAME).start();
	}
	protected void loadProperties() {
		try {
			BufferedInputStream file = null;
			try {
				file =
					new BufferedInputStream(new FileInputStream(SETTING_FILE));
			} catch(Exception e) {
				file = new BufferedInputStream(
					new FileInputStream(DEF_SETTING_FILE));
			}
			Properties property = new Properties();
			property.load(file);
			RTP_DIRECTORY = new URL(property.getProperty("RTP_DIRECTORY"));
			GAME_DIRECTORY = new URL(property.getProperty("GAME_DIRECTORY"));
			MAGNIFICATION
				= new Integer(property.getProperty("MAGNIFICATION"));
			ALWAYS_ON_TOP =
				property.getProperty("ALWAYS_ON_TOP").equalsIgnoreCase("true");
			HIDE_TITLE =
				property.getProperty("HIDE_TITLE").equalsIgnoreCase("true");
			TEST_PLAYING =
				property.getProperty("TEST_PLAYING").equalsIgnoreCase("true");
			FULL_SCREEN =
				property.getProperty("FULL_SCREEN").equalsIgnoreCase("true");
			file.close();
		} catch(Exception e) {}
	}
	
	public void processKeyEvent(KeyEvent e) {
		switch(e.getID()) {
			case KeyEvent.KEY_PRESSED:
				VIEW.keyPressed(e);
				return;
			case KeyEvent.KEY_RELEASED:
				VIEW.keyReleased(e);
				return;
			case KeyEvent.KEY_TYPED:
				VIEW.keyTyped(e);
				return;
		}
	}
	public void run() {
		try {
			long time = 0, start = 0, sleep = 0;
			if(GRAPHICS==null) GRAPHICS = this.getGraphics();
			while(true) {
//				start = currentTimeMillis();
				start = nanoTime();
				runGame();
//				time = currentTimeMillis()-start;
				time = nanoTime()-start;
				loopTime++;
				average += time;
//				sleep = SPEED-time;
				sleep = SPEED_NANO - time;
				if(sleep > 0) Thread.sleep(sleep/1000000, (int)sleep%1000000);
				else delayTime++;
			}
		} catch(Exception e) {
			e.printStackTrace();
			exit(1);
		}
	}
	public void activated() {
		SPRITE.pause(false);
	}
	public void deactivated() {
		SPRITE.pause(true);
	}
	protected void runGame() {
		int key = 0;
		while(!GAME_DATA.KEY_BUFF.isEmpty()) {
			key = GAME_DATA.KEY_BUFF.get(0);
			GAME_DATA.KEY_BUFF.remove(0);
			if(!IS_APPLET) if(!VIEW.systemKey(key)) break;
		}
		if(!GAME_DATA.GAME_KEY_BUFF.isEmpty()) {
			SPRITE.processKey(GAME_DATA.GAME_KEY_BUFF.get(0));
			GAME_DATA.GAME_KEY_BUFF.remove(0);
		}
		if(getMode()==GameMode.FIELD) PROCESSER.runEvent();
		GRAPHICS.drawImage(SPRITE.getScreen(), 0, 0,
			SCREEN_W*MAGNIFICATION, SCREEN_H*MAGNIFICATION,
			this);
	}
	protected void setMode(GameMode mode) {
		SPRITE.setMode(mode);
	}
	protected GameMode getMode() {
		return SPRITE.getMode();
	}
	public void modeChanged(GameMode mode) {
//		float f = ((double)average/loopTime)/1000;
		float f = ((float)average/loopTime)/1000000000;
		System.out.println(getMode()+"\t:"
			+decSpace((int)delayTime, 6)
			+" /"+decSpace((int)loopTime, 6)+"; "
			+f+";");
		loopTime = 0; delayTime = 0; average = 0;
	}
	public void quit(GameMode mode) {
		modeChanged(mode);
		if(!IS_APPLET) FRAME.dispatchEvent(
			new WindowEvent(FRAME, WindowEvent.WINDOW_CLOSING));
	}
	public TestPlayKey getTestPlayKey() {
		if(TEST_PLAYING && !GAME_DATA.TEST_PLAY_KEY_BUFF.isEmpty()) {
			TestPlayKey buff = GAME_DATA.TEST_PLAY_KEY_BUFF.get(0);
			GAME_DATA.TEST_PLAY_KEY_BUFF.remove(0);
			return buff;
		} else return TestPlayKey.NULL;
	}
	public boolean isEncountBlockOff() {
		if(TEST_PLAYING) return VIEW.PRESS_CTRL;
		else return false;
	}
	public boolean isInstantShow() {
		if(TEST_PLAYING) return VIEW.PRESS_SHIFT;
		else return false;
	}
	
	// ループあたりの時間(1/60秒)
	protected static final int
		SPEED = 1000/60+1, SPEED_NANO = 1000000000/60+1;
	protected static final String
		SETTING_FILE = "SETTING", DEF_SETTING_FILE = "DEF_SETTING";
	protected String
		PARAM_SMALL = "Smallest",
		PARAM_ALWAYS = "AlwaysOnTop",
		PARAM_FULL = "FullScreen",
		PARAM_TITLE = "HideTitle",
		PARAM_TEST = "TestPlay",
		PARAM_RTP = "RTPDirectory",
		PARAM_GAME = "GameDirectory";
	protected String VAL_TRUE = "true";
	
	protected View VIEW;
	protected Model GAME_DATA;
	protected Sprite SPRITE;
	protected EventProcesser PROCESSER;
	protected Frame FRAME;
	protected Graphics GRAPHICS;
	protected int PRINT_X, PRINT_Y;
	protected boolean IS_APPLET;
	protected boolean ALWAYS_ON_TOP, FULL_SCREEN, TEST_PLAYING, HIDE_TITLE;
	protected int MAGNIFICATION = 2;
	protected URL GAME_DIRECTORY, RTP_DIRECTORY;
	
	private long loopTime = 0, delayTime = 0, average = 0;
}

/*
	アプレット実行用タグ
	<applet code=Controller archive=RPG2k_Emu_J.jar width=320 height=240>
		<param name="Smallest" value="true">
		<param name="AlwaysOnTop" value="true">
		<param name="FullScreen" value="false">
		<param name="HideTitle" value="false">
		<param name="TestPlay" value="false">
		<param name="RTPDirectory" value="file:./RTP/">
		<param name="GameDirectory" value="file:./Game/">
	</applet>
*/
