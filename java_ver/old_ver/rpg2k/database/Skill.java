package rpg2k.database;

import rpg2k.*;
import rpg2k.media.Sound;

public class Skill extends Data
{
	public static final int INDEX = 0x0C;
	
	protected Skill(byte[][][] data) {
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
		// ����
		// 0x02
		"\0",
		// �g�p���̃��b�Z�[�W�̂P�s��
		// 0x03
		"\0",
		// �g�p���̃��b�Z�[�W�̂Q�s��
		// 0x04
		"\0", null, null,
		// ���s���̃��b�Z�[�W
		// 0x07
		"\0",
		// ���
		// 0x08
		new Integer(0),
		// ����l�o�̕���
		// 0x09
		new Integer(0),
		// ����l�o�̊���
		// 0x0A
		new Integer(1),
		// ����l�o
		// 0x0B
		new Integer(0),
		// ���ʔ͈�
		// 0x0C
		new Integer(0),
		// �n�m�ɂ���X�C�b�`
		// 0x0D
		new Boolean(true),
		// �퓬�A�j��
		// 0x0E
		new Boolean(false), null,
		// ���ʉ�
		// 0x10
		new Sound(), null,
		// �t�B�[���h�Ŏg�p�\
		// 0x12
		new Boolean(true),
		// �퓬���g�p�\
		// 0x13
		new Boolean(false),
		// �u��ԕω��v�Ɓu��Ԏ��Áv�̔��]
		// 0x14
		new Boolean(false),
		// �Ō��֌W�x
		// 0x15
		new Integer(0),
		// ���_�֌W�x
		// 0x16
		new Integer(3),
		// ���l���U�x
		// 0x17
		new Integer(4),
		// ��{���ʗ�
		// 0x18
		new Integer(0),
		// ��{������
		// 0x19
		new Integer(100), null, null, null, null, null,
		// �g�o�ቺ
		// 0x1F
		new Boolean(false),
		// �l�o�ቺ
		// 0x20
		new Boolean(false),
		// �U���͒ቺ
		// 0x21
		new Boolean(false),
		// �h��͒ቺ
		// 0x22
		new Boolean(false),
		// ���_�͒ቺ
		// 0x23
		new Boolean(false),
		// �q�����ቺ
		// 0x24
		new Boolean(false),
		// �z���^�̔\�͒l�ቺ
		// 0x25
		new Boolean(false),
		// �h�䖳���^�̔\�͒l�ቺ
		// 0x26
		new Boolean(false), null, null,
		// ��ԕω��̃f�[�^��
		// 0x29
		new Integer(0),
		// ��ԕω��̃f�[�^
		// 0x2A
		new boolean[]{false},
		// �U���E�h�� �����̃f�[�^��
		// 0x2B
		new Integer(0),
		// �U���E�h�� �����̃f�[�^
		// 0x2C
		new boolean[]{false},
		// �U���E�h�� ������ �ቺ�E�㏸
		// 0x2D
		new Boolean(false), null, null, null,
		// �g�p���̃A�j��
		// 0x31
		new Integer(0),
		// �b�a�`�f�[�^
		// 0x32
		new Integer(0)
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		
	};
}
