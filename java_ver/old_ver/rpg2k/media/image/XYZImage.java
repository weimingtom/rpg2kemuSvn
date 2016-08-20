package rpg2k.media.image;

import java.io.*;
import java.net.URL;
import java.util.zip.InflaterInputStream;

public class XYZImage extends Image
{
	protected static final String XYZ_HEADER = "XYZ1";
	
	public XYZImage(URL file, boolean alpha) throws Exception {
		super(file, alpha);
	}
	protected boolean initImage(InputStream reader) {
		try {
			WIDTH = SHORT(reader);
			HEIGHT = SHORT(reader);
			InflaterInputStream zreader = new InflaterInputStream(reader);
			for(int i = 0; i < 256; i++)
				PALETTE[i] =  (zreader.read()<<16)
					+ (zreader.read()<<8) + zreader.read();
			PIXEL = new int[HEIGHT][WIDTH];
			for(int i = 0; i < HEIGHT; i++) for(int j = 0; j < WIDTH; j++)
				PIXEL[i][j] = zreader.read();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	protected boolean checkHeader(InputStream reader) throws Exception {
		return XYZ_HEADER.equals(new String(new byte[]{
			(byte)reader.read(), (byte)reader.read(),
			(byte)reader.read(), (byte)reader.read()}));
	}
	protected int SHORT(InputStream reader) throws Exception {
		return reader.read() | (reader.read()<<8);
	}
	protected int INT(InputStream reader) throws Exception {
		return reader.read() | (reader.read()<<8)
			| (reader.read()<<16) | (reader.read()<<24);
	}
}
