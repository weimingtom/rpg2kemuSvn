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
		// ���O
		// 0x01
		"\0",
		// ����
		// 0x02
		// 0:���푮��
		// 1:���@����
		new Integer(0), null, null, null, null, null, null, null, null,
		// ���ʗʕϓ��� / �`
		// 0x0B
		new Integer(300),
		// ���ʗʕϓ��� / �a
		// 0x0C
		new Integer(200),
		// ���ʗʕϓ��� / �b
		// 0x0D
		new Integer(100),
		// ���ʗʕϓ��� / �c
		// 0x0E
		new Integer(50),
		// ���ʗʕϓ��� / �d
		// 0x0F
		new Integer(0),
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		
	};
}
