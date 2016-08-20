package rpg2k.sprite;

import java.awt.Graphics2D;
import java.awt.Image;

public class GameoverMode extends Mode
{
	public Image SCRN_BUFF;
	public GameoverMode(Sprite s) {
		super(s);
		SCRN_BUFF = MEDIA.getGameover();
	}
	public void paint(Graphics2D g2) {
		g2.drawImage(SCRN_BUFF, 0, 0, null);
	}
	public void update(GameMode pmode) {
	}
	public void processKey(GameKey key) {
		switch(key) {
			case ENTER:
			case CANCEL:
				owner.setMode(GameMode.TITLE);
				break;
		}
	}
}
