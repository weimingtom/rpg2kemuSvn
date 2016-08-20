package rpg2k.database;

import rpg2k.*;
import rpg2k.media.Sound;

public class Skill extends Data
{
	public static final int INDEX = 0x0C;
	
	protected Skill(byte[][][] data) {
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
		// 説明
		// 0x02
		"\0",
		// 使用時のメッセージの１行目
		// 0x03
		"\0",
		// 使用時のメッセージの２行目
		// 0x04
		"\0", null, null,
		// 失敗時のメッセージ
		// 0x07
		"\0",
		// 種別
		// 0x08
		new Integer(0),
		// 消費ＭＰの方式
		// 0x09
		new Integer(0),
		// 消費ＭＰの割合
		// 0x0A
		new Integer(1),
		// 消費ＭＰ
		// 0x0B
		new Integer(0),
		// 効果範囲
		// 0x0C
		new Integer(0),
		// ＯＮにするスイッチ
		// 0x0D
		new Boolean(true),
		// 戦闘アニメ
		// 0x0E
		new Boolean(false), null,
		// 効果音
		// 0x10
		new Sound(), null,
		// フィールドで使用可能
		// 0x12
		new Boolean(true),
		// 戦闘中使用可能
		// 0x13
		new Boolean(false),
		// 「状態変化」と「状態治療」の反転
		// 0x14
		new Boolean(false),
		// 打撃関係度
		// 0x15
		new Integer(0),
		// 精神関係度
		// 0x16
		new Integer(3),
		// 数値分散度
		// 0x17
		new Integer(4),
		// 基本効果量
		// 0x18
		new Integer(0),
		// 基本成功率
		// 0x19
		new Integer(100), null, null, null, null, null,
		// ＨＰ低下
		// 0x1F
		new Boolean(false),
		// ＭＰ低下
		// 0x20
		new Boolean(false),
		// 攻撃力低下
		// 0x21
		new Boolean(false),
		// 防御力低下
		// 0x22
		new Boolean(false),
		// 精神力低下
		// 0x23
		new Boolean(false),
		// 敏捷性低下
		// 0x24
		new Boolean(false),
		// 吸収型の能力値低下
		// 0x25
		new Boolean(false),
		// 防御無視型の能力値低下
		// 0x26
		new Boolean(false), null, null,
		// 状態変化のデータ数
		// 0x29
		new Integer(0),
		// 状態変化のデータ
		// 0x2A
		new boolean[]{false},
		// 攻撃・防御 属性のデータ数
		// 0x2B
		new Integer(0),
		// 攻撃・防御 属性のデータ
		// 0x2C
		new boolean[]{false},
		// 攻撃・防御 属性の 低下・上昇
		// 0x2D
		new Boolean(false), null, null, null,
		// 使用時のアニメ
		// 0x31
		new Integer(0),
		// ＣＢＡデータ
		// 0x32
		new Integer(0)
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		
	};
}
