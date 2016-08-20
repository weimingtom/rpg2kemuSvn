package rpg2k.database;

import java.io.ByteArrayInputStream;
import rpg2k.*;
import rpg2k.media.Sound;

public class SystemData		// ƒVƒXƒeƒ€
{
	protected static final int INDEX = 0x16;
	
	protected SystemData(byte[][] data) {
		BOAT = Structure.readString(data[0x0B]);
		SHIP = Structure.readString(data[0x0C]);
		AIRSHIP = Structure.readString(data[0x0D]);
		TITLE = Structure.readString(data[0x11]);
		GAMEOVER = Structure.readString(data[0x12]);
		SYSTEM = Structure.readString(data[0x13]);
		try {
			PARTY_NUMBER = Structure.ber2int(data[0x15]);
		} catch (NullPointerException e) {}
		PARTY = new int[PARTY_NUMBER];
		ByteArrayInputStream stream = new ByteArrayInputStream(data[0x16]);
		int i = 0;
		while(i < PARTY_NUMBER)
			PARTY[i++] = stream.read() + (stream.read()<<8);
		TITLE_BGM = new Sound(data[0x1f]);
		BATTLE_BGM = new Sound(data[0x20]);
		BATTLE_END_BGM = new Sound(data[0x21]);
		INN_BGM = new Sound(data[0x22]);
		BOAT_BGM = new Sound(data[0x23]);
		SHIP_BGM = new Sound(data[0x24]);
		AIRSHIP_BGM = new Sound(data[0x25]);
		GAMEOVER_BGM = new Sound(data[0x26]);
		CURSOR_SE = new Sound(data[0x29]);
		ENTER_SE = new Sound(data[0x2a]);
		CANCEL_SE = new Sound(data[0x2b]);
		BUZZER_SE = new Sound(data[0x2c]);
		BATTLE_START_SE = new Sound(data[0x2d]);
		ESCAPE_SE = new Sound(data[0x2e]);
		NORMAL_ATTACK_SE = new Sound(data[0x2f]);
		ENEMY_DAMAGE_SE = new Sound(data[0x30]);
		ALLY_DAMAGE_SE = new Sound(data[0x31]);
		EVADE_SE = new Sound(data[0x32]);
		ENEMY_VANISH_SE = new Sound(data[0x33]);
		ITEM_SE = new Sound(data[0x34]);
		try {
			MOVE_ERASE = Structure.ber2int(data[0x3d]);
		} catch (NullPointerException e) {}
		try {
			MOVE_DISPLAY = Structure.ber2int(data[0x3e]);
		} catch (NullPointerException e) {}
		try {
			BATTLE_START_ERASE = Structure.ber2int(data[0x3f]);
		} catch (NullPointerException e) {}
		try {
			BATTLE_START_DISPLAY = Structure.ber2int(data[0x40]);
		} catch (NullPointerException e) {}
		try {
			BATTLE_END_ERASE = Structure.ber2int(data[0x41]);
		} catch (NullPointerException e) {}
		try {
			BATTLE_END_DISPLAY = Structure.ber2int(data[0x42]);
		} catch (NullPointerException e) {}
		
		try {
			WALLPAPER = Structure.ber2int(data[0x47]);
		} catch (NullPointerException e) {}
		try {
			FONT = Structure.ber2int(data[0x48]);
		} catch (NullPointerException e) {}
		SAVE_TIME = Structure.ber2int(data[0x5B]);
	}
	
	// 0x0B
	public String BOAT;
	// 0x0C
	public String SHIP;
	// 0x0D
	public String AIRSHIP;
	// 0x0E
	public int BOAT_LOCATION = 0;
	// 0x0F
	public int SHIP_LOCATION = 0;
	// 0x10
	public int AIRSHIP_LOCATION = 0;
	// 0x11
	public String TITLE;
	// 0x12
	public String GAMEOVER;
	// 0x13
	public String SYSTEM;
	// 0x15
	public int PARTY_NUMBER = 0;
	// 0x16
	public int PARTY[];
	// 0x1F
	public Sound TITLE_BGM;
	// 0x20
	public Sound BATTLE_BGM;
	// 0x21
	public Sound BATTLE_END_BGM;
	// 0x22
	public Sound INN_BGM;
	// 0x23
	public Sound BOAT_BGM;
	// 0x24
	public Sound SHIP_BGM;
	// 0x25
	public Sound AIRSHIP_BGM;
	// 0x26
	public Sound GAMEOVER_BGM;
	// 0x29
	public Sound CURSOR_SE;
	// 0x2A
	public Sound ENTER_SE;
	// 0x2B
	public Sound CANCEL_SE;
	// 0x2C
	public Sound BUZZER_SE;
	// 0x2D
	public Sound BATTLE_START_SE;
	// 0x2E
	public Sound ESCAPE_SE;
	// 0x2F
	public Sound NORMAL_ATTACK_SE;
	// 0x30
	public Sound ENEMY_DAMAGE_SE;
	// 0x31
	public Sound ALLY_DAMAGE_SE;
	// 0x32
	public Sound EVADE_SE;
	// 0x33
	public Sound ENEMY_VANISH_SE;
	// 0x34
	public Sound ITEM_SE;
	// 0x3D
	public int MOVE_ERASE = 0;
	// 0x3E
	public int MOVE_DISPLAY = 0;
	// 0x3F
	public int BATTLE_START_ERASE = 0;
	// 0x40
	public int BATTLE_START_DISPLAY = 0;
	// 0x41
	public int BATTLE_END_ERASE = 0;
	// 0x42
	public int BATTLE_END_DISPLAY = 0;
	// 0x47
	// 0:Šg‘å‚µ‚Ä•\Ž¦
	// 1:•À‚×‚Ä•\Ž¦
	public int WALLPAPER = 0;
	// 0x48
	// 0:‚l‚r ƒSƒVƒbƒN
	// 1:‚l‚r –¾’©
	public int FONT = 0;
	// 0x5B
	public int SAVE_TIME;
}
