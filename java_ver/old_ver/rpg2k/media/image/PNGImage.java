package rpg2k.media.image;

import java.io.*;
import java.util.*;
import java.util.zip.CRC32;
import java.util.zip.InflaterInputStream;
import java.net.URL;

public class PNGImage extends Image
{
	protected static final int PNG_HEADER[] = {
		0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a};
	protected static final String ASCII = "US-ASCII";
	
	protected Chunk CHUNK[];
	protected int COLOR_TYPE;
	protected int COMPRESSION = 0;
	protected int FILTER_FORM = 0;
	protected int INTERLACE_FORM;
	
	protected static class Chunk {
		protected int DATA_LENGTH;
		protected String NAME;
		protected byte DATA[];
		protected long CRC;
	}
	
	public PNGImage(URL file, boolean alpha) throws Exception {
		super(file, alpha);
	}
	protected boolean checkHeader(InputStream reader)
		throws Exception
	{
		for(int i = 0; i < PNG_HEADER.length; i++)
			if(reader.read() != PNG_HEADER[i]) return false;
		return true;
	}
	protected boolean initImage(InputStream reader) {
		try {
			int i = 0, j = 0;
			Vector<Chunk> buffer = new Vector<Chunk>(10, 1);
			Chunk c = null;
			while(true) {
				c = new Chunk();
				// データ長
				c.DATA_LENGTH = INT(reader);
				byte[] typeByte = new byte[4];
				reader.read(typeByte);
				// チャンク名
				c.NAME = new String(typeByte, ASCII);
				// データ
				c.DATA = new byte[c.DATA_LENGTH];
				reader.read(c.DATA);
				// CRC
				c.CRC = INT(reader) & 0x00000000ffffffffL;
				CRC32 crc32 = new CRC32();
				crc32.update(typeByte);
				crc32.update(c.DATA);
				if(crc32.getValue() != c.CRC)
					throw new IOException("This file is broken!");
				buffer.add(c);
				if(c.NAME.equals("IEND")) {
					CHUNK = buffer.toArray(new Chunk[0]);
					break;
				}
			}
			Chunk buff0 = get("IHDR");
			ByteArrayInputStream breader =
				new ByteArrayInputStream(buff0.DATA);
			WIDTH = INT(breader);
			HEIGHT = INT(breader);
			BIT_DEPTH = breader.read();
			if(BIT_DEPTH > 8) throw new Exception();
			COLOR_TYPE = breader.read();
			COMPRESSION = breader.read();
			FILTER_FORM = breader.read();
			INTERLACE_FORM = breader.read();
			buff0 = get("PLTE");
			breader = new ByteArrayInputStream(buff0.DATA);
			for(i = 0; i < buff0.DATA_LENGTH/3; i++)
				PALETTE[i] = (breader.read()<<16) | SHORT(breader);
			buff0 = get("IDAT");
			PIXEL = new int[HEIGHT][WIDTH];
			InflaterInputStream dreader =
				new InflaterInputStream(new ByteArrayInputStream(buff0.DATA));
			for(i = 0; i < HEIGHT; i++) {
				dreader.skip(1);
				for(j = 0; j < WIDTH; j++) PIXEL[i][j] = dreader.read();
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	protected int SHORT(InputStream reader) throws Exception {
		return (reader.read()<<8) | reader.read();
	}
	protected int INT(InputStream reader) throws Exception {
		return (reader.read()<<24) | (reader.read()<<16)
			| (reader.read()<<8) | reader.read();
	}
	protected String CURRENT_CHUNK = "\0";
	protected int CURRENT_CHUNK_POINT = 0;
	protected Chunk get(String name) {
		for(int i = 0; i < CHUNK.length; i++)
			if(CHUNK[i].NAME.equals(name)) {
				CURRENT_CHUNK = name;
				CURRENT_CHUNK_POINT = i;
				return CHUNK[i];
			}
		return null;
	}
}
