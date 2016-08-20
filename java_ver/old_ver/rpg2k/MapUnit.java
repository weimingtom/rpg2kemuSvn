package rpg2k;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import rpg2k.analyze.Format;
import static rpg2k.Structure.*;

public class MapUnit extends Data implements Field
{
	// �w�b�_
	private static String LMU_HEADER = "LcfMapUnit";
	
	private URL GAME_DIRECTORY;
	
	public MapUnit(URL dir, int mapNum) {
		super();
		SOURCE = new byte[mapNum][][];
		DATA = new Object[mapNum][];
		GAME_DIRECTORY = dir;
		for(int i = 0; i < mapNum; i++) {
			try {
				File lmt_file =
					new File(new URL(GAME_DIRECTORY+getFileName(i)).getFile());
				BufferedInputStream reader =
					new BufferedInputStream(new FileInputStream(lmt_file));
				checkHeader(reader, LMU_HEADER);
				SOURCE[i] = load1DArray(reader);
				reader.close();
			} catch(Exception e) {}
		}
	}
	// �h�c �̃t�@�C������Ԃ�
	public static String getFileName(int id) {
		return "Map"+Format.dec0(id, 4)+".lmu";
	}
	
	public static class MapEvent extends Data
	{
		public MapEvent(byte[] data) {
			super(get2DArray(data));
		}
		public MapEvent() {}
		
		protected Object[] getDefaults() {
			return DEFAULT;
		}
		
		protected static final Object[] DEFAULT = {
			null,
			"\0",
			0,
			0,
			null, null
		};
		protected Type[] getTypes() {
			return TYPE;
		}
		protected static final Type[] TYPE = {
			Type.NULL,
			// �C�x���g��
			// 0X01
			Type.STRING,
			// �w���W
			// 0X02
			Type.INTEGER,
			// �x���W
			// 0X03
			Type.INTEGER,
			Type.NULL,
			// �y�[�W�P�ʂ̃}�b�v�C�x���g
			// 0X05
			Type.MAP_EVENT_PAGE
		};
		public static class Page extends Data
		{
			public Page(byte[] data) {
				super(get2DArray(data));
			}
			
