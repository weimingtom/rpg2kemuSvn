package rpg2k;

import java.awt.Image;
import java.io.*;
import java.net.URL;
import rpg2k.analyze.Format;
import rpg2k.media.Picture;
import rpg2k.sprite.Sprite;
import static rpg2k.Structure.*;

public class SaveData extends Data implements Field
{
	private static String LSD_HEADER = "LcfSaveData";
	protected boolean DATA_EXIST[] = new boolean[SAVE_DATA_MAX];
	private URL GAME_DIRECTORY;
	private Project GAME_DATA;
	public DataBuff BUFF;
	private MapTree MAP_TREE;
	private MapUnit MAP_UNIT;
	private Sprite SPRITE;
	private rpg2k.media.Media MEDIA;
	
	public SaveData(URL dir) {
		GAME_DIRECTORY = dir;
		DATA = new Object[SAVE_DATA_MAX][];
		SOURCE = new byte[SAVE_DATA_MAX][][];
		BufferedInputStream reader = null;
		File lsd_file = null;
		for(int i = 0; i < SAVE_DATA_MAX; i++) {
			try {
				DATA_EXIST[i] = new File(new URL(GAME_DIRECTORY
					+getFileName(i)).getFile()).exists();
				if(!DATA_EXIST[i]) continue;
				lsd_file = new File(
					new URL(GAME_DIRECTORY+getFileName(i)).getFile());
				reader = new BufferedInputStream(new FileInputStream(lsd_file));
				checkHeader(reader, LSD_HEADER);
				SOURCE[i] = load1DArray(reader);
				reader.close();
			} catch (Exception e) {}
		}
		BUFF = new DataBuff();
	}
	public boolean exists(int num) {
		return DATA_EXIST[num];
	}
	private String getFileName(int num) {
		if((num < 0) || (num > SAVE_DATA_MAX)) return "\0";
		return "Save"+Format.dec0(num+1, 2)+".lsd";
	}
	public void setCurrent(int id) {
		super.setCurrent(id);
		BUFF = new DataBuff();
		Location loc = (Location) get(0x68);
		moveMap(loc);
		Menu menu = (Menu) get(0x6d);
		BUFF.PARTY_NUMBER = menu.PARTY_NUMBER;
		BUFF.PARTY = menu.PARTY;
		BUFF.MONEY = menu.MONEY;
	}
	public void startNewGame() {
		BUFF = new DataBuff();
		Location buff = new SaveData.Location();
		buff.MAP_ID = MAP_TREE.START_POINT[0][0];
		buff.X = MAP_TREE.START_POINT[0][1];
		buff.Y = MAP_TREE.START_POINT[0][2];
		moveMap(buff);
		BUFF.PARTY = GAME_DATA.SYS_DATA.PARTY;
		BUFF.PARTY_NUMBER = GAME_DATA.SYS_DATA.PARTY_NUMBER;
	}
	public void setGameData(Project data) {
		GAME_DATA = data;
		MAP_TREE = data.MAP_TREE;
		MAP_UNIT = data.MAP_UNIT;
	}
	public void setSprite(Sprite s) {
		SPRITE = s;
		MEDIA = s.MEDIA;
	}
	public void moveMap(Location l) {
		MAP_UNIT.setCurrent(l.MAP_ID);
		BUFF.CHIP_NUM = MAP_UNIT.getInteger(1);
		BUFF.LOWER_MAP = (int[][]) MAP_UNIT.get(0x47);
		BUFF.UPPER_MAP = (int[][]) MAP_UNIT.get(0x48);
		BUFF.PANORAMA = MAP_UNIT.getBoolean(0x1f)
			? MEDIA.getPanorama(MAP_UNIT.getString(0x20)) : null;
		int buff = 0;
		MAP_TREE.setCurrent(l.MAP_ID);
		buff = MAP_TREE.getInteger(0x1f);
		while(buff == 0) {
			MAP_TREE.setCurrent(MAP_TREE.getInteger(2));
			buff = MAP_TREE.getInteger(0x1f);
		}
		BUFF.CAN_TELEPORT = buff==1 ? true : false;
		MAP_TREE.setCurrent(l.MAP_ID);
		buff = MAP_TREE.getInteger(0x20);
		while(buff == 0) {
			MAP_TREE.setCurrent(MAP_TREE.getInteger(2));
			buff = MAP_TREE.getInteger(0x20);
		}
		BUFF.CAN_ESCAPE = buff==1 ? true : false;
		MAP_TREE.setCurrent(l.MAP_ID);
		buff = MAP_TREE.getInteger(0x21);
		while(buff == 0) {
			MAP_TREE.setCurrent(MAP_TREE.getInteger(2));
			buff = MAP_TREE.getInteger(0x21);
		}
		BUFF.CAN_SAVE = buff==1 ? true : false;
		MAP_TREE.setCurrent(l.MAP_ID);
		BUFF.LOCATION = l;
		BUFF.SCREEN_X = l.X - 10;
		BUFF.SCREEN_Y = l.Y - 7;
		BUFF.HERO = MEDIA.getCharSet(l.GRAPHIC, l.GRAPHIC_LOCATION);
		GAME_DATA.CHIP_SET.setCurrent(BUFF.CHIP_NUM);
		MEDIA.setChipSet(GAME_DATA.CHIP_SET.getString(2));
	}
	protected Object[] getDefaults() {
		return DEFAULT;
	}
	protected static final Object[] DEFAULT = {
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, null, null, null, null, null, null,
		new Picture[PICTURE_MAX]
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		// ヘッダー
		// 0x64
		Type.SAVE_DATA_HEADER,
		// システム情報
		// 0x65
		Type.NULL,
		// 
		// 0x66
		Type.NULL,
		// ピクチャ情報
		// 0x67
		Type.PICTURE_ARRAY,
		// 主人公の情報
		// 0x68
		Type.LOCATION,
		// 小型船の情報
		// 0x69
		Type.LOCATION,
		// 大型船の情報
		// 0x6a
		Type.LOCATION,
		// 飛行船の情報
		// 0x6b
		Type.LOCATION,
		// 主人公の情報
		// 0x6c
		Type.NULL,
		// アイテム情報
		// 0x6d
		Type.SAVE_DATA_MENU,
		// 
		// 0x6e
		Type.NULL,
		// イベント情報
		// 0x6f
		Type.LOCATION_ARRAY,
		// 
		// 0x70
		Type.NULL,
		// 
		// 0x71
		Type.NULL,
	};
	public static class DataBuff
	{
		public int[][] UPPER_MAP, LOWER_MAP;
		public Image PANORAMA;
		public Image[][] HERO;
		public Location LOCATION;
		public int CHIP_NUM, SCREEN_X, SCREEN_Y;
		public boolean CAN_SAVE, CAN_TELEPORT, CAN_ESCAPE, CAN_OPEN_MENU = true;
		public int PARTY_NUMBER, MONEY;
		public int[] PARTY;
		public int[] VARIABLE = new int[LDB_MAX_NUMBER];
		public boolean[] SWITCH = new boolean[LDB_MAX_NUMBER];
	}
	public static class Header
	{
		protected Header(byte[] data) {
			byte[][] buff0 = get1DArray(data);
			try {
				CHARACTER_NAME = readString(buff0[0x0b]);
			} catch(NullPointerException e) {}
			try {
				LEVEL = ber2int(buff0[0x0c]);
			} catch(NullPointerException e) {}
			try {
				HP = ber2int(buff0[0x0d]);
			} catch(NullPointerException e) {}
			for(int i = 0, i_len = 4; i < i_len; i++) {
				try {
					FACE_SET[i] = readString(buff0[0x15+i*2]);
				} catch(Exception e) {
					FACE_SET[i] = null;
				}
				try {
					FACE_SET_NUM[i] = ber2int(buff0[0x16+i*2]);
				} catch(Exception e) {}
			}
		}
		protected Header() {}
		
