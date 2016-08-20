package rpg2k.database;

import rpg2k.*;

public class Enemy extends Data
{
	protected static final int INDEX = 0x0E;
	
	protected Enemy(byte[][][] data) {
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
		// �G�O���t�B�b�N�̃t�@�C����
		// 0x02
		"\0",
		// �O���t�B�b�N�̐F��
		// 0x03
		new Integer(0),
		// �ő�g�o
		// 0x04
		new Integer(10),
		// �ő�l�o
		// 0x05
		new Integer(10),
		// �U����
		// 0x06
		new Integer(10),
		// �h���
		// 0x07
		new Integer(10),
		// ���_��
		// 0x08
		new Integer(10),
		// �q����
		// 0x09
		new Integer(10),
		// �O���t�B�b�N�̔�����
		// 0x0A
		new Boolean(false),
		// ���Ƃ��o���l
		// 0x0B
		new Integer(0),
		// ���Ƃ�����
		// 0x0C
		new Integer(0),
		// ���Ƃ��A�C�e��
		// 0x0D
		new Integer(0),
		// �A�C�e���o����
		// 0x0E
		new Integer(100), null, null, null, null, null, null,
		// �K�E�L��
		// 0x15
		new Boolean(false),
		// �K�E�m��
		// 0x16
		new Integer(30), null, null, null,
		// �ʏ�U���̃~�X����
		// 0x1A
		new Boolean(false), null,
		// �O���t�B�b�N���󒆂�
		// 0x1C
		new Boolean(false), null,
		// ��ԗL���x�̃f�[�^��
		// 0x1F
		new Integer(0),
		// ��ԗL���x�̃f�[�^
		// 0x20
		new char[0],
		// �����L���x�̃f�[�^��
		// 0x21
		new Integer(0),
		// �����L���x�̃f�[�^
		// 0x22
		new char[0], null, null, null, null, null, null, null,
		// �U���p�^�[��
		// 0x2A
		new AttackPattern()
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		
	};
}
