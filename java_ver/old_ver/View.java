import java.awt.*;
import java.awt.event.*;
import java.util.Stack;
import rpg2k.*;
import rpg2k.database.*;
import rpg2k.sprite.Sprite;
import static java.lang.System.*;

public class View implements Field, KeyListener, WindowListener, Runnable
{
	protected Sprite SPRITE;
	private Model GAME_DATA;
	private Controller owner;
	protected boolean
		PRESS_LEFT, PRESS_RIGHT, PRESS_UP, PRESS_DOWN;
	protected boolean PRESS_F12;
	protected boolean PRESS_ALT, PRESS_CTRL, PRESS_SHIFT;
	protected boolean ALWAYS_ON_TOP_SUPPORTED;
	private Thread thisThread;
	private static final int REPEAT = 100;
	
	protected View(Controller c) {
		owner = c;
		GAME_DATA = owner.GAME_DATA;
		if(!owner.IS_APPLET) {
			String version = System.getProperty("java.version");
			if(version.compareTo("1.5.0") > 0)
				ALWAYS_ON_TOP_SUPPORTED = true;
			if(ALWAYS_ON_TOP_SUPPORTED) {
				owner.FRAME.setAlwaysOnTop(owner.ALWAYS_ON_TOP);
				if(owner.ALWAYS_ON_TOP)
					owner.FRAME.setTitle(
						GAME_DATA.GAME_TITLE+" - "+GAME_DATA.GAME_NAME+" - !");
				else owner.FRAME.setTitle(
					GAME_DATA.GAME_TITLE+" - "+GAME_DATA.GAME_NAME);
			} else {
				owner.FRAME.setTitle(
					GAME_DATA.GAME_TITLE+" - "+GAME_DATA.GAME_NAME);
			}
		}
		setMagnification(owner.MAGNIFICATION);
		SPRITE = new Sprite(GAME_DATA);
		if(owner.IS_APPLET) {
			owner.addKeyListener(this);
		} else {
			owner.FRAME.addWindowListener(this);
			owner.FRAME.addKeyListener(this);
		}
		thisThread = new Thread(this);
		thisThread.start();
	}
	public void run() {
		long down = 0, up = 0, left = 0, right = 0, process = 0;
		GameKey curKey = null;
		int keyNum = 0;
		boolean repeat = false;
		Stack<GameKey> keyStack = new Stack<GameKey>();
		while(true) {
			process = currentTimeMillis();
			if(PRESS_DOWN) {
				if(down==0) {
					keyNum++;
					keyStack.push(curKey);
					curKey = GameKey.DOWN;
				}
				down++;
			} else if(down > 0) {
				keyNum--;
				curKey = keyStack.pop();
				down = 0;
				repeat = false;
			}
			if(PRESS_UP) {
				if(up==0) {
					keyNum++;
					keyStack.push(curKey);
					curKey = GameKey.UP;
				}
				up++;
			} else if(up > 0) {
				keyNum--;
				curKey = keyStack.pop();
				up = 0;
				repeat = false;
			}
			if(PRESS_RIGHT) {
				if(right==0) {
					keyNum++;
					keyStack.push(curKey);
					curKey = GameKey.RIGHT;
				}
				right++;
			} else if(right > 0) {
				keyNum--;
				curKey = keyStack.pop();
				right = 0;
				repeat = false;
			}
			if(PRESS_LEFT) {
				if(left==0) {
					keyNum++;
					keyStack.push(curKey);
					curKey = GameKey.LEFT;
				}
				left++;
			} else if(left > 0) {
				keyNum--;
				curKey = keyStack.pop();
				left = 0;
				repeat = false;
			}
			if(keyNum > 0) GAME_DATA.GAME_KEY_BUFF.add(curKey);
			try {
				if(repeat) {
					Thread.sleep(REPEAT-(currentTimeMillis()-process));
				} else if(keyNum > 0) {
					repeat = true;
					Thread.sleep(REPEAT*3/2-(currentTimeMillis()-process));
				} else {
					Thread.yield();
				}
			} catch(Exception e) {
				e.printStackTrace();
				exit(1);
			}
		}
	}
	protected void setMagnification(int mag) {
		if((mag < 1) || (mag > 2)) return;
		owner.MAGNIFICATION = mag;
		if(owner.IS_APPLET==false) {
			owner.FRAME.setVisible(false);
			int left, right, top, bottom;
			left = owner.FRAME.getInsets().left;
			right = owner.FRAME.getInsets().right;
			top = owner.FRAME.getInsets().top;
			bottom = owner.FRAME.getInsets().bottom;
			owner.FRAME.setSize(SCREEN_W*mag + left + right,
				SCREEN_H*mag + top + bottom);
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			owner.FRAME.setLocation((d.width-SCREEN_W*mag)/2,
				(d.height-SCREEN_H*mag)/2);
			owner.FRAME.setVisible(true);
		}
	}
	public boolean systemKey(int GameKey) {
		switch(GameKey) {
			// ウィンドウを常に最前面表示にする
			case KeyEvent.VK_F3:
				if(ALWAYS_ON_TOP_SUPPORTED) {
					owner.ALWAYS_ON_TOP = !owner.ALWAYS_ON_TOP;
					owner.FRAME.setAlwaysOnTop(owner.ALWAYS_ON_TOP);
					if(owner.ALWAYS_ON_TOP)
						owner.FRAME.setTitle(GAME_DATA.GAME_TITLE
							+" - "+GAME_DATA.GAME_NAME+" - !");
					else
						owner.FRAME.setTitle(
							GAME_DATA.GAME_TITLE+" - "+GAME_DATA.GAME_NAME);
				}
				return true;
			// フルスクリーン／ウィンドウ 切り替え
			case KeyEvent.VK_ENTER:
				if(!PRESS_ALT) return true;
			case KeyEvent.VK_F4:
				return true;
			// ウィンドウサイズの変更
			case KeyEvent.VK_F5:
				setMagnification(owner.MAGNIFICATION==1 ? 2 : 1);
				return true;
			// タイトルに戻す
			case KeyEvent.VK_F12:
				PRESS_F12 = true;
				owner.setMode(GameMode.TITLE);
				return true;
		}
		return false;
	}
	public void keyPressed(KeyEvent e) {
		int buff = e.getKeyCode();
		switch(buff) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
			case KeyEvent.VK_NUMPAD8:
			case KeyEvent.VK_K:
				PRESS_UP = true;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
			case KeyEvent.VK_NUMPAD2:
			case KeyEvent.VK_J:
				PRESS_DOWN = true;
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_KP_LEFT:
			case KeyEvent.VK_NUMPAD4:
			case KeyEvent.VK_H:
				PRESS_LEFT = true;
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_KP_RIGHT:
			case KeyEvent.VK_NUMPAD6:
			case KeyEvent.VK_L:
				PRESS_RIGHT = true;
				break;
			case KeyEvent.VK_Z:
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_ENTER:
				GAME_DATA.GAME_KEY_BUFF.add(GameKey.ENTER);
				break;
			case KeyEvent.VK_ESCAPE:
			case KeyEvent.VK_X:
			case KeyEvent.VK_C:
			case KeyEvent.VK_V:
			case KeyEvent.VK_B:
			case KeyEvent.VK_N:
			case KeyEvent.VK_NUMPAD0:
				GAME_DATA.GAME_KEY_BUFF.add(GameKey.CANCEL);
				break;
			case KeyEvent.VK_SHIFT:
				GAME_DATA.GAME_KEY_BUFF.add(GameKey.SHIFT);
				GAME_DATA.KEY_BUFF.add(KeyEvent.VK_SHIFT);
				break;
			case KeyEvent.VK_ALT:
				PRESS_ALT = true;
				break;
			case KeyEvent.VK_CONTROL:
				PRESS_CTRL = true;
				break;
			case KeyEvent.VK_F9:
				GAME_DATA.TEST_PLAY_KEY_BUFF.add(TestPlayKey.DEBUG);
				break;
			case KeyEvent.VK_F10:
				GAME_DATA.TEST_PLAY_KEY_BUFF.add(TestPlayKey.END_EVENT);
				break;
			default:
				GAME_DATA.KEY_BUFF.add(buff);
				break;
		}
		Thread.yield();
	}
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
			case KeyEvent.VK_NUMPAD8:
			case KeyEvent.VK_K:
				PRESS_UP = false;
				return;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
			case KeyEvent.VK_NUMPAD2:
			case KeyEvent.VK_J:
				PRESS_DOWN = false;
				return;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_KP_LEFT:
			case KeyEvent.VK_NUMPAD4:
			case KeyEvent.VK_H:
				PRESS_LEFT = false;
				return;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_KP_RIGHT:
			case KeyEvent.VK_NUMPAD6:
			case KeyEvent.VK_L:
				PRESS_RIGHT = false;
				return;
			case KeyEvent.VK_SHIFT:
				PRESS_SHIFT = false;
				return;
			case KeyEvent.VK_CONTROL:
				PRESS_CTRL = false;
				return;
			case KeyEvent.VK_ALT:
				PRESS_ALT = false;
				return;
		}
	}
	public void keyTyped(KeyEvent e) {
	}
	public void windowActivated(WindowEvent e) {
		owner.activated();
	}
	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}
	public void windowClosing(WindowEvent e) {
		owner.FRAME.dispose();
	}
	public void windowDeactivated(WindowEvent e) {
		owner.deactivated();
	}
	public void windowDeiconified(WindowEvent e) {
		owner.start();
	}
	public void windowIconified(WindowEvent e) {
		owner.stop();
	}
	public void windowOpened(WindowEvent e) {
	}
}
