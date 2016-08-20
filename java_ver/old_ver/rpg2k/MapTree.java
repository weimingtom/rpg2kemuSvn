package rpg2k;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import rpg2k.media.Sound;
import static rpg2k.Structure.*;

// LcfMapTree(RPG�c�N�[��2000�̃}�b�v�c���[)
public class MapTree extends Data implements Field
{
// �w�b�_
	private static String LMT_HEADER = "LcfMapTree";
// �t�@�C����
	private static String LMT_FILE_NAME = "RPG_RT.lmt";
// �p�[�e�B�[�Ə�蕨�̏����ʒu
	public int START_POINT[][] = new int[4][3];
	
	private URL GAME_DIRECTORY;
	
	public MapTree(URL dir, String file)
	{
		super();
		try {
			GAME_DIRECTORY = dir;
			BufferedInputStream reader = null;
			File lmt_file
				= new File(new URL
				(GAME_DIRECTORY.toString() + file).getFile());
			reader = new BufferedInputStream(new FileInputStream(lmt_file));
			checkHeader(reader, LMT_HEADER);
			
			// ���ƂȂ�}�b�v�c���[�̃f�[�^(�񎟌��z��\)
			SOURCE = load2DArray(reader);
			
			// ��l���Ə�蕨�̏����ʒu�̎擾
			for(int i = 0, i_length = ber2int(reader)+1;
				 i < i_length; i++)
				ber2int(reader);
			byte[][] buffer = load1DArray(reader);
			DATA = new Object[SOURCE.length][];
			for(int i = 0, i_length = buffer.length/10+1; i < i_length; i++) {
				START_POINT[i][0] = ber2int(buffer[10*i+1]);
				START_POINT[i][1] = ber2int(buffer[10*i+2]);
				START_POINT[i][2] = ber2int(buffer[10*i+3]);
			}
			buffer = null;
			reader.close();
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public MapTree(URL dir) {
		this(dir, LMT_FILE_NAME);
	}
	public int getMapNum() {
		return SOURCE.length-1;
	}
	protected Object[] getDefaults() {
		return DEFAULT;
	}
	protected static Object[] DEFAULT = {
		null,
		"\0",
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, null, null, null, null, null, null, null,
		"\0",
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, null, null, null, null, null, null, null, null,
		25
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		Type.NULL,
		// �}�b�v��
		// 0x01
		Type.STRING,
		// �e�}�b�v�h�c
		// 0x02
		Type.INTEGER,
		Type.NULL,
		// �}�b�v�E�G���A���ʃt���O
		// 0x04
		Type.INTEGER,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		// �a�f�l �I�v�V����
		// 0x0B
		Type.INTEGER,
		// �a�f�l �t�@�C����
		// 0x0C
		Type.SOUND,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL,
		// �퓬�w�i �I�v�V����
		// 0x15
		Type.INTEGER,
		// �퓬�w�i �t�@�C����
		// 0x16
		Type.STRING,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL,
		// �e���|�[�g�̃I�v�V����
		// 0x1F
		Type.INTEGER,
		// �G�X�P�[�v�̃I�v�V����
		// 0x20
		Type.INTEGER,
		// �Z�[�u�̃I�v�V����
		// 0x21
		Type.INTEGER,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL,
		// �G���J�E���g����G�O���[�v(�񎟌��z��)
		// 0x29
		Type.ENEMY_GROUP,
		Type.NULL, Type.NULL,
		// �G�o������
		// 0x2C
		Type.INTEGER
	};
}
