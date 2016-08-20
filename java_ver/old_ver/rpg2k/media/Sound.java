package rpg2k.media;

import rpg2k.*;

public class Sound
{
	private static final String SOUND_OFF = "(OFF)";
	public Sound() {}
	public Sound(String name) {
		NAME = name;
	}
	public Sound(final byte[] data) {
		byte[][] source = Structure.get1DArray(data);
		NAME = Structure.readString(source[0x01]);
		if(!SOUND_OFF.equals(NAME)) {
			try {
				FADE_IN_TIME = Structure.ber2int(source[0x02]);
			} catch(Exception e) {}
			try {
				VOLUME = Structure.ber2int(source[0x03]);
			} catch(Exception e) {}
			try {
				TEMPO = Structure.ber2int(source[0x04]);
			} catch(Exception e) {}
			try {
				BALANCE = Structure.ber2int(source[0x05]);
			} catch(Exception e) {}
		}
	}
	// 0x01
	public String NAME;
	// 0x02
	public int FADE_IN_TIME = 0;
	// 0x03
	public int VOLUME = 100;
	// 0x04
	public int TEMPO = 100;
	// 0x05
	public int BALANCE = 50;
}
