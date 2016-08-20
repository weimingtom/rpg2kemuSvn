package rpg2k.sprite;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import rpg2k.SaveData;
import static rpg2k.analyze.Format.*;

public class SaveMode extends Mode
{
	protected SaveData.Header HEADER[] = new SaveData.Header[SAVE_DATA_MAX];
	protected int CURSOR_W, OFFSET, SELECT, CURSOR_ANIME, ARROW_ANIME;
	protected boolean CURSOR_TYPE, ARROW_TYPE;
	protected Image SCRN_BUFF;
	protected String LEVEL, HP;
	
	public SaveMode(Sprite s) {
		super(s);
		CURSOR_W =
			getStringLength(GAME_DATA.TERM.get(0x94))*6+FONT_SIZE+8;
		SCRN_BUFF = new BufferedImage(SCREEN_W, 960, IMAGE_TYPE);
		LEVEL = GAME_DATA.TERM.get(0x80);
		HP = GAME_DATA.TERM.get(0x81);
	}
	public void paint(Graphics2D g2) {
		int top = SELECT - OFFSET, i = 0;
		// 壁紙
		g2.drawImage(WALLPAPER, 0, 0, null);
		// セーブデータの情報
		g2.drawImage(SCRN_BUFF, 0, 40, SCREEN_W, 232,
			0, OFFSET*64, SCREEN_W, OFFSET*64+192, null);
		// 上のメッセージ
		g2.drawImage(MEDIA.getWindow(SCREEN_W, 32), 0, 0, null);
		g2.drawImage(FONT.getString(GAME_DATA.TERM.get(0x93),FNT_NORMAL),
			8, 10, null);
		// カーソル
		g2.drawImage(MEDIA.getCursor(CURSOR_W, CURSOR_H, CURSOR_FRAME),
			4, 48+top*64, null);
		// スクロールの矢印
		if(ARROW_ANIME++ > CUR_ARROW) {
			ARROW_TYPE = !ARROW_TYPE;
			ARROW_ANIME = 0;
		}
		if(ARROW_TYPE) {
			if(OFFSET > 0) g2.drawImage(
				SYSTEM, 152, 32, 168, 40, 40, 8, 56, 16, null);
			if(OFFSET < 12) g2.drawImage(
				SYSTEM, 152, 232, 168, 240, 40, 16, 56, 24, null);
		}
		// セーブデータの番号
		String header = GAME_DATA.TERM.get(0x94);
		for(i = 0; i < 3; i++)
			g2.drawImage(FONT.getString(header+decSpace(OFFSET+i+1, 2),
				FNT_NORMAL), 8, 50+i*64, null);
	}
	protected void update(GameMode pmode) {
		int base = 0, i = 0, j = 0;
		OFFSET = SELECT = CURSOR_ANIME = ARROW_ANIME = 0;
		for(i = 0; i < SAVE_DATA_MAX; i++) if(GAME_DATA.SAVE_DATA.exists(i))
			HEADER[i] = (SaveData.Header)GAME_DATA.SAVE_DATA.getData(i)[0x64];
		boolean exists = false;
		SaveData.Header buff = null;
		Graphics g = SCRN_BUFF.getGraphics();
		for(i = 0; i < 15; i++) {
			base = i*64;
			g.drawImage(MEDIA.getWindow(SCREEN_W, 64), 0, base, null);
			exists = GAME_DATA.SAVE_DATA.exists(i);
			if(exists) {
				buff = HEADER[i];
				for(j = 0; j < 4; j++) if(buff.FACE_SET[j]!=null)
					g.drawImage(MEDIA.getFaceSet(buff.FACE_SET[j],
						buff.FACE_SET_NUM[j]), 96+j*56, 8+base, null);
				g.drawImage(FONT.getString(
					buff.CHARACTER_NAME, FNT_NORMAL), 8, 26+base, null);
				g.drawImage(FONT.getString(LEVEL, FNT_STATUS),
					8, 42+base, null);
				g.drawImage(FONT.getString(
					decSpace(buff.LEVEL, 2), FNT_NORMAL),
					20, 42+base, null);
				g.drawImage(FONT.getString(HP, FNT_STATUS),
					48, 42+base, null);
				g.drawImage(FONT.getString(
					decSpace(buff.HP, 3), FNT_NORMAL),
					60, 42+base, null);
			}
		}
	}
	public void processKey(GameKey key) {
		switch(key) {
			case ENTER:
				owner.returnMode();
				break;
			case CANCEL:
				owner.returnMode();
				break;
			case UP:
				SELECT--;
				if(SELECT < 0) SELECT++;
				else if((SELECT-OFFSET) < 0) OFFSET--;
				break;
			case DOWN:
				SELECT++;
				if(SELECT >= SAVE_DATA_MAX) SELECT--;
				else if((SELECT-OFFSET) > 2) OFFSET++;
				break;
		}
	}
}
