package rpg2k.database;

import rpg2k.*;

public class CommonEvent extends Data
{
	protected static final int INDEX = 0x19;
	
	protected CommonEvent(byte[][][] data) {
		super(data);
	}
	protected Object[] getDefaults() {
		return DEFAULT;
	}
	protected static final Object[] DEFAULT = {
		null,
		"\0",
		null, null, null, null, null, null, null, null, null,
		5,
		0,
		1,
		null, null, null, null, null, null, null, null, null
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		Type.NULL,
		// ���O
		// 0x01
		Type.STRING,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL, Type.NULL, Type.NULL,
		// �C�x���g�J�n����
		// 0x0B
		Type.INTEGER,
		// �C�x���g�o������ �X�C�b�`
		// 0x0C
		Type.INTEGER,
		// �C�x���g�o������ �X�C�b�`�ԍ�
		// 0x0D
		Type.INTEGER,
		Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL, Type.NULL,
		Type.NULL,
		// �f�[�^�T�C�Y
		// 0x15
		Type.INTEGER,
		// �C�x���g�f�[�^
		// 0x16
		Type.EVENT
	};

}