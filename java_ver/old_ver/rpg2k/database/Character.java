package rpg2k.database;

import rpg2k.*;

public class Character extends Data
{
	protected static final int INDEX = 0x0B;
	
	protected Character(byte[][][] data) {
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
		// ������
		// 0x02
		"\0",
		// ���s�O���t�B�b�N�̃t�@�C����
		// 0x03
		"\0",
		// ���s�O���t�B�b�N�̈ʒu
		// 0x04
		new Integer(0),
		// ������
		// 0x05
		new Integer(0), null,
		// �������x��
		// 0x07
		new Integer(50),
		// �ō����x��
		// 0x08
		new Integer(1),
		// �K�E�L��
		// 0x09
		new Integer(1),
		// �K�E�m��
		// 0x0A
		new Integer(30), null, null, null, null,
		// ��O���t�B�b�N�̃t�@�C����
		// 0x0F
		"\0",
		// ��O���t�B�b�N�̈ʒu
		// 0x10
		new Integer(0), null, null, null, null,
		// �񓁗�
		// 0x15
		new Integer(0),
		// �����Œ�
		// 0x16
		new Integer(0),
		// �����`�h�s��
		// 0x17
		new Integer(0),
		// ���͖h��
		// 0x18
		new Integer(0), null, null, null, null, null, null,
		// �e���x�����Ƃ̃X�e�[�^�X
		// 0x1F
		new short[6][50], null, null, null, null, null, null, null, null, null,
		// �o���l�Ȑ��̊�{�l
		// 0x29
		new Integer(30),
		// �o���l�̑����x
		// 0x2A
		new Integer(30),
		// �o���l�̕␳�l
		// 0x2B
		new Integer(0), null, null, null, null, null, null, null,
		// ��������
		// 0x33
		new short[5],
		// �f��ōU�������Ƃ��̐퓬�A�j��
		// 0x38
		new Integer(0),
		// �E��
		// 0x39
		new Integer(0), null, null, null, null,
		// �퓬�A�j��
		// 0x3E
		new Integer(0),
		// �e���x���ŏK���������Z�\
		// 0x3F
		new int[0][0], null, null,
		// �Ǝ��퓬�R�}���h�L��
		// 0x42
		new Integer(0),
		// �Ǝ��퓬�R�}���h
		// 0x43
		"\0", null, null, null,
		// ��ԗL���x�̃f�[�^��
		// 0x47
		new Integer(0),
		// ��ԗL���x
		// 0x48
		new char[0],
		// �����L���x�̃f�[�^��
		// 0x49
		new Integer(0),
		// �����L���x
		// 0x4A
		new char[0], null, null, null, null, null,
		// �퓬�R�}���h
		// 0x50
		new int[6]
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		
	};
}
