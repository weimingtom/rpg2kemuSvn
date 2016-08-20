package rpg2k;

import java.io.*;
import rpg2k.media.*;
import static rpg2k.Structure.*;

public abstract class Data implements Field
{
	// バッファ
	protected Object[][] DATA;
	protected Object[] CURRENT_DATA;
	protected int CURRENT_ID;
	protected byte[][][] SOURCE;
	protected enum Type {
		// 基本データ
		NULL, INTEGER, STRING, FLAG,
		// マップデータ
		ENEMY_GROUP, MAP, MAP_EVENT, MAP_EVENT_PAGE, ROUTE, REQUIRE,
		// イベント
		EVENT,
		// 攻撃パターン
		ATTACK_PATTERN,
		// チップセット
		CHIP_BLOCK, CHIP_LANDFORM,
		// イベント
		// セーブデータ
		SAVE_DATA_HEADER, SAVE_DATA_MENU,
		// 素材
		SOUND, PICTURE_ARRAY,
		// 位置情報
		LOCATION, LOCATION_ARRAY
	}
	// デフォルトを取得
	protected abstract Object[] getDefaults();
	protected Object getDefault(int index) {
		return getDefaults()[index];
	}
	// 型を取得
	protected abstract Type[] getTypes();
	protected Type getType(int index) {
		return getTypes()[index];
	}
	// コンストラクタ
	protected Data(byte[][][] src) {
		DATA = new Object[src.length][];
		SOURCE = src;
	}
	// オーバーライドして使うコンストラクタ
	protected Data() {}
	// 現在のデータの ID の指定
	public void setCurrent(int id) {
		CURRENT_DATA = getData(id);
		CURRENT_ID = id;
	}
	// 整数の取得
	public int getInteger(int index) {
		return (Integer) CURRENT_DATA[index];
	}
	// 文字列の取得
	public String getString(int index) {
		return (String) CURRENT_DATA[index];
	}
	// boolean の取得
	public boolean getBoolean(int index) {
		return (Boolean) CURRENT_DATA[index];
	}
	// データの取得
	public Object get(int index) {
		return CURRENT_DATA[index];
	}
	// データを取得
	public Object[] getData(int id) {
		if(DATA[id]==null) {
			DATA[id] = loadData(id);
			SOURCE[id] = null;
		}
		return DATA[id];
	}
	// データを読み込む
	protected Object[] loadData(int id) {
		if(SOURCE[id]==null) return null;
		int i = 0, j = 0, k = 0;
		byte[] src = null; byte[][] a1src = null; byte[][][] a2src = null;
		ByteArrayInputStream stream =  null;
		int[] a1ret = null; int[][] a2ret = null;
		Object[] def = getDefaults();
		Type[] type = getTypes();
		Object[] ret = new Object[type.length];
		try {
		for(i = 0; true; i++) {
			src = SOURCE[id][i];
			if(src==null) {
				ret[i] = def[i];
				continue;
			}
			switch(type[i]) {
				case NULL:
					break;
				case INTEGER:
					ret[i] = ber2int(src);
					break;
				case STRING:
					ret[i] = readString(src);
					break;
				case FLAG:
					ret[i] = src[0]!=0 ? true : false;
					break;
				case MAP:
					a2ret = new int[(Integer)ret[0x03]][(Integer)ret[0x02]];
					stream = new ByteArrayInputStream(src);
					for(j = 0; j < a2ret.length; j++)
						for(k = 0; k < a2ret[j].length; k++)
							a2ret[j][k] = stream.read() | (stream.read()<<8);
					ret[i] = a2ret;
					break;
				case ENEMY_GROUP:
					a2src = get2DArray(src);
					a1ret = new int[a2src.length];
					for(j = 0; j < a1ret.length; i++)
						a1ret[i] = ber2int(a2src[i][1]);
					ret[i] = a1ret;
					break;
				case CHIP_BLOCK:
					a1ret = new int[src.length/2];
					stream = new ByteArrayInputStream(src);
					for(j = 0; j < a1ret.length; j++)
						a1ret[j] = stream.read() | (stream.read()<<8);
					ret[i] = a1ret;
					break;
				case CHIP_LANDFORM:
					a1ret = new int[src.length];
					stream = new ByteArrayInputStream(src);
					for(j = 0; j < a1ret.length; j++)
						a1ret[j] = stream.read();
					ret[i] = a1ret;
					break;
				case MAP_EVENT:
					ret[i] = new MapUnit.MapEvent(src);
					break;
				case MAP_EVENT_PAGE:
					ret[i] = new MapUnit.MapEvent.Page(src);
					break;
				case ROUTE:
					ret[i] = new MapUnit.MapEvent.Page.Route(src);
					break;
				case REQUIRE:
					ret[i] = new MapUnit.MapEvent.Page.Require(src);
				case EVENT:
					ret[i] = Event.newEvent(src);
					break;
				case ATTACK_PATTERN:
					ret[i] = AttackPattern.	newAttackPattern(src);
					break;
				case SOUND:
					ret[i] = new Sound(src);
					break;
				case PICTURE_ARRAY:
					// ret[i] = Picture.newPicture(src);
					break;
				case SAVE_DATA_HEADER:
					ret[i] = new SaveData.Header(src);
					break;
				case SAVE_DATA_MENU:
					ret[i] = new SaveData.Menu(src);
					break;
				case LOCATION:
					ret[i] = new SaveData.Location(src);
					break;
				case LOCATION_ARRAY:
/*					a2ret = get2DArray(src);
					SaveData.Location[] ret4 =
						new SaveData.Location[buff4.length];
					for(j = 0; j < ret4.length; j++) if(buff4[j]!=null)
						ret4[j] = new SaveData.Location(buff4[j]);
					ret[i] = ret4;
*/					break;
				default:
					ret[i] = null;
			}
		}
		} catch(ArrayIndexOutOfBoundsException e) {}
		return ret;
	}
	// データの書き込み
	protected ByteArrayOutputStream store(int id) throws Exception {
		int i = 0, j = 0, k = 0;
		Type[] defType = getTypes();
		Object[] src = DATA[id];
		byte[][] buff = new byte[defType.length][];
		for(i = 0; i < defType.length; i++) {
			switch(defType[i]) {
				case NULL:
					continue;
				case INTEGER:
					buff[i] = int2ber((Integer)src[i]);
			}
		}
		return store1DArray(buff);
	}
}
