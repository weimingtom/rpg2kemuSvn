package rpg2k.database;

import rpg2k.*;

public class EnemyGroup extends Data
{
	protected static final int INDEX = 0x0F;
	
	protected EnemyGroup(byte[][][] data) {
		super(data);
	}
	protected Object[] getDefaults() {
		return DEFAULT;
	}
	protected static final Object[] DEFAULT = {
		null,
		// 名前
		// 0x01
		"\0",
		// 敵リスト
		// 0x02
		new int[0][0], null,
		// 出現可能地形のデータ数
		// 0x04
		new Integer(0),
		// 出現可能地形のデータ
		// 0x05
		new boolean[]{true}
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		
	};
}
