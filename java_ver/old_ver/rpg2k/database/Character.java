package rpg2k.database;

import rpg2k.*;

public class Character extends Data
{
	protected static final int INDEX = 0x0B;
	
	protected Character(byte[][][] data) {
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
		// 肩書き
		// 0x02
		"\0",
		// 歩行グラフィックのファイル名
		// 0x03
		"\0",
		// 歩行グラフィックの位置
		// 0x04
		new Integer(0),
		// 半透明
		// 0x05
		new Integer(0), null,
		// 初期レベル
		// 0x07
		new Integer(50),
		// 最高レベル
		// 0x08
		new Integer(1),
		// 必殺有効
		// 0x09
		new Integer(1),
		// 必殺確率
		// 0x0A
		new Integer(30), null, null, null, null,
		// 顔グラフィックのファイル名
		// 0x0F
		"\0",
		// 顔グラフィックの位置
		// 0x10
		new Integer(0), null, null, null, null,
		// 二刀流
		// 0x15
		new Integer(0),
		// 装備固定
		// 0x16
		new Integer(0),
		// 強制ＡＩ行動
		// 0x17
		new Integer(0),
		// 強力防御
		// 0x18
		new Integer(0), null, null, null, null, null, null,
		// 各レベルごとのステータス
		// 0x1F
		new short[6][50], null, null, null, null, null, null, null, null, null,
		// 経験値曲線の基本値
		// 0x29
		new Integer(30),
		// 経験値の増加度
		// 0x2A
		new Integer(30),
		// 経験値の補正値
		// 0x2B
		new Integer(0), null, null, null, null, null, null, null,
		// 初期装備
		// 0x33
		new short[5],
		// 素手で攻撃したときの戦闘アニメ
		// 0x38
		new Integer(0),
		// 職業
		// 0x39
		new Integer(0), null, null, null, null,
		// 戦闘アニメ
		// 0x3E
		new Integer(0),
		// 各レベルで習得する特殊技能
		// 0x3F
		new int[0][0], null, null,
		// 独自戦闘コマンド有効
		// 0x42
		new Integer(0),
		// 独自戦闘コマンド
		// 0x43
		"\0", null, null, null,
		// 状態有効度のデータ数
		// 0x47
		new Integer(0),
		// 状態有効度
		// 0x48
		new char[0],
		// 属性有効度のデータ数
		// 0x49
		new Integer(0),
		// 属性有効度
		// 0x4A
		new char[0], null, null, null, null, null,
		// 戦闘コマンド
		// 0x50
		new int[6]
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		
	};
}
