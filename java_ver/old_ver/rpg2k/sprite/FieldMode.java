package rpg2k.sprite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import rpg2k.*;
import static rpg2k.analyze.Format.*;

public class FieldMode extends Mode 
{
	private MapTree MAP_TREE;
	private MapUnit MAP_UNIT;
	
	public FieldMode(Sprite s) {
		super(s);
		BUFF = SAVE_DATA.BUFF;
		MAP_TREE = GAME_DATA.MAP_TREE;
		MAP_UNIT = GAME_DATA.MAP_UNIT;
	}
	public void paint(Graphics2D g2) {
		int x = 0, y = 0, scrnX = BUFF.SCREEN_X, scrnY = BUFF.SCREEN_Y;
		g2.setColor(Color.black);
		if(BUFF.PANORAMA == null) g2.fillRect(0, 0, SCREEN_W, SCREEN_H);
		else g2.drawImage(BUFF.PANORAMA, 0, 0, null);
		for(x = 0, scrnX = BUFF.SCREEN_X; x < 20; x++, scrnX++) {
			for(y = 0, scrnY = BUFF.SCREEN_Y; y < 15; y++, scrnY++) {
				try {
					g2.drawImage(MEDIA.getChip(
						BUFF.LOWER_MAP[scrnY][scrnX], 0), x*16, y*16, null);
					g2.drawImage(MEDIA.getChip(
						BUFF.UPPER_MAP[scrnY][scrnX], 0), x*16, y*16, null);
				} catch(Exception e) {}
			}
		}
		g2.drawImage(
			BUFF.HERO[BUFF.LOCATION.PATTERN][BUFF.LOCATION.DIRECTION],
			(BUFF.LOCATION.X-BUFF.SCREEN_X)*16-4,
			(BUFF.LOCATION.Y-BUFF.SCREEN_Y)*16-16, null);
	}
	public void update(GameMode pmode) {
		if(pmode==GameMode.TITLE) SAVE_DATA.startNewGame();
		else if(pmode==GameMode.LOAD)
			SAVE_DATA.setCurrent(owner.getSaveDataID());
		else return;
	}
	public void processKey(GameKey key) {
		switch(key) {
			case ENTER:
				break;
			case CANCEL:
				if(BUFF.CAN_OPEN_MENU) owner.setMode(GameMode.MENU);
				break;
			case UP:
				break;
			case DOWN:
				break;
			case LEFT:
				break;
			case RIGHT:
				break;
			case SHIFT:
				break;
		}
	}
}
