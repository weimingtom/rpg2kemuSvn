package rpg2k.sprite;

import rpg2k.Field;

public interface ModeListener
{
	public void modeChanged(Field.GameMode mode);
	public void quit(Field.GameMode mode);
	public Field.TestPlayKey getTestPlayKey();
	public boolean isEncountBlockOff();
	public boolean isInstantShow();
}
