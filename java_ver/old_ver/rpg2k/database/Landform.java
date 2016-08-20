package rpg2k.database;

import rpg2k.*;

public class Landform extends Data
{
	public static final int NUMBER = 0x10;
	
	protected Landform(byte[][][] data) {
		super(data);
	}
	protected Object[] getDefaults() {
		return DEFAULT;
	}
	protected static final Object[] DEFAULT = {
		null,
		// ���O
		// 0x01
		"\0",
		// �_���[�W
		// 0x02
		0,
		// �G�o���{��
		// 0x03
		100,
		// �퓬�w�i�̃t�@�C����
		// 0x04
		"\0",
		// ���^�D�ł̒ʍs
		// 0x05
		false,
		// ��^�D�̒ʍs
		// 0x06
		false,
		// ��s�D�̒ʍs
		// 0x07
		true, null,
		// ��s�D�Œ����\
		// 0x09
		true, null,
		// �ʏ�L�����̕\�����@
		// 0x0B
		0,
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		
	};
}
