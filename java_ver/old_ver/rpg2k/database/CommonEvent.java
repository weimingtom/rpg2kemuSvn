package rpg2k.database;

import rpg2k.*;

public class CommonEvent extends Data
{
	protected static final int INDEX = 0x19;
	
	protected CommonEvent(byte[][][] data) {
		super(data);
	}
	protected Object[] getDefaults() {
		return DEFAULT;
	}
	protected static final Object[] DEFAULT = {
		null,
		"\0",
		null, null, null, null, null, null, null, null, null,
		5,
		0,
		1,
		null, null, null, null, null, null, null, null, null
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		Type.NULL,
		// 名前
		// 0x01
		Type.STRING,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL,
		// イベント開始条件
		// 0x0B
		Type.INTEGER,
		// イベント出現条件 スイッチ
		// 0x0C
		Type.INTEGER,
		// イベント出現条件 スイッチ番号
		// 0x0D
		Type.INTEGER,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL,
		// データサイズ
		// 0x15
		Type.INTEGER,
		// イベントデータ
		// 0x16
		Type.EVENT
	};

}