package rpg2k.database;

import rpg2k.*;

public class Term
{
	protected static final int INDEX = 0x15;
	
	private String DATA[];
	
	protected Term(byte[][] data) {
		DATA = new String[data.length];
		for(int i = 0; i < data.length; i = i + 1)
			if(data[i] != null) DATA[i] = Structure.readString(data[i]);
	}
	public String get(int i) {
		return DATA[i];
	}
}