			protected Object[] getDefaults() {
				return DEFAULT;
			}
			protected static final Object[] DEFAULT = {
				null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null,
				"\0",
				0,
				2,
				1,
				false,
				null, null, null, null, null,
				0,
				3,
				0,
				0,
				false,
				0,
				3,
				null, null, null, null, null, null, null, null, null, null, null,
				null, null,
				0,
				null
			};
			protected Type[] getTypes() {
				return TYPE;
			}
			protected static final Type[] TYPE = {
				Type.NULL, Type.NULL,
				// �C�x���g�o������
				// 0X02
				Type.REQUIRE,
				Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
				Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
				Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
				// ���s�O���t�B�b�N�̃t�@�C����
				// 0X15
				Type.STRING,
				// �O���t�B�b�N�̏�w�}�b�v�h�c
				// 0X16
				Type.INTEGER,
				// �O���t�B�b�N�̌���
				// 0X17
				Type.INTEGER,
				// �O���t�B�b�N�p�^�[��
				// 0X18
				Type.INTEGER,
				// ������
				// 0X19
				Type.FLAG,
				Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
				// �ړ��^�C�v
				// 0X1F
				Type.INTEGER,
				// �ړ��p�x
				// 0X20
				Type.INTEGER,
				// �C�x���g�J�n����
				// 0X21
				Type.INTEGER,
				// �v���C�I���e�B�^�C�v
				// 0X22
				Type.INTEGER,
				// �ʂ̃C�x���g�Əd�Ȃ�Ȃ�
				// 0X23
				Type.FLAG,
				// �A�j���[�V�����^�C�v
				// 0X24
				Type.INTEGER,
				// �ړ����x
				// 0X25
				Type.INTEGER,
				Type.NULL, Type.NULL, Type.NULL,
				// �ړ����[�g
				// 0X29
				Type.ROUTE,
				Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
				Type.NULL, Type.NULL, Type.NULL,
				// �C�x���g�f�[�^�T�C�Y
				// 0X33
				Type.INTEGER,
				// �C�x���g�f�[�^��
				// 0X34
				Type.EVENT,
			};
			public static class Route {
				public Route(byte[] src) {
					byte[][] buff = get1DArray(src);
					try {
						try {
							PATTERN_NUMBER = ber2int(buff[1]);
							PATTERN = new int[PATTERN_NUMBER];
							ByteArrayInputStream reader =
								new ByteArrayInputStream(buff[0x0c]);
							for(int i = 0; i < PATTERN_NUMBER; i++)
								PATTERN[i] = ber2int(reader);
							try {
								REPEAT = buff[0x15][0] != 0 ? true : false;
							} catch(NullPointerException e) {}
							try {
								IGNORE = buff[0x16][0] != 0 ? true : false;
							} catch(NullPointerException e) {}
						} catch(NullPointerException e0) {}
					} catch(IOException e) {}
				}
				public int PATTERN_NUMBER = 0;
				public int PATTERN[];
				public boolean REPEAT = true;
				public boolean IGNORE = false;
			}
			public static class Require {
				public Require(byte[] src) {
					byte[][] buff = get1DArray(src);
					try {
						for(int i = 1, c = 0; i < 0x40; i <<= 1, c++)
							MASK[c] = (buff[1][0]&i) != 0 ? true : false;
						try {
							SWITCH1 = ber2int(buff[2]);
						} catch(NullPointerException e) {}
						try {
							SWITCH2 = ber2int(buff[3]);
						} catch(NullPointerException e) {}
						try {
							VARIABLE = ber2int(buff[4]);
						} catch(NullPointerException e) {}
						try {
							VARIABLE_COMPARE = ber2int(buff[5]);
						} catch(NullPointerException e) {}
						try {
							ITEM = ber2int(buff[6]);
						} catch(NullPointerException e) {}
						try {
							CHARACTER = ber2int(buff[7]);
						} catch(NullPointerException e) {}
						try {
							TIMER = ber2int(buff[8]);
						} catch(NullPointerException e) {}
						try {
						} catch(NullPointerException e) {}
					} catch(NullPointerException e0) {}
				}
				// 0x01
				public boolean MASK[] = new boolean[6];
				// 0x02
				public int SWITCH1 = 1;
				// 0x03
				public int SWITCH2 = 1;
				// 0x04
				public int VARIABLE = 1;
				// 0x05
				public int VARIABLE_COMPARE = 0;
				// 0x06
				public int ITEM = 1;
				// 0x07
				public int CHARACTER = 1;
				// 0x08
				public int TIMER = 0;
			}
		}
	}
	protected Object[] getDefaults() {
		return DEFAULT;
	}
	protected static final Object[] DEFAULT = {
		null,
		1,
		20,
		15,
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, null, null,
		false,
		"\0",
		false,
		false,
		false,
		0,
		false,
		0,
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, null, null, null, null,
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		Type.NULL,
		// �`�b�v�Z�b�g�h�c
		// 0X01
		Type.INTEGER,
		// ��
		// 0X02
		Type.INTEGER,
		// ����
		// 0X03
		Type.INTEGER,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL,
		// �X�N���[���^�C�v
		// 0X0B
		Type.INTEGER,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL,
		// ���i�t�@�C�����g�p����
		// 0X1F
		Type.FLAG,
		// ���i�t�@�C����
		// 0X20
		Type.STRING,
		// ���i���������Ƀ��[�v
		// 0X21
		Type.FLAG,
		// ���i���c�����Ƀ��[�v
		// 0X22
		Type.FLAG,
		// ���i���������Ɏ����X�N���[��
		// 0X23
		Type.FLAG,
		// ���i�̉������̎����X�N���[���̑��x
		// 0X24
		Type.INTEGER,
		// ���i���c�����Ɏ����X�N���[��
		// 0X25
		Type.FLAG,
		// ���i�̏c�����̎����X�N���[���̑��x
		// 0X26
		Type.INTEGER,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL,
		// ���w�}�b�v
		// 0X47
		Type.MAP,
		// ��w�}�b�v
		// 0X48
		Type.MAP,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL,
		// �}�b�v�C�x���g
		// 0X51
		Type.MAP_EVENT,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL,
		// �}�b�v�̕ۑ���
		// 0X5B
		Type.INTEGER
	};
}
