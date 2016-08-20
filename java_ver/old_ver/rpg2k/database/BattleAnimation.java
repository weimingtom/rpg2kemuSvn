package rpg2k.database;

import java.util.Vector;
import rpg2k.*;

public class BattleAnimation extends Data
{
	protected static final int INDEX = 0x13;
	
	protected BattleAnimation(byte[][][] data) {
		super(data);
	}
	protected Object[] getDefaults() {
		return DEFAULT;
	}
	protected static final Object[] DEFAULT = {
		null
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		
	};
}
