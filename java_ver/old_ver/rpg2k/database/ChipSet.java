package rpg2k.database;

import rpg2k.*;

public class ChipSet extends Data
{
	protected static final int INDEX = 0x14;
	private static final int LOWER_CHIP_NUM = 162;
	private static final int UPPER_CHIP_NUM = 144;
	
	protected ChipSet(byte[][][] data) {
		super(data);
	}
	protected Object[] getDefaults() {
		return DEFAULT;
	}
	protected static final Object[] DEFAULT = {
		null,
	// 0x0001
		"\0",
	// 0x0002
		"\0",
	// 0x0003
		new short[LOWER_CHIP_NUM],
	// 0x0004
		new byte[LOWER_CHIP_NUM],
	// 0x0005
		new byte[UPPER_CHIP_NUM],
		null, null, null, null, null,
	// 0x000B
		new Integer(0),
	// 0x000C
		new Integer(0)
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		Type.NULL,
		// 名前
		// 0x01
		Type.STRING,
		// チップセットグラフィックファイル名
		// 0x02
		Type.STRING,
		// 地形データ
		// 0x03
		Type.CHIP_LANDFORM,
		// 下層チップの通行・ブロック
		// 0x04
		Type.CHIP_BLOCK,
		// 上層チップの通行・ブロック
		// 0x05
		Type.CHIP_BLOCK,
		// 海チップアニメ方式
		// 0x0B
		Type.INTEGER,
		// 海チップアニメ速度
		// 0x0C
		Type.INTEGER
	};
}
