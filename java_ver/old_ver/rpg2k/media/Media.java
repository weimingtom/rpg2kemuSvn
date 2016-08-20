package rpg2k.media;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Hashtable;
import rpg2k.*;
import rpg2k.media.image.PNGImage;
import rpg2k.media.image.XYZImage;

public final class Media implements Field
{
	private URL DIRECTORY, RTP_DIRECTORY;
	private Project GAME_DATA;
	private Image SYS_GRP;
	private int WALLPAPER;
	// チップ系の情報
	private String CURRENT_CHIPSET_FILENAME;
	private Image[][] CURRENT_CHIP;
	private Image CURRENT_CHIPSET_IMAGE;
	// 素材の読み込みバッファ
	private Hashtable<String, Image> MEDIA_BUFF[] = new Hashtable[14];
	// create() のバッファ
	private Hashtable<String, Image> CREATE_BUFF[] = new Hashtable[3];
	// 顔グラフィックの読み込みバッファ
	private Hashtable<String, Image>
		FACE_SET_BUFF = new Hashtable<String, Image>();
	// キャラグラフィックの読み込みバッファ
	private Hashtable<String, Image[][]>
		CHAR_SET_BUFF = new Hashtable<String, Image[][]>();
	// チップセット読み込みバッファ
	private Hashtable<String, Image[][]>
		CHIP_SET_BUFF = new Hashtable<String, Image[][]>();
	// コンストラクタ
	public Media(Project gameData) {
		DIRECTORY = gameData.GAME_DIRECTORY;
		RTP_DIRECTORY = gameData.RTP_DIRECTORY;
		GAME_DATA = gameData;
		for(int i = 0; i < 14; i++)
			MEDIA_BUFF[i] = new Hashtable<String, java.awt.Image>();
	}
	// ピクチャの透明色
	private boolean PICTURE_ALPHA = false;
	// タイトルグラフィックの取得
	public Image getTitle() {
		return getImage(GAME_DATA.SYS_DATA.TITLE, Material.TITLE);
	}
	// ゲームオーバーグラフィックの取得
	public Image getGameover() {
		return getImage(GAME_DATA.SYS_DATA.GAMEOVER, Material.GAMEOVER);
	}
	// パノラマグラフィックの取得
	public Image getPanorama(String file) {
		return getImage(file, Material.PANORAMA);
	}
	// キャラセットの取得
	public Image[][] getCharSet(String file, int loc) {
		Image ret[][] = CHAR_SET_BUFF.get(file+loc);
		if(ret==null) {
			ret = createCharSet(file, loc);
			CHAR_SET_BUFF.put(file+loc, ret);
		}
		return ret;
	}
	public Image[][] createCharSet(String file, int loc) {
		int i = 0, j = 0;
		Image[][] ret = new Image[3][4];
		int cx = loc%4*72, cy = loc/4*128;
		Image src = getImage(file, Material.CHARSET);
		for(i = 0; i < 4; i++) {
			for(j = 0; j < 3; j++) {
				ret[j][i] = new BufferedImage(24, 32, IMAGE_TYPE);
				ret[j][i].getGraphics().drawImage(src, 0, 0, 24, 32,
					cx+j*24, cy, cx+j*24+24, cy+32, null);
			}
			cy += 32;
		}
		return ret;
	}
	// チップセットの取得
	public void setChipSet(String file) {
		try {
			CHIP_SET_BUFF.put(CURRENT_CHIPSET_FILENAME, CURRENT_CHIP);
		} catch(NullPointerException e) {}
		CURRENT_CHIPSET_FILENAME = file;
		CURRENT_CHIP = CHIP_SET_BUFF.get(file);
		CURRENT_CHIPSET_IMAGE = getImage(file, Material.CHIPSET);
		if(CURRENT_CHIP==null) CURRENT_CHIP = new Image[4][CHIP_MAX];
	}
	public Image getChip(int num, int anime) {
		if(CURRENT_CHIP[anime][num]==null)
			CURRENT_CHIP[anime][num] = createChip(num, anime);
		return CURRENT_CHIP[anime][num];
	}
	private Image createChip(int num, int anime) {
		BufferedImage ret = new BufferedImage(16, 16, IMAGE_TYPE);
		Graphics g = ret.getGraphics();
		if(num < 3000) {
			paintOcean(g, CURRENT_CHIPSET_IMAGE, anime,
				num/1000, (num%1000)/50, (num%1000)%50);
			paintCoastline(g, CURRENT_CHIPSET_IMAGE, anime,
				num/1000, (num%1000)/50, (num%1000)%50);
		} else if(num < 4000) {
			int cx = (num-3028)/50*16+64, cy = 64+anime*16;
			g.drawImage(CURRENT_CHIPSET_IMAGE,
				0, 0, 16, 16, cx, cy, cx+16, cy+16, null);
		} else if(num < 5000) {
			paintLandform(g,
				CURRENT_CHIPSET_IMAGE, (num-4000)/50, (num-4000)%50);
		} else if(num < 5096) {
			int cx = 192+((num-5000)%6)*16, cy = ((num-5000)/6)*16;
			g.drawImage(CURRENT_CHIPSET_IMAGE,
				0, 0, 16, 16, cx, cy, cx+16, cy+16, null);
		} else if(num < 5144) {
			int cx = 288+((num-5096)%6)*16, cy = ((num-5096)/6)*16;
			g.drawImage(CURRENT_CHIPSET_IMAGE,
				0, 0, 16, 16, cx, cy, cx+16, cy+16, null);
		} else if(num < 10048) {
			int cx = 288 + ((num-10000)%6)*16, cy = 128 + ((num-10000)/6)*16;
			g.drawImage(CURRENT_CHIPSET_IMAGE,
				0, 0, 16, 16, cx, cy, cx+16, cy+16, null);
		} else if(num < 10144) {
			int cx = 384+((num-10048)%6)*16, cy = ((num-10048)/6)*16;
			g.drawImage(CURRENT_CHIPSET_IMAGE,
				0, 0, 16, 16, cx, cy, cx+16, cy+16, null);
		}
		return ret;
	}
	// 一般地形チップの描画
	private void paintLandform(Graphics g, Image img, int x, int y) {
		int i = 0, srcX = 0, srcY = 0,
			bx = x<4 ? x%2*48 : x%2*48+96,
			by = x<4 ? x/2*64+128 : (x-4)/2*64;
		int src[][] = new int[4][];
		int[] d = null;
		if(y < 0x10) {
			for(i = 0; i < 4; i++) {
				if((y&(1<<i)) != 0) src[i] = __C;
				else src[i] = __D5;
			}
		} else if(y < 0x14) {
			src[_0_0] = src[_0_1] = __D4;
			if((y&0x01) != 0) src[_1_0] = __C;
			else src[_1_0] = __D5;
			if((y&0x02) != 0) src[_1_1] = __C;
			else src[_1_1] = __D5;
		} else if(y < 0x18) {
			src[_0_0] = src[_1_0] = __D8;
			if((y&0x01) != 0) src[_1_1] = __C;
			else src[_1_1] = __D5;
			if((y&0x02) != 0) src[_0_1] = __C;
			else src[_0_1] = __D5;
		} else if(y < 0x1C) {
			src[_1_0] = src[_1_1] = __D6;
			if((y&0x01) != 0) src[_0_1] = __C;
			else src[_0_1] = __D5;
			if((y&0x02) != 0) src[_0_0] = __C;
			else src[_0_0] = __D5;
		} else if(y < 0x20) {
			src[_0_1] = src[_1_1] = __D2;
			if((y&0x01) != 0) src[_0_0] = __C;
			else src[_0_0] = __D5;
			if((y&0x02) != 0) src[_1_0] = __C;
			else src[_1_0] = __D5;
		} else if(y < 0x21) {
			src[_0_0] = src[_0_1] = __D4;
			src[_1_0] = src[_1_1] = __D6;
		} else if(y < 0x22) {
			src[_0_0] = src[_1_0] = __D8;
			src[_0_1] = src[_1_1] = __D2;
		} else if(y < 0x24) {
			src[_0_0] = src[_1_0] = src[_0_1] = __D7;
			if((y&0x01) != 0) src[_1_1] = __C;
			else src[_1_1] = __D7;
		} else if(y < 0x26) {
			src[_0_0] = src[_1_0] = src[_1_1] = __D9;
			if((y&0x01) != 0) src[_0_1] = __C;
			else src[_0_1] = __D9;
		} else if(y < 0x28) {
			src[_1_0] = src[_0_1] = src[_1_1] = __D3;
			if((y&0x01) != 0) src[_0_0] = __C;
			else src[_0_0] = __D3;
		} else if(y < 0x2a) {
			src[_0_0] = src[_0_1] = src[_1_1] = __D1;
			if((y&0x01) != 0) src[_1_0] = __C;
			else src[_1_0] = __D1;
		} else if(y < 0x2b) {
			src[_0_0] = src[_0_1] = __D7;
			src[_1_0] = src[_1_1] = __D9;
		} else if(y < 0x2c) {
			src[_0_0] = src[_1_0] = __D7;
			src[_0_1] = src[_1_1] = __D1;
		} else if(y < 0x2d) {
			src[_0_0] = src[_0_1] = __D1;
			src[_1_0] = src[_1_1] = __D3;
		} else if(y < 0x2e) {
			src[_0_0] = src[_1_0] = __D9;
			src[_0_1] = src[_1_1] = __D3;
		} else if(y < 0x2f) {
			src[_0_0] = __D7;
			src[_1_0] = __D9;
			src[_0_1] = __D1;
			src[_1_1] = __D3;
		}
		for(i = 0; i < 4; i++) {
			srcX = src[i][0]+bx; srcY = src[i][1]+by;
			d = _4_4[i];
			g.drawImage(img, d[0], d[1], d[2], d[3],
				srcX+d[0], srcY+d[1], srcX+d[2], srcY+d[3], null);
		}
	}
	// 海岸線の描画
	private void paintCoastline(
		Graphics g, Image img, int anime, int x, int y, int z
	) {
		int i = 0, srcY = 0,
			bx = 16*anime+(x!=1 ? 0 : 48), srcX = bx, by = 0;
		int src[][] = new int[4][1];
		int[] d = null;
		if(z < 0x10) {
			for(i = 0; i < 4; i++) {
				if((z&(1<<i)) != 0) src[i][0] = _D;
				else src[i] = null;
			}
		} else if(z < 0x14) {
			src[_0_0][0] = src[_0_1][0] = _B;
			if((z&0x01) != 0) src[_1_0][0] = _D;
			else src[_1_0] = null;
			if((z&0x02) != 0) src[_1_1][0] = _D;
			else src[_1_1] = null;
		} else if(z < 0x18) {
			src[_0_0][0] = src[_1_0][0] = _C;
			if((z&0x01) != 0) src[_1_1][0] = _D;
			else src[_1_1] = null;
			if((z&0x02) != 0) src[_0_1][0] = _D;
			else src[_0_1] = null;
		} else if(z < 0x1C) {
			src[_1_0][0] = src[_1_1][0] = _B;
			if((z&0x01) != 0) src[_0_1][0] = _D;
			else src[_0_1] = null;
			if((z&0x02) != 0) src[_0_0][0] = _D;
			else src[_0_0] = null;
		} else if(z < 0x20) {
			src[_0_1][0] = src[_1_1][0] = _C;
			if((z&0x01) != 0) src[_0_0][0] = _D;
			else src[_0_0] = null;
			if((z&0x02) != 0) src[_1_0][0] = _D;
			else src[_1_0] = null;
		} else if(z < 0x21) {
			src[i][0] = src[i][1] = src[i][2] = src[i][3] = _B;
		} else if(z < 0x22) {
			src[i][0] = src[i][1] = src[i][2] = src[i][3] = _C;
		} else if(z < 0x24) {
			src[_0_0][0] = _A;
			src[_1_0][0] = _C;
			src[_0_1][0] = _B;
			if((z&0x01) != 0) src[_1_1][0] = _D;
			else src[_1_1] = null;
		} else if(z < 0x26) {
			src[_0_0][0] = _C;
			src[_1_0][0] = _A;
			if((z&0x01) != 0) src[_0_1][0] = _D;
			else src[_0_1] = null;
			src[_1_1][0] = _B;
		} else if(z < 0x28) {
			if((z&0x01) != 0) src[_0_0][0] = _D;
			else src[_0_0] = null;
			src[_1_0][0] = _B;
			src[_0_1][0] = _C;
			src[_1_1][0] = _A;
		} else if(z < 0x2a) {
			src[_0_0][0] = _B;
			if((z&0x01) != 0) src[_1_0][0] = _D;
			else src[_1_0] = null;
			src[_0_1][0] = _A;
			src[_1_1][0] = _C;
		} else if(z < 0x2b) {
			src[_0_0][0] = src[_1_0][0] = _A;
			src[_0_1][0] = src[_1_1][0] = _B;
		} else if(z < 0x2c) {
			src[_0_0][0] = src[_0_1][0] = _A;
			src[_1_0][0] = src[_1_1][0] = _C;
		} else if(z < 0x2d) {
			src[_0_0][0] = src[_1_0][0] = _B;
			src[_0_1][0] = src[_1_1][0] = _A;
		} else if(z < 0x2e) {
			src[_0_0][0] = src[_0_1][0] = _C;
			src[_1_0][0] = src[_1_1][0] = _A;
		} else if(z < 0x2f) {
			src[0][0] = src[1][0] = src[2][0] = src[3][0] = _A;
		}
		for(i = 0; i < 4; i++) {
			try {
				srcY = src[i][0]+by;
				d = _4_4[i];
				g.drawImage(img, d[0], d[1], d[2], d[3],
					srcX+d[0], srcY+d[1], srcX+d[2], srcY+d[3], null);
			} catch(NullPointerException e) {}
		}
	}
	// 海の描画
	private void paintOcean(
		Graphics g, Image src, int anime, int x, int y, int z
	) {
		int i = 0,
			bx0 = 16*anime, by0 = x!=2 ? 64+_A : 64+_D,
			bx1 = 16*anime, by1 = x!=2 ? 64+_B : 64+_C;
		int[] d = null;
		for(i = 0; i < 4; i++) {
			d = _4_4[i];
			if((y&(1<<i)) != 0) g.drawImage(src, d[0], d[1], d[2], d[3],
				bx0+d[0], by0+d[1], bx0+d[2], by0+d[3], null);
			else g.drawImage(src, d[0], d[1], d[2], d[3],
				bx1+d[0], by1+d[1], bx1+d[2], by1+d[3], null);
		}
	}
	// システムグラフィックの取得
	public Image getSystem(String file, int wallpaper) {
		Image ret = getImage(file, Material.SYSTEM);
		// ついでに各設定を設定
		SYS_GRP = ret;
		WALLPAPER = wallpaper;
		for(int i = 0; i < CREATE_BUFF.length; i++)
			CREATE_BUFF[i] = new Hashtable<String, java.awt.Image>();
		return ret;
	}
	public Image getFaceSet(String name, int num) {
		String key = name + String.valueOf(num);
		BufferedImage ret = (BufferedImage)FACE_SET_BUFF.get(key);
		if(ret==null) {
			int srcX = (num%4)*48, srcY = (num/4)*48;
			ret = new BufferedImage(48, 48, IMAGE_TYPE);
			ret.getGraphics().drawImage(getImage(name, Material.FACESET),
				0, 0, 48, 48, srcX, srcY, srcX+48, srcY+48, null);
			FACE_SET_BUFF.put(key, ret);
		}
		return ret;
	}
	private String getKey(int width, int height) {
		return new StringBuffer(String.valueOf(width))
			.append("x").append(height).toString();
	}
	// create() の type
//	private static final int WINDOW = 1, CURSOR = 2, CURSOR0 = 3;
	private enum CreateType {
		WINDOW, CURSOR, CURSOR0;
		private int getStartX() {
			return (ordinal()+1)*32;
		}
	};
	// ウィンドウの取得
	public Image getWindow(int width, int height) {
		String keyBuff = getKey(width, height);
		Image buff = (Image)
			CREATE_BUFF[CreateType.WINDOW.ordinal()].get(keyBuff);
		if(buff==null) {
			buff = create(CreateType.WINDOW, width, height);
			CREATE_BUFF[CreateType.WINDOW.ordinal()].put(keyBuff, buff);
		}
		return buff;
	}
	// カーソルの取得
	public Image getCursor(int width, int height, boolean type) {
		String keyBuff = getKey(width, height);
		Image buff = null;
		if(type) {
			buff = (Image)
				CREATE_BUFF[CreateType.CURSOR.ordinal()].get(keyBuff);
			if(buff==null) {
				buff = create(CreateType.CURSOR, width, height);
				CREATE_BUFF[CreateType.CURSOR.ordinal()].put(keyBuff, buff);
			}
		} else {
			buff = (Image)
				CREATE_BUFF[CreateType.CURSOR0.ordinal()].get(keyBuff);
			if(buff==null) {
				buff = create(CreateType.CURSOR0, width, height);
				CREATE_BUFF[CreateType.CURSOR0.ordinal()].put(keyBuff, buff);
			}
		}
		return buff;
	}
	// 枠の隅・辺の大きさ
	private static final int CORNER = 8, SIDE = 16;
	// ウィンドウ・カーソルの構築
	private Image create(CreateType type, int width, int height) {
		int i = 0, i_len = 0, j = 0, j_len = 0, start = type.getStartX();
		Image sys = SYS_GRP;
		int wallpaper = WALLPAPER;
		BufferedImage ret = new BufferedImage(width, height, IMAGE_TYPE);
		Graphics g = ret.getGraphics();
		// 壁紙
		if(type==CreateType.WINDOW) {
			if(wallpaper == 0) {
				g.drawImage(sys, 0, 0, width, height, 0, 0, 32, 32, null);
			} else if(wallpaper == 1) {
				i_len = width / 32; j_len = height / 32;
				if((width%32) != 0) i_len++;
				if((height%32) != 0) j_len++;
				for(i = 0; i < i_len; i++)
					for(j = 0; j < j_len; j++)
						g.drawImage(sys, i*32, j*32, i*32+32, j*32+32,
							0, 0, 32, 32, null);
			}
		} else {
			if(wallpaper==0)
				g.drawImage(sys, 4, 4, width-4, height-4,
					start+4, 4, start+24, 24, null);
			else if(wallpaper==1) {
				i_len = width / 20; j_len = height / 20;
				if((width%20) != 0) i_len++;
				if((height%20) != 0) j_len++;
				for(i = 0; i < i_len; i++)
					for(j = 0; j < j_len; j++)
						g.drawImage(sys, i*20, j*20, i*20+20, j*20+20,
							start+4, 4, start+24, 24, null);
			}
		}
		// 上下の枠
		for(i = 0, i_len = (width-CORNER*2)/SIDE; i < i_len; i++) {
			g.drawImage(sys, CORNER+i*SIDE, 0, i*SIDE+24, CORNER,
				start+CORNER, 0, start+24, CORNER, null);
			g.drawImage(sys, CORNER+i*SIDE, height-CORNER, i*SIDE+24, height,
				start+CORNER, 24, start+24, 32, null);
		}
		int residue = (width-CORNER*2) % SIDE, div = (width-CORNER*2) / SIDE;
		if(residue != 0) {
			g.drawImage(sys, CORNER+div*SIDE, 0, width-CORNER, CORNER,
				start+CORNER, 0, start+CORNER+residue, CORNER, null);
			g.drawImage(sys,
				CORNER+div*SIDE, height-CORNER, width-CORNER, height,
				start+CORNER, 24, start+CORNER+residue, 32, null);
		}
		// 左右の枠
		for(i = 0, i_len = (height-CORNER*2)/SIDE; i < i_len; i++) {
			g.drawImage(sys, 0, CORNER+i*SIDE, CORNER, i*SIDE+24,
				start, CORNER, start+CORNER, 24, null);
			g.drawImage(sys, width-CORNER, CORNER+i*SIDE, width, i*SIDE+24,
				start+24, CORNER, start+32, 24, null);
		}
		residue = (height-CORNER*2) % SIDE;
		div = (height-CORNER*2) / SIDE;
		if(residue != 0) {
			g.drawImage(sys, 0, CORNER+div*SIDE, CORNER, height-CORNER,
				start, CORNER, start+CORNER, CORNER+residue, null);
			g.drawImage(sys,
				width-CORNER, CORNER+div*SIDE, width, height-CORNER,
				start+24, CORNER, start+32, CORNER+residue, null);
		}
		// 四隅
		g.drawImage(sys, 0, 0, CORNER, CORNER,
			start, 0, start+CORNER, CORNER, null);
		g.drawImage(sys, width-CORNER, 0, width, CORNER,
			start+24, 0, start+32, CORNER, null);
		g.drawImage(sys, 0, height-CORNER, CORNER, height,
			start, 24, start+CORNER, 32, null);
		g.drawImage(sys, width-CORNER, height-CORNER, width, height,
			start+24, 24, start+32, 32, null);
		
		return ret;
	}
	// イメージを取得
	private Image getImage(String name, Material type) {
		if(name.equals("\0")) return null;
		// バッファにあるかどうか
		Image ret = MEDIA_BUFF[type.ordinal()].get(name);
		// バッファになかった場合
		if(ret==null) {
			// バッファに追加
			ret = createImage(name, type);
			MEDIA_BUFF[type.ordinal()].put(name, ret);
		}
		return ret;
	}
	// 素材を読み込む
	private Image createImage(String name, Material type) {
		// 透明色をオンにするかどうか
		boolean alpha = false;
		switch(type) {
			case TITLE:    case GAMEOVER: case BACKDROP:
			case PANORAMA: case FACESET:
				alpha = false;
				break;
			case SYSTEM:   case MONSTER:  case CHIPSET:
			case CHARSET:  case BATTLE:
				alpha = true;
				break;
			// ピクチャはこのメソッドの呼び出し元が設定する
			case PICTURE:
				alpha = PICTURE_ALPHA;
				break;
		}
		// ファイル名を所得
		int grpType = 0;
		URL fileName = null;
		try {
			// ゲームデータ内のもの
			String fileDir = new StringBuffer(DIRECTORY.toString())
				.append(type.getFolder()).append(name).toString();
			for(grpType = 0; grpType < 3; grpType++) {
				fileName = new URL(fileDir+GRAPHIC_EXTENSION[grpType]);
				if(new File(fileName.getFile()).exists()) 
					throw new Exception();
			}
			// ＲＴＰのもの
			fileDir = new StringBuffer(RTP_DIRECTORY.toString())
				.append(type.getFolder()).append(name).toString();
			for(grpType = 0; grpType < 3; grpType++) {
				fileName = new URL(fileDir+GRAPHIC_EXTENSION[grpType]);
				if(new File(fileName.getFile()).exists())
					throw new Exception();
			}
			return null;
		} catch(Exception e) {}
		try {
			switch(grpType) {
//				// ＢＭＰ 形式
//				case 0: return new BitmapImage(fileName, alpha).getImage();
				// ＰＮＧ 形式
				case 1: return new PNGImage(fileName, alpha).getImage();
				// ＸＹＺ 形式
				case 2: return new XYZImage(fileName, alpha).getImage();
			}
		}  catch(Exception e) {}
		return null;
	}
	// 静止画の拡張子
	private static final String GRAPHIC_EXTENSION[] = {".bmp", ".png", ".xyz"};
	// ムービーの拡張子
	private static final String MOVIE_EXTENSION[] = {".avi", ".mpg" };
	// ＢＧＭ の拡張子
	private static final String BGM_EXTENSION[] = {".mid", ".wav", ".mp3" };
	// 効果音の拡張子
	private static final String SE_EXTENSION[] = {".wav" };
	// 素材フォルダ
	private enum Material {
		BACKDROP, BATTLE, CHARSET, CHIPSET, FACESET, GAMEOVER, MONSTER,
		PANORAMA, PICTURE, SYSTEM, TITLE, MOVIE, MUSIC, SOUND;
		private static final String FOLDER[] = {
			"BackDrop/",// 戦闘背景グラフィック
			"Battle/"  ,// 戦闘アニメグラフィック
			"CharSet/" ,// キャラセットグラフィック
			"ChipSet/" ,// チップセットグラフィック
			"FaceSet/" ,// 顔グラフィック
			"GameOver/",// ゲームオーバーグラフィック
			"Monster/" ,// 敵キャラグラフィック
			"Panorama/",// 遠景グラフィック
			"Picture/" ,// ピクチャーグラフィック
			"System/"  ,// システムグラフィック
			"Title/"   ,// タイトルグラフィック
			"Movie/"   ,// ムービー
			"Music/"   ,// 音楽
			"Sound/"    // 効果音
		};
		private String getFolder() {
			return FOLDER[ordinal()];
		}
	};
	// 1/4 x 1/4
	private static final int _4_4[][] = {
		// 左上 = 0
		{ 0, 0, 8, 8 },
		// 右上 = 1
		{ 8, 0, 16, 8 },
		// 右下 = 2
		{ 8, 8, 16, 16 },
		// 左下 = 3
		{ 0, 8, 8, 16 },
	};
	/* 1/4 x 1/4 位置
	   { (0, 0) (1, 0)
	     (0, 1) (1, 1) */
	private static final int _0_0 = 0, _1_0 = 1, _1_1 = 2, _0_1 = 3;
	/* 海岸線
	   | A |
	   | B |
	   | C |
	   | D | */
	private static final int _A = 0, _B = 16, _C = 32, _D = 48;
	/* 地形チップ
	  | A  | B  | C  |
	  | D7 | D8 | D9 |
	  | D4 | D5 | D6 |
	  | D1 | D2 | D3 | */
	private static final int[]
		__A  = {  0,  0 }, __B  = { 16,  0 }, __C  = { 32,  0 },
		__D7 = {  0, 16 }, __D8 = { 16, 16 }, __D9 = { 32, 16 },
		__D4 = {  0, 32 }, __D5 = { 16, 32 }, __D6 = { 32, 32 },
		__D1 = {  0, 48 }, __D2 = { 16, 48 }, __D3 = { 32, 48 };
}
