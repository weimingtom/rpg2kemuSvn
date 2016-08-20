package rpg2k.database;

import rpg2k.*;

public class Item extends Data implements Field
{
	protected static final int INDEX = 0x0D;
	
	protected Item(byte[][][] data) {
		super(data);
	}
	protected Object[] getDefaults() {
		return DEFAULT;
	}
	protected static final Object[] DEFAULT = {
		// �� �� ����
		// �� �� ��/�Z/��/�����i
		// �� �� ��
		// �{ �� �{
		// �� �� ��
		// �� �� ����Z�\����
		// �X �� �X�C�b�`
		
		null,
		// ���O
		// ���E���E��E�{�E��E���E�X
		// 0x01
		"\0",
		// ����
		// ���E���E��E�{�E��E���E�X
		// 0x02
		"\0",
		// ���
		// ���E���E��E�{�E��E���E�X
		// 0x03
		new Integer(0), null,
		// �l�i
		// ���E���E��E�{�E��E���E�X
		// 0x05
		new Integer(0),
		// �g�p��
		// ��E�{�E��E���E�X
		// 0x06
		new Integer(1), null, null, null, null,
		// �U���͂̏㏸��
		// ���E��
		// 0x0B
		new Integer(0),
		// �h��͂̏㏸��
		// ���E��
		// 0x0C
		new Integer(0),
		// ���_�͂̏㏸��
		// ���E��
		// 0x0D
		new Integer(0),
		// �q�����̏㏸��
		// ���E��
		// 0x0E
		new Integer(0),
		// ������
		// 0:�Ў莝��
		// 1:���莝��
		// ��
		// 0x0F
		new Integer(0),
		// ����l�o
		// ��
		// 0x10
		new Integer(0),
		// ��{������
		// ��
		// 0x11
		new Integer(0),
		// �K�E�m��������
		// ��
		// 0x12
		new Integer(0), null,
		// �퓬�A�j��
		// ��
		// 0x14
		new Integer(1),
		// �^�[���ŏ��ɐ搧�U��
		// ��
		// 0x15
		new Boolean(false),
		// ���U��
		// ��
		// 0x16
		new Boolean(false),
		// �S�̍U��
		// ��
		// 0x17
		new Boolean(false),
		// �G�̉�𗦂𖳎�
		// ��
		// 0x18
		new Boolean(false),
		// �K�E�h�~
		// ��
		// 0x19
		new Boolean(false),
		// �����U���̉�𗦃A�b�v
		// ��
		// 0x1A
		new Boolean(false),
		// �l�o����ʔ���
		// ��
		// 0x1B
		new Boolean(false),
		// �n�`�_���[�W����
		// ��
		// 0x1C
		new Boolean(false),
		// ����Ƃ��Ă̌���
		// ���E��
		// 0x1D
		new Integer(1), null,
		// ���ʔ͈�
		// ��
		// 0x1F
		new Integer(0),
		// �g�o�񕜗ʂ̊���
		// ��
		// 0x20
		new Integer(0),
		// �g�o�񕜗ʂ̐�Η�
		// ��
		// 0x21
		new Integer(0),
		// �l�o�񕜗ʂ̊���
		// ��
		// 0x22
		new Integer(0),
		// �l�o�񕜗ʂ̐�Η�
		// ��
		// 0x23
		new Integer(0), null,
		// �t�B�[���h�ł̂ݎg�p�\
		// ��
		// 0x25
		new Boolean(false),
		// �퓬�s�\�L�����N�^�[�݂̂ɗL��
		// ��
		// 0x26
		new Boolean(false), null, null,
		// �ő�g�o�̕ω���
		// ��
		// 0x29
		new Integer(0),
		// �ő�l�o�̕ω���
		// ��
		// 0x2A
		new Integer(0),
		// �U���͂̕ω���
		// ��
		// 0x2B
		new Integer(0),
		// �h��ʂ̕ω���
		// ��
		// 0x2C
		new Integer(0),
		// ���_�͂̕ω���
		// ��
		// 0x2D
		new Integer(0),
		// �q�����̕ω���
		// ��
		// 0x2E
		new Integer(0), null, null, null, null,
		// �g�p���̃��b�Z�[�W
		// ��
		// 0x33
		"\0", null,
		// �K���E���� ����Z�\
		// ���E���E�{�E��
		// 0x35
		new Integer(1), null,
		// �n�m�ɂ���X�C�b�`
		// �X
		// 0x37
		new Integer(1), null,
		// �t�B�[���h�Ŏg�p�\
		// �X
		// 0x39
		new Boolean(true),
		// �o�g���Ŏg�p�\
		// �X
		// 0x3A
		new Boolean(false), null, null,
		// �����E�g�p �\�Ȏ�l���̃f�[�^��
		// ���E���E��E�{�E��E��
		// 0x3D
		new Integer(0),
		// �����E�g�p �\�Ȏ�l���̃f�[�^
		// ���E���E��E�{�E��E��
		// 0x3E
		new boolean[]{true},
		// ��� �ω��E�h�~�E���� �̃f�[�^��
		// ���E���E��
		// 0x3F
		new Integer(0),
		// ��� �ω��E�h�~�E���� �̃f�[�^
		// ���E���E��
		// 0x40
		new boolean[]{false},
		// �U���E�h�� �����̃f�[�^��
		// ���E��
		// 0x41
		new Integer(0),
		// �U���E�h�� �����̃f�[�^
		// ���E��
		// 0x42
		new boolean[]{false},
		// ��� �ω��E�h�~ �m��
		// ���E��
		// 0x43
		new Integer(0),
		// �u��ԕω��v�Ɓu��Ԏ��Áv�̋t�]
		// ���E��
		// 0x44
		new Boolean(false),
		// �g�p���̃A�j��
		// ���E��
		// 0x45
		new Integer(0),
		// �A�C�e���Ƃ��ē���Z�\���g��
		// ���E��
		// 0x47
		new Boolean(false),
		// �����\�ȐE�Ƃ̃f�[�^��
		// ���E���E��E�{�E��E��
		// 0x48
		new Integer(0),
		// �����\�ȐE��
		// ���E���E��E�{�E��E��
		// 0x49
		new boolean[]{true}
	};
	protected Type[] getTypes() {
		return TYPE;
	}
	protected static final Type[] TYPE = {
		
	};
}
