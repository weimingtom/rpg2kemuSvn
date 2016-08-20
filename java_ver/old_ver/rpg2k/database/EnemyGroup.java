package rpg2k.database;

import rpg2k.*;

public class EnemyGroup extends Data
{
	protected static final int INDEX = 0x0F;
	
	protected EnemyGroup(byte[][][] data) {
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
		// �G���X�g
		// 0x02
		new int[0][0], null,
		// �o���\�n�`�̃f�[�^��
		// 0x04
		new Integer(0),
		// �o���\�n�`�̃f�[�^
		// 0x05
		new boolean[]{true}
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		
	};
}
