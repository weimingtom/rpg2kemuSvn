package rpg2k.media;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import rpg2k.*;

public class Font implements Field
{
	protected Project GAME_DATA;
	// �t�H���g�̉e
	private BufferedImage SHADOW[] = new BufferedImage[0x10000];
	// �t�H���g�̃L���b�V��
	private BufferedImage[][] CACHE = new BufferedImage[COLOR_MAX][0x10000];
	// �V�X�e���O���t�B�b�N
	private BufferedImage SYSTEM;
	// �t�H���g���T�|�[�g����Ă��邩
	private boolean SUPPORTED = false;
	// �t�H���g��
	// 0:�l�r �S�V�b�N
	// 1:�l�r ����
	private int MODE = 0;
	// �t�H���g�̃|�C���g�T�C�Y
	private int POINT;
	// ���݂̃t�H���g
	private java.awt.Font FONT = null;
	// ���݂̃t�H���g��
	private String NAME = "\0";
	// �R���X�g���N�^
	public Font(Project gameData) {
		GAME_DATA = gameData;
		SUPPORTED = isFontSupported();
		POINT = FONT_SIZE;
//		POINT = (FONT_SIZE-1) * 72
//			/ Toolkit.getDefaultToolkit().getScreenResolution()+2;
		MODE = GAME_DATA.SYS_DATA.FONT;
		NAME = SUPPORTED
			? MODE!=0 ? MS_MING : MS_GOTHIC : java.awt.Font.MONOSPACED;
	}
	// �V�X�e���O���t�B�b�N�̕ύX
	public void setSystem(BufferedImage file, int font) {
		
		MODE = font;
		SYSTEM = file;
		if(SUPPORTED) NAME = MODE != 0 ? MS_MING : MS_GOTHIC;
		for(int i = 0; i < COLOR_MAX; i++) {
			for(int j = 0; j < 0x10000; j++) {
				if(CACHE[i][j]==null) continue;
				CACHE[i][j].flush();
				CACHE[i][j] = null;
			}
		}
		for(int i = 0; i < 0x10000; i++) {
			if(SHADOW[i]==null) continue;
			SHADOW[i].flush();
			SHADOW[i] = null;
		}
		FONT = new java.awt.Font(NAME, java.awt.Font.PLAIN, POINT);
	}
	// ������̒������擾(���p�ꕶ�����P�Ƃ����Ƃ�)
	private static int getStringLength(String str) {
		try {
			return str.getBytes(CHARSET).length;
		} catch (UnsupportedEncodingException e) {}
		return -1;
	}
	// ��������擾(���͂̕\���Ɏg��)
	public Image getString(String str) {
		return null;
	}
	// ��������擾
	public Image getString(String str, int color) {
		if((color < 0) || (COLOR_MAX < color)) return null;
		int strW = getStringLength(str), writeX = 0;
		char addr = 0;
		if(strW < 1) strW = 1;
		BufferedImage ret = new BufferedImage(
			strW*(HALF_SIZE), FONT_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics g = ret.getGraphics();
		for(int i = 0; i < str.length(); i++) {
			addr = str.charAt(i);
			if(addr=='$') {
				i++;
				addr = str.charAt(i);
				writeX += FULL_SIZE;
			} else {
				if(CACHE[color][addr]==null) createCharacter(addr, color);
				g.drawImage(CACHE[color][addr], writeX, 0, null);
				writeX += isFullChar(addr) ? FULL_SIZE : HALF_SIZE;
			}
		}
		return ret;
	}
	// ���������
	private void createCharacter(char c, int color) {
		int i = 0, j = 0,
			width = isFullChar(c) ? FULL_SIZE-1 : HALF_SIZE-1,
			size = FONT_SIZE-1,
			startX = (color%10)*16+2, startY = (color/10+3)*16+2;
		BufferedImage buff = new BufferedImage(
			width, size, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buff.getGraphics();
		g.setFont(FONT);
		g.setColor(BUFF_COLOR);
		g.drawString(new Character(c).toString(), 0, size-1);
		for(i = 0; i < width; i++) for(j = 0; j < size; j++)
			if(buff.getRGB(i, j)==BUFF_COLOR_INT)
				buff.setRGB(i, j, SYSTEM.getRGB(startX+i, startY+j));
		BufferedImage target =
			new BufferedImage(width+1, FONT_SIZE, BufferedImage.TYPE_INT_ARGB);
		if(SHADOW[c]==null) createShadow(c);
		g = target.getGraphics();
		g.drawImage(SHADOW[c], 1, 1, null);
		g.drawImage(buff, 0, 0, null);
		buff.flush();
		buff = null;
		CACHE[color][c] = target;
	}
	// �����̉e�����
	private void createShadow(char c) {
		int width = isFullChar(c) ? FULL_SIZE-1 : HALF_SIZE-1,
			size = FONT_SIZE - 1;
		BufferedImage target =
			new BufferedImage(width, FONT_SIZE-1, BufferedImage.TYPE_INT_ARGB);
		Graphics g = target.getGraphics();
		g.setFont(FONT);
		g.setColor(BUFF_COLOR);
		g.drawString(new Character(c).toString(), 0, size-1);
		for(int i = 0; i < width; i++) for(int j = 0; j < size; j++)
			if(target.getRGB(i,j) == BUFF_COLOR_INT)
				target.setRGB(i, j, SYSTEM.getRGB(SHADOW_X+i, SHADOW_Y+j));
		SHADOW[c] = target;
	}
	// �������S�p��
	private boolean isFullChar(char c) {
		try {
			if(new Character(c).toString().getBytes(CHARSET).length > 1)
				return true;
		} catch (UnsupportedEncodingException e) {}
		return false;
	}
	// �t�H���g���T�|�[�g����Ă��邩
	private boolean isFontSupported() {
		// �t�H���g�̃T�|�[�g�̊m�F
		String fonts[] = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		for(int i = 0; i < fonts.length; i++)
			if(fonts[i].equalsIgnoreCase(MS_GOTHIC))
				for(int j = 0; j < fonts.length; j++)
					if(fonts[j].equalsIgnoreCase(MS_MING)) return true;
		return false;
	}
	
	private static final int FULL_SIZE = FONT_SIZE, HALF_SIZE = FONT_SIZE / 2;
	private static final int COLOR_MAX = 20;
	private static final int SHADOW_X = 16, SHADOW_Y = 32;
	private static final Color BUFF_COLOR = Color.white;
	private static final int BUFF_COLOR_INT = BUFF_COLOR.getRGB();
	private static final String
		MS_GOTHIC = "�l�r �S�V�b�N", MS_MING = "�l�r ����";
}
