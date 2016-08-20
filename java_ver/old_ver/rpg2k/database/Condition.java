package rpg2k.database;

import rpg2k.*;

public class Condition extends Data
{
	protected static final int INDEX = 0x12;
	
	protected Condition(byte[][][] data) {
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
		// 種別
		// 0x02
		new Integer(0),
		// 表示色
		// 0x03
		new Integer(6),
		// 優先度
		// 0x04
		new Integer(50),
		// 行動制限
		// 0x05
		new Integer(100), null, null, null, null, null,
		// 異常発生率/Ａ
		// 0x0B
		new Integer(80),
		// 異常発生率/Ｂ
		// 0x0C
		new Integer(60),
		// 異常発生率/Ｃ
		// 0x0D
		new Integer(30),
		// 異常発生率/Ｄ
		// 0x0E
		new Integer(0),
		// 異常発生率/Ｅ
		// 0x0F
		new Integer(0), null, null, null, null, null,
		// 最低持続ターン数
		// 0x15
		new Integer(0),
		// 自然治癒確率
		// 0x16
		new Integer(0),
		// 衝撃による治癒確率
		// 0x17
		new Integer(0), null, null, null, null, null, null, null,
		// 攻撃力の半減
		// 0x1F
		new Boolean(false),
		// 防御力の半減
		// 0x20
		new Boolean(false),
		// 精神力の半減
		// 0x21
		new Boolean(false),
		// 敏捷性の半減
		// 0x22
		new Boolean(false),
		// 命中率減少量
		// 0x23
		new Integer(100),
		// 100% 攻撃回避
		// 0x24
		new Boolean(false),
		// 魔法反射
		// 0x25
		new Boolean(false),
		// アイテムの装備が外れなくなる
		// 0x26
		new Boolean(false),
		// アニメ
		// 0x27
		new Integer(6), null,
		// 打撃系特殊技能の使用不可
		// 0x29
		new Boolean(false),
		// 使用できなくなる特殊技能の打撃関係度
		// 0x2A
		new Integer(0),
		// 精神系特殊技能の使用不可
		// 0x2B
		new Boolean(false),
		// 使用できなくなる特殊技能の精神関係度
		// 0x2C
		new Integer(0),
		// ＨＰの増減の種類
		// 0x2D
		new Integer(0),
		// ＭＰの増減の種類
		// 0x2E
		new Integer(0), null, null, null, null,
		// 味方がこの状態になったときのメッセージ
		// 0x33
		"\0",
		// 敵がこの状態になったときのメッセージ
		// 0x34
		"\0",
		// すでにこの状態になっているときのメッセージ
		// 0x35
		"\0",
		// この状態のときの自分のターンのメッセージ
		// 0x36
		"\0",
		// この状態が回復したときのメッセージ
		// 0x37
		"\0", null, null, null, null, null,
		// 戦闘中毎ターン減少するＨＰの割合
		// 0x3D
		new Integer(0),
		// 戦闘中毎ターン減少するＨＰの量
		// 0x3E
		new Integer(0),
		// マップ移動中に変化するＨＰ(x歩ごと)
		// 0x3F
		new Integer(0),
		// マップ移動中に変化するＨＰの量
		// 0x40
		new Integer(0),
		// 戦闘中毎ターン減少するＭＰの割合
		// 0x41
		new Integer(0),
		// 戦闘中毎ターン減少するＭＰの量
		// 0x42
		new Integer(0),
		// マップ移動中に変化するＭＰ(x歩ごと)
		// 0x43
		new Integer(0),
		// マップ移動中に変化するＭＰの量
		// 0x44
		new Integer(0)
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		
	};
}
