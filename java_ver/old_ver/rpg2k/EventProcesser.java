package rpg2k;

import rpg2k.sprite.Sprite;
import rpg2k.media.Sound;

public class EventProcesser implements Field
{
	// ループ一回あたりのステップ数
	protected static final int STEP_PER_LOOP = 10000;
	// イベントの呼び出しのバッファ数
	protected static final int STACK_SIZE = 1000;
	// 次のループに
	protected static final int NEXT_FRAME = -1;
	// 次のイベントに
	protected static final int NEXT_EVENT = -2;
	// イベントの終端
	protected static final int END_OF_EVENT = -3;
	// ゲームモード変更
	protected static final int CHANGE_MODE = -4;
	
	protected Sprite SPRITE;
	protected Project GAME_DATA;
	protected SaveData.DataBuff BUFF;
	
	// コンストラクタ
	public EventProcesser(Sprite s, Project data) {
		SPRITE = s;
		GAME_DATA = data;
	}
	// イベントを実行
	public void runEvent() {
		int i = 0;
		BUFF = GAME_DATA.SAVE_DATA.BUFF;
		while(i < STEP_PER_LOOP) {
			i += runEventCode(null);
		}
	}
	private int runEventCode(final Event e) {
		if(e == null) return 1;
		Sound sBuff;
		switch(e.CODE) {
			// 分岐処理の終端
			case 00000:
				return 0;
			// イベント全体の終了
			case 00010:
				return END_OF_EVENT;
			// 文章の表示（1行目)
			case 10110:
				return 1;
			// 文章の表示(2行目～)
			case 20110:
				return 1;
			// 文章オプションの変更
			case 10120:
				return 1;
			// 顔グラフィックの設定
			case 10130:
				return 1;
			// 選択肢の処理（分岐開始)
			case 10140:
				return 1;
			// 選択肢の処理（選択肢)
			case 20140:
				return 1;
			// 選択肢の処理（分岐終了）
			case 20141:
				return 1;
			// 数値入力の処理
			case 10150:
				return 1;
			// スイッチの操作
			case 10210:
				return 1;
			// 変数の操作
			case 10220:
				return 1;
			// タイマーの操作
			case 10230:
				return 1;
			// 所持金の増減
			case 10310:
				return 1;
			// アイテムの増減
			case 10320:
				return 1;
			// メンバーの入れ替え
			case 10330:
				return 1;
			// 経験値の増減
			case 10410:
				return 1;
			// レベルの増減
			case 10420:
				return 1;
			// 能力値の増減
			case 10430:
				return 1;
			// 特殊技能の増減
			case 10440:
				return 1;
			// 装備の変更
			case 10450:
				return 1;
			// HPの増減
			case 10460:
				return 1;
			// MPの増減
			case 10470:
				return 1;
			// 状態の変更
			case 10480:
				return 1;
			// 全回復
			case 10490:
				return 1;
			// ダメージの処理
			case 10500:
				return 1;
			// 主人公の名前変更
			case 10610:
				return 1;
			// 主人公の肩書き変更
			case 10620:
				return 1;
			// 主人公の歩行グラフィック変更
			case 10630:
				return 1;
			// 主人公の顔グラフィック変更
			case 10640:
				return 1;
			// 乗り物グラフィックの変更
			case 10650:
				switch(e.ARGS[0]) {
					// 小型船
					case 0:
						break;
					// 大型船
					case 1:
						break;
					// 飛行船
					case 2:
						break;
				}
				return 1;
			// システムBGMの変更
			case 10660:
				sBuff = new Sound();
				sBuff.NAME = e.STRING;
				sBuff.FADE_IN_TIME = e.ARGS[1];
				sBuff.VOLUME = e.ARGS[2];
				sBuff.TEMPO = e.ARGS[3];
				sBuff.BALANCE = e.ARGS[4];
				switch(e.ARGS[0]) {
					// 戦闘
					case 0:
						break;
					// 戦闘終了
					case 1:
						break;
					// 宿屋
					case 2:
						break;
					// 小型船
					case 3:
						break;
					// 大型船
					case 4:
						break;
					// 飛行船
					case 5:
						break;
					// ゲームオーバー
					case 6:
						break;
				}
				return 1;
			// システム効果音の変更
			case 10670:
				sBuff = new Sound();
				sBuff.NAME = e.STRING;
				sBuff.VOLUME = e.ARGS[1];
				sBuff.TEMPO = e.ARGS[2];
				sBuff.BALANCE = e.ARGS[3];
				switch(e.ARGS[0]) {
					// カーソル移動
					case 0:
						break;
					// 決定
					case 1:
						break;
					// キャンセル
					case 2:
						break;
					// ブザー
					case 3:
						break;
					// 戦闘開始
					case 4:
						break;
					// 逃走
					case 5:
						break;
					// 敵の通常攻撃
					case 6:
						break;
					// 敵ダメージ
					case 7:
						break;
					// 味方ダメージ
					case 8:
						break;
					// 回避
					case 9:
						break;
					// 敵消滅
					case 10:
						break;
					// アイテム使用
					case 11:
						break;
				}
				return 1;
			// システムグラフィックの変更
			case 10680:
				SPRITE.setSystem(e.STRING, e.ARGS[0], e.ARGS[1]);
				return 1;
			// 画面切り替え方式の変更
			case 10690:
				return 1;
			// 戦闘の処理（分岐開始)
			case 10710:
				return 1;
			// 戦闘の処理（勝った場合)
			case 20710:
				return 1;
			// 戦闘の処理(逃げた場合)
			case 20711:
				return 1;
			// お店の処理(分岐開始)
			case 10720:
				return 1;
			// お店の処理(売買した場合)
			case 20720:
				return 1;
			// お店の処理(売買しなかった場合)
			case 20721:
				return 1;
			// お店の処理(分岐終了)
			case 20722:
				return 1;
			// 宿屋の処理(分岐開始)
			case 10730:
				return 1;
			// 宿屋の処理(宿泊した場合)
			case 20730:
				return 1;
			// 宿屋の処理(宿泊しなかった場合)
			case 20731:
				return 1;
			// 宿屋の処理(分岐終了)
			case 20732:
				return 1;
			// 名前入力の処理
			case 10740:
				return 1;
			// 場所移動
			case 10810:
				return 1;
			// 現在の場所を記憶
			case 10820:
				return 1;
			// 記憶した場所へ移動
			case 10830:
				return 1;
			// 乗り物の乗降
			case 10840:
				return 1;
			// 乗り物の位置を設定
			case 10850:
				return 1;
			// イベントの位置を設定
			case 10860:
				return 1;
			// イベントの位置の交換
			case 10870:
				return 1;
			// 指定位置の地形ID取得
			case 10910:
				return 1;
			// 指定位置のイベントID取得
			case 10920:
				return 1;
			// 画面の消去
			case 11010:
				return 1;
			// 画面の表示
			case 11020:
				return 1;
			// 画面の色調変更
			case 11030:
				return 1;
			// 画面のフラッシュ
			case 11040:
				return 1;
			// 画面のシェイク
			case 11050:
				return 1;
			// 画面のスクロール
			case 11060:
				return 1;
			// 天候エフェクトの設定
			case 11070:
				return 1;
			// ピクチャーの表示
			case 11110:
				return 1;
			// ピクチャーの移動
			case 11120:
				return 1;
			// ピクチャーの消去
			case 11130:
				return 1;
			// 戦闘アニメの表示（非戦闘時）
			case 11210:
				return 1;
			// 主人公の透明状態変更
			case 11310:
				return 1;
			// キャラクターのフラッシュ
			case 11320:
				return 1;
			// キャラクターの動作指定
			case 11330:
				return 1;
			// 指定動作の全実行
			case 11340:
				return 1;
			// 指定動作の前解除
			case 11350:
				return 1;
			// ウェイト
			case 11410:
				return NEXT_FRAME;
			// BGMの演奏
			case 11510:
				return 1;
			// BGMのフェードアウト
			case 11520:
				return 1;
			// 現在のBGMの記憶
			case 11530:
				return 1;
			// 記憶したBGMを演奏
			case 11540:
				return 1;
			// 効果音の演奏
			case 11550:
				return 1;
			// ムービーの再生
			case 11560:
				return 1;
			// キー入力の処理
			case 11610:
				return 1;
			// チップセットの変更
			case 11710:
				return 1;
			// 遠景の変更
			case 11720:
				return 1;
			// 敵出現歩数の変更
			case 11730:
				return 1;
			// チップの置換
			case 11740:
				return 1;
			// テレポート位置の増減
			case 11810:
				return 1;
			// テレポート禁止の変更
			case 11820:
				return 1;
			// エスケープ位置の設定
			case 11830:
				return 1;
			// エスケープ禁止の変更
			case 11840:
				return 1;
			// メニュー画面の呼び出し
			case 11950:
				return 1;
			// メニュー画面禁止の変更
			case 11960:
				return 1;
			// 条件分岐(条件を満たす場合）
			case 12010:
				return 1;
			// 条件分岐(条件を満たさない場合)
			case 22010:
				return 1;
			// 条件分岐(分岐終了)
			case 22011:
				return 1;
			// ラベルの設定
			case 12110:
				return 1;
			// 指定ラベルへ飛ぶ
			case 12120:
				return 1;
			// 繰り返し処理(開始)
			case 12210:
				return 1;
			// 繰り返し処理(終端)
			case 22210:
				return 1;
			// 繰り返し処理の中断
			case 12220:
				return 1;
			// イベントの中断
			case 12310:
				return 1;
			// イベントの一時消去
			case 12320:
				return 1;
			// イベントの呼び出し
			case 12330:
				return 1;
			// 注釈・コメント（1行目）
			case 12410:
				return 0;
			// 注釈・コメント（2行目～）
			case 22410:
				return 0;
			// ゲームオーバー
			case 12420:
				SPRITE.setMode(GameMode.GAMEOVER);
				return CHANGE_MODE;
			// タイトル画面に戻る
			case 12510:
				SPRITE.setMode(GameMode.TITLE);
				return CHANGE_MODE;
			// 敵キャラのHPの増減
			case 13110:
				return 1;
			// 敵キャラのMPの増減
			case 13120:
				return 1;
			// 敵キャラの状態変更
			case 13130:
				return 1;
			// 敵キャラの出現
			case 13150:
				return 1;
			// 戦闘背景の変更
			case 13210:
				return 1;
			// 戦闘アニメの表示
			case 13260:
				return 1;
			// 戦闘時の条件分岐（条件を満たす場合）
			case 13310:
				return 1;
			// 戦闘時の条件分岐（条件を満たさない場合）
			case 23310:
				return 1;
			// 戦闘時の条件分岐（分岐終了）
			case 23311:
				return 1;
			// 戦闘の中断
			case 13410:
				return 1;
			default:
				return 1;
		}
	}
}
