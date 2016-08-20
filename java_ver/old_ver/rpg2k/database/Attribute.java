package rpg2k.database;

import rpg2k.*;

public class Attribute extends Data
{
	public static final int INDEX = 0x11;
	
	protected Attribute(byte[][][] data) {
		super(data);
	}
	protected Object[] getDefaults() {
		return DEFAULT;
	}
	protected static final Object[] DEFAULT = {
		null,
		// –¼‘O
		// 0x01
		"\0",
		// ¯•Ê
		// 0x02
		// 0:•Ší‘®«
		// 1:–‚–@‘®«
		new Integer(0), null, null, null, null, null, null, null, null,
		// Œø‰Ê—Ê•Ï“®—Ê / ‚`
		// 0x0B
		new Integer(300),
		// Œø‰Ê—Ê•Ï“®—Ê / ‚a
		// 0x0C
		new Integer(200),
		// Œø‰Ê—Ê•Ï“®—Ê / ‚b
		// 0x0D
		new Integer(100),
		// Œø‰Ê—Ê•Ï“®—Ê / ‚c
		// 0x0E
		new Integer(50),
		// Œø‰Ê—Ê•Ï“®—Ê / ‚d
		// 0x0F
		new Integer(0),
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		
	};
}
