package rpg2k.sprite;

import java.awt.*;
import java.awt.image.*;
import java.net.URL;
import java.util.*;
import rpg2k.*;
import rpg2k.media.Media;
import static rpg2k.analyze.Format.*;

public class Sprite implements Field
{
	protected Image DEF_SYSTEM_GRP, SYSTEM_GRP, WALLPAPER;
	protected Image[] STRING;
	protected BufferedImage SCRN =
		new BufferedImage(SCREEN_W, SCREEN_H, IMAGE_TYPE);
	public Media MEDIA;
	protected rpg2k.media.Font FONT;
	protected Project GAME_DATA;
	protected GameMode GAME_MODE = GameMode.TITLE;
	protected String CUR_SYS_NAME;
	protected int CUR_WALLPAPER, CUR_FONT;
	protected boolean PAUSE = false;
	private int CUR_SAVE_DATA_ID;
	protected Mode CUR_MODE;
	protected GameMode PRE_MODE;
	protected ModeListener LISTENER;
	protected int CURSOR_COUNT = 0;
	protected static final int CUR_NORMAL = 12;
	
	// コンストラクタ
	public Sprite(Project gameData) {
		GAME_DATA = gameData;
		MEDIA = new Media(gameData);
		FONT = new rpg2k.media.Font(gameData);
		WALLPAPER = new BufferedImage(SCREEN_W, SCREEN_H, IMAGE_TYPE);
		setSystem(GAME_DATA.SYS_DATA.SYSTEM,
			GAME_DATA.SYS_DATA.WALLPAPER, GAME_DATA.SYS_DATA.FONT);
		DEF_SYSTEM_GRP = SYSTEM_GRP;
		TITLE_MODE = new rpg2k.sprite.TitleMode(this);
		GAMEOVER_MODE = new rpg2k.sprite.GameoverMode(this);
		SHOP_MODE = new rpg2k.sprite.ShopMode(this);
		SAVE_MODE = new rpg2k.sprite.SaveMode(this);
		LOAD_MODE = new rpg2k.sprite.LoadMode(this);
		DEBUG_MODE = new rpg2k.sprite.DebugMode(this);
		BATTLE_MODE = new rpg2k.sprite.BattleMode(this);
		MENU_MODE = new rpg2k.sprite.MenuMode(this);
		FIELD_MODE = new rpg2k.sprite.FieldMode(this);
		CUR_MODE = TITLE_MODE;
		GAME_DATA.SAVE_DATA.setSprite(this);
	}
	// 描画を一時停止
	public void pause(boolean flag) {
		PAUSE = flag;
	}
	// スクリーンを取得
	public Image getScreen() {
		if(PAUSE) return null;
		Graphics2D g2 = SCRN.createGraphics();
		CUR_MODE.paint(g2);
		if(CURSOR_COUNT++ > CUR_NORMAL) {
			CUR_MODE.processCursorAnime();
			CURSOR_COUNT = 0;
		}
		return SCRN;
	}
	// システムグラフィックを設定
	public void setSystem(String file, int wallpaper, int font) {
		SYSTEM_GRP = MEDIA.getSystem(file, wallpaper);
		FONT.setSystem((BufferedImage)SYSTEM_GRP, font);
		CUR_SYS_NAME = file;
		CUR_WALLPAPER = wallpaper;
		CUR_FONT = font;
		int i = 0, j = 0;
		Graphics g = WALLPAPER.getGraphics();
		if(GAME_DATA.SYS_DATA.WALLPAPER==1)
			for(i = 0; i < SCREEN_W/16; i++)
				for(j = 0; j < SCREEN_H/16; j++)
					g.drawImage(SYSTEM_GRP, i*16, j*16, i*16+16, j*16+16,
						0, 32, 16, 48, null);
		else if(GAME_DATA.SYS_DATA.WALLPAPER==0)
			g.drawImage(SYSTEM_GRP,
				0, 0, SCREEN_W, SCREEN_H, 0, 32, 16, 48, null);
	}
	// ゲームモードの設定
	public void setMode(GameMode mode) {
		if(!CUR_SYS_NAME.equals(GAME_DATA.SYS_DATA.SYSTEM)
			|| (CUR_WALLPAPER!=GAME_DATA.SYS_DATA.WALLPAPER)
			|| (CUR_FONT!=GAME_DATA.SYS_DATA.FONT)
		) setSystem(GAME_DATA.SYS_DATA.SYSTEM,
			GAME_DATA.SYS_DATA.WALLPAPER, GAME_DATA.SYS_DATA.FONT);
		LISTENER.modeChanged(mode);
		switch(mode) {
			case TITLE:
				CUR_MODE = TITLE_MODE;
				break;
			case GAMEOVER:
				CUR_MODE = GAMEOVER_MODE;
				break;
			case LOAD:
				CUR_MODE = LOAD_MODE;
				break;
			case DEBUG:
				CUR_MODE = DEBUG_MODE;
				break;
			case SAVE:
				CUR_MODE = SAVE_MODE;
				break;
			case SHOP:
				CUR_MODE = SHOP_MODE;
				break;
			case BATTLE:
				CUR_MODE = BATTLE_MODE;
				break;
			case MENU:
				CUR_MODE = MENU_MODE;
				break;
			case FIELD:
				CUR_MODE = FIELD_MODE;
				break;
		}
		CUR_MODE.updateMode(GAME_MODE);
		PRE_MODE = GAME_MODE;
		GAME_MODE = mode;
	}
	// 前のモードに戻る
	public void returnMode() {
		setMode(PRE_MODE);
	}
	// ゲームモードを取得
	public GameMode getMode() {
		return GAME_MODE;
	}
	// キー入力を処理
	public void processKey(GameKey key) {
		CUR_MODE.processKey(key);
	}
	// モードリスナーを設定
	public void setModeListener(ModeListener ml) {
		LISTENER = ml;
	}
	// 現在のセーブデータを取得
	public int getSaveDataID() {
		return CUR_SAVE_DATA_ID;
	}
	// セーブデータを設定
	protected void setSaveData(int id) {
		CUR_SAVE_DATA_ID = id;
	}
	// ゲーム終了
	protected void quitGame() {
		LISTENER.quit(GAME_MODE);
	}
	// テストプレイのキーを取得
	protected TestPlayKey getTestPlayKey() {
		return LISTENER.getTestPlayKey();
	}
	// エンカウントとブロックがオフか
	protected boolean isEncountBlockOff() {
		return LISTENER.isEncountBlockOff();
	}
	// 文章の表示が瞬間表示か
	protected boolean isInstantShow() {
		return LISTENER.isInstantShow();
	}
	// ゲームモード
	protected TitleMode TITLE_MODE;
	protected GameoverMode GAMEOVER_MODE;
	protected ShopMode SHOP_MODE;
	protected LoadMode LOAD_MODE;
	protected SaveMode SAVE_MODE;
	protected DebugMode DEBUG_MODE;
	protected BattleMode BATTLE_MODE;
	protected MenuMode MENU_MODE;
	protected FieldMode FIELD_MODE;
}
