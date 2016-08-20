package rpg2k.media.image;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import rpg2k.*;

public abstract class Image implements Field
{
	protected int PALETTE[] = new int[256];
	protected int PIXEL[][];
	protected int WIDTH;
	protected int HEIGHT;
	protected int BIT_DEPTH;
	protected BufferedImage IMAGE;
	
	protected abstract boolean initImage(InputStream reader);
	protected abstract boolean checkHeader(InputStream reader) throws Exception;
	protected Image(URL file, boolean alpha) throws Exception {
		int i = 0, j = 0;
		BufferedInputStream reader =
			new BufferedInputStream(new FileInputStream(file.getFile()));
		if((!checkHeader(reader)) || (!initImage(reader)))
			throw new Exception();
		reader.close();
		for(i = 0; i < 256; i++) PALETTE[i] |= 0xff000000;
		if(alpha) PALETTE[0] &= 0x00ffffff;
		IMAGE = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		for(i = 0; i < HEIGHT; i++) for(j = 0; j < WIDTH; j++)
			IMAGE.setRGB(j, i, PALETTE[PIXEL[i][j]]);
	}
	public java.awt.Image getImage() {
		return IMAGE;
	}
}
