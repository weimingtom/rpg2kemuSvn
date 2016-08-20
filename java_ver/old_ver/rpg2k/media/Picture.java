package rpg2k.media;

import rpg2k.*;
import static rpg2k.Structure.*;

public class Picture
{
	// 0x01
	public String FILE_NAME = "\0";
	public boolean ALPHA = false;
	// 0x09
	public boolean VISIBLE = false;
	public Picture() {}
	public Picture(byte input[]) {
		this(get1DArray(input));
	}
	protected Picture(byte input[][]) {
		try {
			FILE_NAME = readString(input[1]);
		} catch(Exception e) {}
	}
	public static Picture[] newPicture(byte input[]) {
		byte buff[][][] = get2DArray(input);
		Picture[] ret = new Picture[buff.length];
		for(int i = 0, i_len = ret.length; i < i_len; i++)
			if(buff[i]!=null) ret[i] = new Picture(buff[i]);
		return ret;
	}
}
