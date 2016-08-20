package rpg2k.sprite;

import java.awt.Graphics2D;
import java.awt.Image;
import rpg2k.*;
import rpg2k.media.*;

public abstract class Mode implements Field
{
	protected Sprite owner;
	protected Project GAME_DATA;
	protected SaveData SAVE_DATA;
	protected SaveData.DataBuff BUFF;
	protected Media MEDIA;
	protected Font FONT;
	protected Image WALLPAPER;
	protected Image SYSTEM;
	protected boolean CURSOR_FRAME = false;
	protected static final int CUR_ARROW = 30, CURSOR_H = 16;
	protected static final int
		FNT_NORMAL = 0, FNT_DISABLE = 3, FNT_ENABLE = 0, FNT_STATUS = 1,
		FNT_PARAM_UP = 2, FNT_PARAM_DOWN = 3, FNT_HP_LOW = 4, FNT_MP_LOW = 4;
	
	protected Mode(Sprite s) {
		owner = s;
		GAME_DATA = s.GAME_DATA;
		MEDIA = s.MEDIA;
		FONT = s.FONT;
		WALLPAPER = s.WALLPAPER;
		SAVE_DATA = GAME_DATA.SAVE_DATA;
	}
	protected static int getStringLength(String str) {
		try {
			return str.getBytes(CHARSET).length;
		} catch (Exception e) {
			return 0;
		}
	}
	protected final void updateMode(GameMode pmode) {
		SYSTEM = owner.SYSTEM_GRP;
		WALLPAPER = owner.WALLPAPER;
		FONT = owner.FONT;
		update(pmode);
		CURSOR_FRAME = false;
		BUFF = SAVE_DATA.BUFF;
	}
	protected void processCursorAnime() {
		CURSOR_FRAME = !CURSOR_FRAME;
	}
	protected abstract void paint(Graphics2D g2);
	protected abstract void update(GameMode pmode);
	public abstract void processKey(GameKey key);
}
