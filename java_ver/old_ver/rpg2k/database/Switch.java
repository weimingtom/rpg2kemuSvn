package rpg2k.database;

import java.util.Vector;
import rpg2k.*;

public final class Switch implements Field
{
	protected static final int INDEX = 0x17;
	
	private String NAME[];
	
	protected Switch(byte[][][] data) {
		NAME = new String[data.length];
		for(int i = 1, i_length = data.length; i < i_length; i++) {
			try {
				NAME[i] = Structure.readString(data[i][1]);
			} catch (Exception e) {
				NAME[i] = "\0";
			}
		}
	}
	public String getName(int id) {
		return NAME[id];
	}
}