		// 0x0B
		public String CHARACTER_NAME = "\0";
		// 0x0C
		public int LEVEL = 0;
		// 0x0D
		public int HP = 0;
		// 0x15, 0x17, 0x19, 0x1B
		public String[] FACE_SET = new String[4];
		// 0x16, 0x18, 0x1A, 0x1C
		public int[] FACE_SET_NUM = new int[4];
	}
	public static class Location
	{
		protected Location(byte[] input) {
			byte[][] buff0 = get1DArray(input);
			try {
				MAP_ID = ber2int(buff0[0x0b]);
			} catch(Exception e) {}
			X = ber2int(buff0[0x0c]);
			Y = ber2int(buff0[0x0d]);
			try {
				DIRECTION = ber2int(buff0[0x17]);
			} catch(Exception e) {}
			try {
				PATTERN = ber2int(buff0[0x18]);
			} catch(Exception e) {}
			try {
				GRAPHIC = readString(buff0[0x49]);
			} catch(Exception e) {}
			try {
				GRAPHIC_LOCATION = ber2int(buff0[0x4a]);
			} catch(Exception e) {}
		}
		public Location() {}
		// 0x0B
		public int MAP_ID = 0;
		// 0x0C
		public int X = 0;
		// 0x0D
		public int Y = 0;
		// 0x16
		public int DIRECTION = 2;
		// 0x17
		public int PATTERN = 1;
		// 0x49
		public String GRAPHIC = "\0";
		// 0x4A
		public int GRAPHIC_LOCATION = 0;
	}
	public static class Menu
	{
		protected Menu(byte[] input) {
			int i = 0;
			byte buff[][] = get1DArray(input);
			PARTY_NUMBER = ber2int(buff[1]);
			PARTY = new int[PARTY_NUMBER];
			ByteArrayInputStream stream = new ByteArrayInputStream(buff[2]);
			while(i < PARTY_NUMBER)
				PARTY[i++] = stream.read() + (stream.read()<<8);
			try {
				MONEY = ber2int(buff[0x15]);
			} catch(Exception e) {}
		}
		public Menu() {}
		// 0x01
		public int PARTY_NUMBER;
		// 0x02
		public int PARTY[];
		// 0x15
		public int MONEY = 0;
	}
}

