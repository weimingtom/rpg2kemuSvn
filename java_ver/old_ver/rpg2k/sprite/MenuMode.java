package rpg2k.sprite;

import java.awt.Graphics2D;
import static rpg2k.analyze.Format.*;

public class MenuMode extends Mode
{
	protected enum Mode {
		MAIN, EQUIP, SELECT_SKILL, USE_SKILL, SELECT_ITEM, USE_ITEM, END,
		SELECT_CHARACTER,
	};
	protected int MAIN_CURSOR, SECOND_CURSOR, THIRD_CURSOR;
	protected boolean END_CURSOR = true, EQUIPING;
	protected String
		ITEM, SKILL, EQUIP, SAVE, END, END_MESSAGE, YES, NO,
		POWER, GAURD, MIND, SPEED, WEAPON, SHIELD, ARMOR, HELMET, ACCESSORY,
		CURRENCY;
	protected int END_MESSAGE_W, YES_NO_W;
	
	protected Mode MODE;
	public MenuMode(Sprite s) {
		super(s);
		MODE = Mode.MAIN;
		ITEM = GAME_DATA.TERM.get(0x6a);
		SKILL = GAME_DATA.TERM.get(0x6b);
		EQUIP = GAME_DATA.TERM.get(0x6c);
		SAVE = GAME_DATA.TERM.get(0x6e);
		END = GAME_DATA.TERM.get(0x70);
		END_MESSAGE = GAME_DATA.TERM.get(0x97);
		YES = GAME_DATA.TERM.get(0x98);
		NO = GAME_DATA.TERM.get(0x99);
		END_MESSAGE_W = getStringLength(END_MESSAGE)*FONT_SIZE/2;
		YES_NO_W = getStringLength(YES);
		YES_NO_W = getStringLength(NO) > YES_NO_W ?
			getStringLength(NO)*FONT_SIZE/2 : YES_NO_W*FONT_SIZE/2;
		POWER = GAME_DATA.TERM.get(0x84);
		GAURD = GAME_DATA.TERM.get(0x85);
		MIND = GAME_DATA.TERM.get(0x86);
		SPEED = GAME_DATA.TERM.get(0x87);
		WEAPON = GAME_DATA.TERM.get(0x88);
		SHIELD = GAME_DATA.TERM.get(0x89);
		ARMOR = GAME_DATA.TERM.get(0x8a);
		HELMET = GAME_DATA.TERM.get(0x8b);
		ACCESSORY = GAME_DATA.TERM.get(0x8c);
	}
	public void paint(Graphics2D g2) {
		switch(MODE) {
			case MAIN:
				g2.drawImage(WALLPAPER, 0, 0, null);
				g2.drawImage(MEDIA.getWindow(88, 96), 0, 0, null);
				g2.drawImage(MEDIA.getWindow(88, 32), 0, 208, null);
				g2.drawImage(MEDIA.getWindow(232, 240), 88, 0, null);
				g2.drawImage(MEDIA.getCursor(80, 16, CURSOR_FRAME),
					4, MAIN_CURSOR*16+8, null);
				g2.drawImage(FONT.getString(ITEM, FNT_NORMAL), 8, 10, null);
				g2.drawImage(FONT.getString(SKILL, FNT_NORMAL), 8, 26, null);
				g2.drawImage(FONT.getString(EQUIP,FNT_NORMAL), 8, 42, null);
				g2.drawImage(FONT.getString(SAVE, FNT_NORMAL), 8, 58, null);
				g2.drawImage(FONT.getString(END, FNT_NORMAL), 8, 74, null);
				break;
			case SELECT_CHARACTER:
				g2.drawImage(WALLPAPER, 0, 0, null);
				g2.drawImage(MEDIA.getWindow(88, 96), 0, 0, null);
				g2.drawImage(MEDIA.getWindow(88, 32), 0, 208, null);
				g2.drawImage(MEDIA.getWindow(232, 240), 88, 0, null);
				g2.drawImage(MEDIA.getCursor(80, 16, false),
					4, MAIN_CURSOR*16+8, null);
				g2.drawImage(MEDIA.getCursor(224, 56, CURSOR_FRAME),
					92, 4+SECOND_CURSOR*58, null);
				g2.drawImage(FONT.getString(ITEM, FNT_NORMAL), 8, 10, null);
				g2.drawImage(FONT.getString(SKILL, FNT_NORMAL), 8, 26, null);
				g2.drawImage(FONT.getString(EQUIP,FNT_NORMAL), 8, 42, null);
				g2.drawImage(FONT.getString(SAVE, FNT_NORMAL), 8, 58, null);
				g2.drawImage(FONT.getString(END, FNT_NORMAL), 8, 74, null);
				break;
			case EQUIP:
				g2.drawImage(MEDIA.getWindow(SCREEN_W, 32), 0, 0, null);
				g2.drawImage(MEDIA.getWindow(124, 96), 0, 32, null);
				g2.drawImage(MEDIA.getWindow(196, 96), 124, 32, null);
				g2.drawImage(MEDIA.getWindow(SCREEN_W, 124), 0, 128, null);
				if(EQUIPING) {
					g2.drawImage(MEDIA.getCursor(188, 16, false),
						128, SECOND_CURSOR*16+40, null);
				} else {
					g2.drawImage(MEDIA.getCursor(188, 16, CURSOR_FRAME),
						128, SECOND_CURSOR*16+40, null);
				}
				g2.drawImage(FONT.getString(POWER, FNT_STATUS), 8, 58, null);
				g2.drawImage(FONT.getString(GAURD, FNT_STATUS), 8, 74, null);
				g2.drawImage(FONT.getString(MIND, FNT_STATUS), 8, 90, null);
				g2.drawImage(FONT.getString(SPEED, FNT_STATUS),
					8, 106, null);
				g2.drawImage(FONT.getString(WEAPON, FNT_STATUS),
					132, 42, null);
				g2.drawImage(FONT.getString(SHIELD, FNT_STATUS),
					132, 58, null);
				g2.drawImage(FONT.getString(ARMOR, FNT_STATUS),
					132, 74, null);
				g2.drawImage(FONT.getString(HELMET, FNT_STATUS),
					132, 90, null);
				g2.drawImage(FONT.getString(ACCESSORY, FNT_STATUS),
					132, 106, null);
				break;
			case SELECT_SKILL:
				g2.drawImage(MEDIA.getWindow(SCREEN_W, 32), 0, 0, null);
				g2.drawImage(MEDIA.getWindow(SCREEN_W, 32), 0, 32, null);
				g2.drawImage(MEDIA.getWindow(SCREEN_W, 176), 0, 64, null);
				break;
			case USE_SKILL:
				break;
			case SELECT_ITEM:
				g2.drawImage(MEDIA.getWindow(SCREEN_W, 32), 0, 0, null);
				g2.drawImage(MEDIA.getWindow(SCREEN_W, 208), 0, 32, null);
				g2.drawImage(MEDIA.getCursor(152, 16, CURSOR_FRAME),
					4, 40, null);
				break;
			case USE_ITEM:
				break;
			case END:
				g2.drawImage(WALLPAPER, 0, 0, null);
				g2.drawImage(MEDIA.getWindow(END_MESSAGE_W+16, 32),
					(SCREEN_W-END_MESSAGE_W-16)/2, 72, null);
				g2.drawImage(MEDIA.getWindow(YES_NO_W+16, 48),
					(SCREEN_W-YES_NO_W-16)/2, 120, null);
				g2.drawImage(MEDIA.getCursor(YES_NO_W+8, 16, CURSOR_FRAME),
					(SCREEN_W-YES_NO_W-8)/2, END_CURSOR ? 128 : 144, null);
				g2.drawImage(FONT.getString(END_MESSAGE, FNT_NORMAL),
					(SCREEN_W-END_MESSAGE_W)/2, 82, null);
				g2.drawImage(FONT.getString(YES, FNT_NORMAL),
					(SCREEN_W-YES_NO_W)/2, 130, null);
				g2.drawImage(FONT.getString(NO, FNT_NORMAL),
					(SCREEN_W-YES_NO_W)/2, 146, null);
				break;
		}
	}
	public void update(GameMode pmode) {
		MODE = Mode.MAIN;
		if(pmode==GameMode.SAVE) MAIN_CURSOR = 3;
		else MAIN_CURSOR = 0;
	}
	public void processKey(GameKey key) {
		switch(MODE) {
			case MAIN:
				switch(key) {
					case ENTER:
						SECOND_CURSOR = 0;
						switch(MAIN_CURSOR) {
							case 0:
								MODE = Mode.SELECT_ITEM;
								break;
							case 1:
								MODE = Mode.SELECT_CHARACTER;
								break;
							case 2:
								MODE = Mode.SELECT_CHARACTER;
								break;
							case 3:
								owner.setMode(GameMode.SAVE);
								break;
							case 4:
								MODE = Mode.END;
								break;
						}
						break;
					case CANCEL:
						owner.setMode(GameMode.FIELD);
						break;
					case UP:
						if(MAIN_CURSOR <= 0) MAIN_CURSOR = 4;
						else MAIN_CURSOR--;
						break;
					case DOWN:
						if(MAIN_CURSOR >= 4) MAIN_CURSOR = 0;
						else MAIN_CURSOR++;
						break;
				}
				break;
			case SELECT_CHARACTER:
				switch(key) {
					case ENTER:
						if(MAIN_CURSOR==1)
							MODE = Mode.SELECT_SKILL;
						else if(MAIN_CURSOR==2)
							MODE = Mode.EQUIP;
						break;
					case CANCEL:
						MODE = Mode.MAIN;
						break;
					case UP:
						SECOND_CURSOR--;
						if(SECOND_CURSOR < 0)
							SECOND_CURSOR = BUFF.PARTY_NUMBER-1;
						break;
					case DOWN:
						SECOND_CURSOR++;
						if(SECOND_CURSOR >= BUFF.PARTY_NUMBER)
							SECOND_CURSOR = 0;
						break;
				}
				break;
			case EQUIP:
				switch(key) {
					case CANCEL:
						if(EQUIPING) EQUIPING = false;
						else MODE = Mode.MAIN;
						break;
					case ENTER:
						if(EQUIPING) {
						} else {
							EQUIPING = true;
						}
						break;
					case LEFT:
						if(EQUIPING) {
						}
						break;
					case RIGHT:
						if(EQUIPING) {
						}
						break;
					case UP:
						if(EQUIPING) {
						} else {
							if(SECOND_CURSOR <= 0) SECOND_CURSOR = 4;
							else SECOND_CURSOR--;
						}
						break;
					case DOWN:
						if(EQUIPING) {
						} else {
							if(SECOND_CURSOR >= 4) SECOND_CURSOR = 0;
							else SECOND_CURSOR++;
						}
						break;
				}
				break;
			case SELECT_ITEM:
				switch(key) {
					case CANCEL:
						MODE = Mode.MAIN;
						break;
					case ENTER:
						MODE = Mode.USE_ITEM;
						SECOND_CURSOR = 0;
						break;
					case DOWN:
						SECOND_CURSOR += 2;
						break;
					case UP:
						SECOND_CURSOR -= 2;
						break;
					case LEFT:
						SECOND_CURSOR--;
						break;
					case RIGHT:
						SECOND_CURSOR++;
						break;
				}
				break;
			case SELECT_SKILL:
				switch(key) {
					case CANCEL:
						MODE = Mode.SELECT_CHARACTER;
						break;
					case ENTER:
						MODE = Mode.USE_SKILL;
						break;
					case DOWN:
						break;
					case UP:
						break;
					case RIGHT:
						break;
					case LEFT:
						break;
				}
				break;
			case USE_ITEM:
				switch(key) {
					case CANCEL:
						MODE = Mode.SELECT_ITEM;
						break;
					case ENTER:
						break;
					case UP:
						break;
					case DOWN:
						break;
				}
				break;
			case USE_SKILL:
				switch(key) {
					case CANCEL:
						MODE = Mode.SELECT_SKILL;
						break;
					case ENTER:
//						MODE = Mode.USE_SKILL;
						break;
					case DOWN:
						break;
					case UP:
						break;
				}
				break;
			case END:
				switch(key) {
					case ENTER:
						if(END_CURSOR) {
							owner.setMode(GameMode.TITLE);
						} else {
							MODE = Mode.MAIN;
							END_CURSOR = true;
						}
						break;
					case CANCEL:
						MODE = Mode.MAIN;
						END_CURSOR = true;
						break;
					case UP:
					case DOWN:
						END_CURSOR = !END_CURSOR;
						break;
				}
				break;
		}
	}
}
