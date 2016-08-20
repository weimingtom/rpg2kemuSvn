package rpg2k;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import rpg2k.media.Sound;
import static rpg2k.Structure.*;

// LcfMapTree(RPGツクール2000のマップツリー)
public class MapTree extends Data implements Field
{
// ヘッダ
	private static String LMT_HEADER = "LcfMapTree";
// ファイル名
	private static String LMT_FILE_NAME = "RPG_RT.lmt";
// パーティーと乗り物の初期位置
	public int START_POINT[][] = new int[4][3];
	
	private URL GAME_DIRECTORY;
	
	public MapTree(URL dir, String file)
	{
		super();
		try {
			GAME_DIRECTORY = dir;
			BufferedInputStream reader = null;
			File lmt_file
				= new File(new URL
				(GAME_DIRECTORY.toString() + file).getFile());
			reader = new BufferedInputStream(new FileInputStream(lmt_file));
			checkHeader(reader, LMT_HEADER);
			
			// 元となるマップツリーのデータ(二次元配列\)
			SOURCE = load2DArray(reader);
			
			// 主人公と乗り物の初期位置の取得
			for(int i = 0, i_length = ber2int(reader)+1;
				 i < i_length; i++)
				ber2int(reader);
			byte[][] buffer = load1DArray(reader);
			DATA = new Object[SOURCE.length][];
			for(int i = 0, i_length = buffer.length/10+1; i < i_length; i++) {
				START_POINT[i][0] = ber2int(buffer[10*i+1]);
				START_POINT[i][1] = ber2int(buffer[10*i+2]);
				START_POINT[i][2] = ber2int(buffer[10*i+3]);
			}
			buffer = null;
			reader.close();
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public MapTree(URL dir) {
		this(dir, LMT_FILE_NAME);
	}
	public int getMapNum() {
		return SOURCE.length-1;
	}
	protected Object[] getDefaults() {
		return DEFAULT;
	}
	protected static Object[] DEFAULT = {
		null,
		"\0",
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, null, null, null, null, null, null, null,
		"\0",
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, null, null, null, null, null, null, null, null,
		25
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		Type.NULL,
		// マップ名
		// 0x01
		Type.STRING,
		// 親マップＩＤ
		// 0x02
		Type.INTEGER,
		Type.NULL,
		// マップ・エリア識別フラグ
		// 0x04
		Type.INTEGER,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		// ＢＧＭ オプション
		// 0x0B
		Type.INTEGER,
		// ＢＧＭ ファイル名
		// 0x0C
		Type.SOUND,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL,
		// 戦闘背景 オプション
		// 0x15
		Type.INTEGER,
		// 戦闘背景 ファイル名
		// 0x16
		Type.STRING,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL,
		// テレポートのオプション
		// 0x1F
		Type.INTEGER,
		// エスケープのオプション
		// 0x20
		Type.INTEGER,
		// セーブのオプション
		// 0x21
		Type.INTEGER,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL,
		// エンカウントする敵グループ(二次元配列)
		// 0x29
		Type.ENEMY_GROUP,
		Type.NULL, Type.NULL,
		// 敵出現歩数
		// 0x2C
		Type.INTEGER
	};
}
