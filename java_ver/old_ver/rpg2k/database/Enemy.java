package rpg2k.database;

import rpg2k.*;

public class Enemy extends Data
{
	protected static final int INDEX = 0x0E;
	
	protected Enemy(byte[][][] data) {
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
		// 敵グラフィックのファイル名
		// 0x02
		"\0",
		// グラフィックの色相
		// 0x03
		new Integer(0),
		// 最大ＨＰ
		// 0x04
		new Integer(10),
		// 最大ＭＰ
		// 0x05
		new Integer(10),
		// 攻撃量
		// 0x06
		new Integer(10),
		// 防御力
		// 0x07
		new Integer(10),
		// 精神力
		// 0x08
		new Integer(10),
		// 敏捷性
		// 0x09
		new Integer(10),
		// グラフィックの半透明
		// 0x0A
		new Boolean(false),
		// 落とす経験値
		// 0x0B
		new Integer(0),
		// 落とすお金
		// 0x0C
		new Integer(0),
		// 落とすアイテム
		// 0x0D
		new Integer(0),
		// アイテム出現率
		// 0x0E
		new Integer(100), null, null, null, null, null, null,
		// 必殺有効
		// 0x15
		new Boolean(false),
		// 必殺確率
		// 0x16
		new Integer(30), null, null, null,
		// 通常攻撃のミス多発
		// 0x1A
		new Boolean(false), null,
		// グラフィックを空中に
		// 0x1C
		new Boolean(false), null,
		// 状態有効度のデータ数
		// 0x1F
		new Integer(0),
		// 状態有効度のデータ
		// 0x20
		new char[0],
		// 属性有効度のデータ数
		// 0x21
		new Integer(0),
		// 属性有効度のデータ
		// 0x22
		new char[0], null, null, null, null, null, null, null,
		// 攻撃パターン
		// 0x2A
		new AttackPattern()
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		
	};
}
