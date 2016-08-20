package rpg2k;

import java.awt.event.KeyEvent;

public interface Field
{
	public static final int SAVE_DATA_MAX = 15;
	public static final int PICTURE_MAX = 50;
	public static final int CHIP_MAX = 10144;
	public static final int FONT_SIZE = 12;
	public static final int SCREEN_W = 320, SCREEN_H = 240;
	public static final int CHIP_W = 20, CHIP_H = 15;
	public static final String CHARSET = "Shift_JIS";
	public static final int LDB_MAX_NUMBER = 5000;
	public static final int
		VARIABLE_MAXIMUM =  999999, VARIABLE_MINIMUM = -999999;
	public static final int IMAGE_TYPE =
		java.awt.image.BufferedImage.TYPE_INT_ARGB;
	public enum GameKey {
		NULL, ENTER, CANCEL, SHIFT, UP, DOWN, RIGHT, LEFT;
		private int[] CODE = {
			-1,
			KeyEvent.VK_ENTER,
			KeyEvent.VK_ESCAPE,
			KeyEvent.VK_SHIFT,
			KeyEvent.VK_UP,
			KeyEvent.VK_DOWN,
			KeyEvent.VK_RIGHT,
			KeyEvent.VK_LEFT,
		};
		public int getKeyCode() {
			return CODE[ordinal()];
		}
	}
	public enum TestPlayKey {
		NULL, DEBUG, END_EVENT;
		private int[] CODE = {
			-1,
			KeyEvent.VK_F9,
			KeyEvent.VK_F10,
		};
		public int getKeyCode() {
			return CODE[ordinal()];
		}
	}
	public enum ItemType {
		NORMAL, WEAPON, SHEILD, ARMOUR, HELMET, ACCESSORY,
		MEDICINE, BOOK, SEED, SKILL, SWITCH,
	};
	public enum GameMode {
		// ゲーム外のゲームモード
		TITLE, GAMEOVER, LOAD,
		// ゲーム内のゲームモード
		DEBUG, SAVE, FIELD, BATTLE, SHOP, MENU,
	};
}
