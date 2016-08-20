package rpg2k.database;

import rpg2k.*;

public class Condition extends Data
{
	protected static final int INDEX = 0x12;
	
	protected Condition(byte[][][] data) {
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
		// ���
		// 0x02
		new Integer(0),
		// �\���F
		// 0x03
		new Integer(6),
		// �D��x
		// 0x04
		new Integer(50),
		// �s������
		// 0x05
		new Integer(100), null, null, null, null, null,
		// �ُ픭����/�`
		// 0x0B
		new Integer(80),
		// �ُ픭����/�a
		// 0x0C
		new Integer(60),
		// �ُ픭����/�b
		// 0x0D
		new Integer(30),
		// �ُ픭����/�c
		// 0x0E
		new Integer(0),
		// �ُ픭����/�d
		// 0x0F
		new Integer(0), null, null, null, null, null,
		// �Œ᎝���^�[����
		// 0x15
		new Integer(0),
		// ���R�����m��
		// 0x16
		new Integer(0),
		// �Ռ��ɂ�鎡���m��
		// 0x17
		new Integer(0), null, null, null, null, null, null, null,
		// �U���͂̔���
		// 0x1F
		new Boolean(false),
		// �h��͂̔���
		// 0x20
		new Boolean(false),
		// ���_�͂̔���
		// 0x21
		new Boolean(false),
		// �q�����̔���
		// 0x22
		new Boolean(false),
		// ������������
		// 0x23
		new Integer(100),
		// 100% �U�����
		// 0x24
		new Boolean(false),
		// ���@����
		// 0x25
		new Boolean(false),
		// �A�C�e���̑������O��Ȃ��Ȃ�
		// 0x26
		new Boolean(false),
		// �A�j��
		// 0x27
		new Integer(6), null,
		// �Ō��n����Z�\�̎g�p�s��
		// 0x29
		new Boolean(false),
		// �g�p�ł��Ȃ��Ȃ����Z�\�̑Ō��֌W�x
		// 0x2A
		new Integer(0),
		// ���_�n����Z�\�̎g�p�s��
		// 0x2B
		new Boolean(false),
		// �g�p�ł��Ȃ��Ȃ����Z�\�̐��_�֌W�x
		// 0x2C
		new Integer(0),
		// �g�o�̑����̎��
		// 0x2D
		new Integer(0),
		// �l�o�̑����̎��
		// 0x2E
		new Integer(0), null, null, null, null,
		// ���������̏�ԂɂȂ����Ƃ��̃��b�Z�[�W
		// 0x33
		"\0",
		// �G�����̏�ԂɂȂ����Ƃ��̃��b�Z�[�W
		// 0x34
		"\0",
		// ���łɂ��̏�ԂɂȂ��Ă���Ƃ��̃��b�Z�[�W
		// 0x35
		"\0",
		// ���̏�Ԃ̂Ƃ��̎����̃^�[���̃��b�Z�[�W
		// 0x36
		"\0",
		// ���̏�Ԃ��񕜂����Ƃ��̃��b�Z�[�W
		// 0x37
		"\0", null, null, null, null, null,
		// �퓬�����^�[����������g�o�̊���
		// 0x3D
		new Integer(0),
		// �퓬�����^�[����������g�o�̗�
		// 0x3E
		new Integer(0),
		// �}�b�v�ړ����ɕω�����g�o(x������)
		// 0x3F
		new Integer(0),
		// �}�b�v�ړ����ɕω�����g�o�̗�
		// 0x40
		new Integer(0),
		// �퓬�����^�[����������l�o�̊���
		// 0x41
		new Integer(0),
		// �퓬�����^�[����������l�o�̗�
		// 0x42
		new Integer(0),
		// �}�b�v�ړ����ɕω�����l�o(x������)
		// 0x43
		new Integer(0),
		// �}�b�v�ړ����ɕω�����l�o�̗�
		// 0x44
		new Integer(0)
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		
	};
}
