package rpg2k.sprite;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TitleMode extends Mode
{
	protected static final int MENU_H = 64;
	
	protected int CURSOR_X, CURSOR_Y, CURSOR_W,
		MENU_X, MENU_Y, MENU_W, SELECT, ANIME;
	protected BufferedImage SCRN_BUFF;
	protected String NEW_GAME, CONTINUE, QUIT;
	
	public TitleMode(Sprite owner) {
		super(owner);
		NEW_GAME = GAME_DATA.TERM.get(0x72);
		CONTINUE = GAME_DATA.TERM.get(0x73);
		QUIT = GAME_DATA.TERM.get(0x75);
		int max = getStringLength(NEW_GAME), cmp = getStringLength(CONTINUE);
		if(max < cmp) max = cmp;
		cmp = getStringLength(QUIT);
		if(max < cmp) max = cmp;
		MENU_W = 8*2+6*max;
		MENU_X = (SCREEN_W-MENU_W) / 2;
		MENU_Y = GAME_DATA.HIDE_TITLE ? 88 : 148;
		CURSOR_W = MENU_W - 4*2;
		CURSOR_X = MENU_X + 4;
		CURSOR_Y = MENU_Y + 8;
		SCRN_BUFF = new BufferedImage(SCREEN_W, SCREEN_H, IMAGE_TYPE);
		Graphics2D g = SCRN_BUFF.createGraphics();
		g.drawImage(GAME_DATA.HIDE_TITLE ? owner.WALLPAPER : MEDIA.getTitle(),
			0, 0, null);
		g.drawImage(MEDIA.getWindow(MENU_W, MENU_H), MENU_X, MENU_Y, null);
	}
	public void paint(Graphics2D g2) {
		g2.drawImage(SCRN_BUFF, 0, 0, null);
		g2.drawImage(MEDIA.getCursor(CURSOR_W, CURSOR_H, CURSOR_FRAME),
			CURSOR_X, CURSOR_Y+SELECT*16, null);
		g2.drawImage(FONT.getString(NEW_GAME, FNT_NORMAL),
			MENU_X+8, MENU_Y+10, null);
		g2.drawImage(FONT.getString(CONTINUE, FNT_NORMAL),
			MENU_X+8, MENU_Y+26, null);
		g2.drawImage(FONT.getString(QUIT, FNT_NORMAL),
			MENU_X+8, MENU_Y+42, null);
	}
	protected void update(GameMode pmode) {
		SELECT = ANIME = 0;
//		if(pmode==GameMode.LOAD)
//			SELECT = 1;
	}
	public void processKey(GameKey key) {
		switch(key) {
			case UP:
				if(SELECT<=0) SELECT = 2;
				else SELECT--;
				break;
			case DOWN:
				if(SELECT>=2) SELECT = 0;
				else SELECT++;
				break;
			case ENTER:
				switch(SELECT) {
					case 0:
						owner.setMode(GameMode.FIELD);
						break;
					case 1:
						owner.setMode(GameMode.LOAD);
						break;
					case 2:
						owner.quitGame();
						break;
				}
				break;
		}
	}
}
