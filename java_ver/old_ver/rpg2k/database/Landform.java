package rpg2k.database;

import rpg2k.*;

public class Landform extends Data
{
	public static final int NUMBER = 0x10;
	
	protected Landform(byte[][][] data) {
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
		// ダメージ
		// 0x02
		0,
		// 敵出現倍率
		// 0x03
		100,
		// 戦闘背景のファイル名
		// 0x04
		"\0",
		// 小型船での通行
		// 0x05
		false,
		// 大型船の通行
		// 0x06
		false,
		// 飛行船の通行
		// 0x07
		true, null,
		// 飛行船で着陸可能
		// 0x09
		true, null,
		// 通常キャラの表示方法
		// 0x0B
		0,
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		
	};
}
