package rpg2k.database;

import rpg2k.*;

public class Item extends Data implements Field
{
	protected static final int INDEX = 0x0D;
	
	protected Item(byte[][][] data) {
		super(data);
	}
	protected Object[] getDefaults() {
		return DEFAULT;
	}
	protected static final Object[] DEFAULT = {
		// 武 → 武器
		// 装 → 盾/鎧/兜/装飾品
		// 薬 → 薬
		// 本 → 本
		// 種 → 種
		// 特 → 特殊技能発動
		// ス → スイッチ
		
		null,
		// 名前
		// 武・装・薬・本・種・特・ス
		// 0x01
		"\0",
		// 説明
		// 武・装・薬・本・種・特・ス
		// 0x02
		"\0",
		// 種別
		// 武・装・薬・本・種・特・ス
		// 0x03
		new Integer(0), null,
		// 値段
		// 武・装・薬・本・種・特・ス
		// 0x05
		new Integer(0),
		// 使用回数
		// 薬・本・種・特・ス
		// 0x06
		new Integer(1), null, null, null, null,
		// 攻撃力の上昇量
		// 武・装
		// 0x0B
		new Integer(0),
		// 防御力の上昇量
		// 武・装
		// 0x0C
		new Integer(0),
		// 精神力の上昇量
		// 武・装
		// 0x0D
		new Integer(0),
		// 敏捷性の上昇量
		// 武・装
		// 0x0E
		new Integer(0),
		// 持ち手
		// 0:片手持ち
		// 1:両手持ち
		// 武
		// 0x0F
		new Integer(0),
		// 消費ＭＰ
		// 武
		// 0x10
		new Integer(0),
		// 基本命中率
		// 武
		// 0x11
		new Integer(0),
		// 必殺確率増加量
		// 武
		// 0x12
		new Integer(0), null,
		// 戦闘アニメ
		// 武
		// 0x14
		new Integer(1),
		// ターン最初に先制攻撃
		// 武
		// 0x15
		new Boolean(false),
		// 二回攻撃
		// 武
		// 0x16
		new Boolean(false),
		// 全体攻撃
		// 武
		// 0x17
		new Boolean(false),
		// 敵の回避率を無視
		// 武
		// 0x18
		new Boolean(false),
		// 必殺防止
		// 装
		// 0x19
		new Boolean(false),
		// 物理攻撃の回避率アップ
		// 装
		// 0x1A
		new Boolean(false),
		// ＭＰ消費量半減
		// 装
		// 0x1B
		new Boolean(false),
		// 地形ダメージ無効
		// 装
		// 0x1C
		new Boolean(false),
		// 道具としての効果
		// 武・装
		// 0x1D
		new Integer(1), null,
		// 効果範囲
		// 薬
		// 0x1F
		new Integer(0),
		// ＨＰ回復量の割合
		// 薬
		// 0x20
		new Integer(0),
		// ＨＰ回復量の絶対量
		// 薬
		// 0x21
		new Integer(0),
		// ＭＰ回復量の割合
		// 薬
		// 0x22
		new Integer(0),
		// ＭＰ回復量の絶対量
		// 薬
		// 0x23
		new Integer(0), null,
		// フィールドでのみ使用可能
		// 薬
		// 0x25
		new Boolean(false),
		// 戦闘不能キャラクターのみに有効
		// 薬
		// 0x26
		new Boolean(false), null, null,
		// 最大ＨＰの変化量
		// 種
		// 0x29
		new Integer(0),
		// 最大ＭＰの変化量
		// 種
		// 0x2A
		new Integer(0),
		// 攻撃力の変化量
		// 種
		// 0x2B
		new Integer(0),
		// 防御量の変化量
		// 種
		// 0x2C
		new Integer(0),
		// 精神力の変化量
		// 種
		// 0x2D
		new Integer(0),
		// 敏捷性の変化量
		// 種
		// 0x2E
		new Integer(0), null, null, null, null,
		// 使用時のメッセージ
		// 特
		// 0x33
		"\0", null,
		// 習得・発動 特殊技能
		// 武・装・本・特
		// 0x35
		new Integer(1), null,
		// ＯＮにするスイッチ
		// ス
		// 0x37
		new Integer(1), null,
		// フィールドで使用可能
		// ス
		// 0x39
		new Boolean(true),
		// バトルで使用可能
		// ス
		// 0x3A
		new Boolean(false), null, null,
		// 装備・使用 可能な主人公のデータ数
		// 武・装・薬・本・種・特
		// 0x3D
		new Integer(0),
		// 装備・使用 可能な主人公のデータ
		// 武・装・薬・本・種・特
		// 0x3E
		new boolean[]{true},
		// 状態 変化・防止・治療 のデータ数
		// 武・装・薬
		// 0x3F
		new Integer(0),
		// 状態 変化・防止・治療 のデータ
		// 武・装・薬
		// 0x40
		new boolean[]{false},
		// 攻撃・防御 属性のデータ数
		// 武・装
		// 0x41
		new Integer(0),
		// 攻撃・防御 属性のデータ
		// 武・装
		// 0x42
		new boolean[]{false},
		// 状態 変化・防止 確率
		// 武・装
		// 0x43
		new Integer(0),
		// 「状態変化」と「状態治療」の逆転
		// 武・装
		// 0x44
		new Boolean(false),
		// 使用時のアニメ
		// 武・装
		// 0x45
		new Integer(0),
		// アイテムとして特殊技能を使う
		// 武・装
		// 0x47
		new Boolean(false),
		// 装備可能な職業のデータ数
		// 武・装・薬・本・種・特
		// 0x48
		new Integer(0),
		// 装備可能な職業
		// 武・装・薬・本・種・特
		// 0x49
		new boolean[]{true}
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		
	};
}
