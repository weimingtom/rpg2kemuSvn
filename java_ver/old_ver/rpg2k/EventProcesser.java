package rpg2k;

import rpg2k.sprite.Sprite;
import rpg2k.media.Sound;

public class EventProcesser implements Field
{
	// ���[�v��񂠂���̃X�e�b�v��
	protected static final int STEP_PER_LOOP = 10000;
	// �C�x���g�̌Ăяo���̃o�b�t�@��
	protected static final int STACK_SIZE = 1000;
	// ���̃��[�v��
	protected static final int NEXT_FRAME = -1;
	// ���̃C�x���g��
	protected static final int NEXT_EVENT = -2;
	// �C�x���g�̏I�[
	protected static final int END_OF_EVENT = -3;
	// �Q�[�����[�h�ύX
	protected static final int CHANGE_MODE = -4;
	
	protected Sprite SPRITE;
	protected Project GAME_DATA;
	protected SaveData.DataBuff BUFF;
	
	// �R���X�g���N�^
	public EventProcesser(Sprite s, Project data) {
		SPRITE = s;
		GAME_DATA = data;
	}
	// �C�x���g�����s
	public void runEvent() {
		int i = 0;
		BUFF = GAME_DATA.SAVE_DATA.BUFF;
		while(i < STEP_PER_LOOP) {
			i += runEventCode(null);
		}
	}
	private int runEventCode(final Event e) {
		if(e == null) return 1;
		Sound sBuff;
		switch(e.CODE) {
			// ���򏈗��̏I�[
			case 00000:
				return 0;
			// �C�x���g�S�̂̏I��
			case 00010:
				return END_OF_EVENT;
			// ���͂̕\���i1�s��)
			case 10110:
				return 1;
			// ���͂̕\��(2�s�ځ`)
			case 20110:
				return 1;
			// ���̓I�v�V�����̕ύX
			case 10120:
				return 1;
			// ��O���t�B�b�N�̐ݒ�
			case 10130:
				return 1;
			// �I�����̏����i����J�n)
			case 10140:
				return 1;
			// �I�����̏����i�I����)
			case 20140:
				return 1;
			// �I�����̏����i����I���j
			case 20141:
				return 1;
			// ���l���͂̏���
			case 10150:
				return 1;
			// �X�C�b�`�̑���
			case 10210:
				return 1;
			// �ϐ��̑���
			case 10220:
				return 1;
			// �^�C�}�[�̑���
			case 10230:
				return 1;
			// �������̑���
			case 10310:
				return 1;
			// �A�C�e���̑���
			case 10320:
				return 1;
			// �����o�[�̓���ւ�
			case 10330:
				return 1;
			// �o���l�̑���
			case 10410:
				return 1;
			// ���x���̑���
			case 10420:
				return 1;
			// �\�͒l�̑���
			case 10430:
				return 1;
			// ����Z�\�̑���
			case 10440:
				return 1;
			// �����̕ύX
			case 10450:
				return 1;
			// HP�̑���
			case 10460:
				return 1;
			// MP�̑���
			case 10470:
				return 1;
			// ��Ԃ̕ύX
			case 10480:
				return 1;
			// �S��
			case 10490:
				return 1;
			// �_���[�W�̏���
			case 10500:
				return 1;
			// ��l���̖��O�ύX
			case 10610:
				return 1;
			// ��l���̌������ύX
			case 10620:
				return 1;
			// ��l���̕��s�O���t�B�b�N�ύX
			case 10630:
				return 1;
			// ��l���̊�O���t�B�b�N�ύX
			case 10640:
				return 1;
			// ��蕨�O���t�B�b�N�̕ύX
			case 10650:
				switch(e.ARGS[0]) {
					// ���^�D
					case 0:
						break;
					// ��^�D
					case 1:
						break;
					// ��s�D
					case 2:
						break;
				}
				return 1;
			// �V�X�e��BGM�̕ύX
			case 10660:
				sBuff = new Sound();
				sBuff.NAME = e.STRING;
				sBuff.FADE_IN_TIME = e.ARGS[1];
				sBuff.VOLUME = e.ARGS[2];
				sBuff.TEMPO = e.ARGS[3];
				sBuff.BALANCE = e.ARGS[4];
				switch(e.ARGS[0]) {
					// �퓬
					case 0:
						break;
					// �퓬�I��
					case 1:
						break;
					// �h��
					case 2:
						break;
					// ���^�D
					case 3:
						break;
					// ��^�D
					case 4:
						break;
					// ��s�D
					case 5:
						break;
					// �Q�[���I�[�o�[
					case 6:
						break;
				}
				return 1;
			// �V�X�e�����ʉ��̕ύX
			case 10670:
				sBuff = new Sound();
				sBuff.NAME = e.STRING;
				sBuff.VOLUME = e.ARGS[1];
				sBuff.TEMPO = e.ARGS[2];
				sBuff.BALANCE = e.ARGS[3];
				switch(e.ARGS[0]) {
					// �J�[�\���ړ�
					case 0:
						break;
					// ����
					case 1:
						break;
					// �L�����Z��
					case 2:
						break;
					// �u�U�[
					case 3:
						break;
					// �퓬�J�n
					case 4:
						break;
					// ����
					case 5:
						break;
					// �G�̒ʏ�U��
					case 6:
						break;
					// �G�_���[�W
					case 7:
						break;
					// �����_���[�W
					case 8:
						break;
					// ���
					case 9:
						break;
					// �G����
					case 10:
						break;
					// �A�C�e���g�p
					case 11:
						break;
				}
				return 1;
			// �V�X�e���O���t�B�b�N�̕ύX
			case 10680:
				SPRITE.setSystem(e.STRING, e.ARGS[0], e.ARGS[1]);
				return 1;
			// ��ʐ؂�ւ������̕ύX
			case 10690:
				return 1;
			// �퓬�̏����i����J�n)
			case 10710:
				return 1;
			// �퓬�̏����i�������ꍇ)
			case 20710:
				return 1;
			// �퓬�̏���(�������ꍇ)
			case 20711:
				return 1;
			// ���X�̏���(����J�n)
			case 10720:
				return 1;
			// ���X�̏���(���������ꍇ)
			case 20720:
				return 1;
			// ���X�̏���(�������Ȃ������ꍇ)
			case 20721:
				return 1;
			// ���X�̏���(����I��)
			case 20722:
				return 1;
			// �h���̏���(����J�n)
			case 10730:
				return 1;
			// �h���̏���(�h�������ꍇ)
			case 20730:
				return 1;
			// �h���̏���(�h�����Ȃ������ꍇ)
			case 20731:
				return 1;
			// �h���̏���(����I��)
			case 20732:
				return 1;
			// ���O���͂̏���
			case 10740:
				return 1;
			// �ꏊ�ړ�
			case 10810:
				return 1;
			// ���݂̏ꏊ���L��
			case 10820:
				return 1;
			// �L�������ꏊ�ֈړ�
			case 10830:
				return 1;
			// ��蕨�̏�~
			case 10840:
				return 1;
			// ��蕨�̈ʒu��ݒ�
			case 10850:
				return 1;
			// �C�x���g�̈ʒu��ݒ�
			case 10860:
				return 1;
			// �C�x���g�̈ʒu�̌���
			case 10870:
				return 1;
			// �w��ʒu�̒n�`ID�擾
			case 10910:
				return 1;
			// �w��ʒu�̃C�x���gID�擾
			case 10920:
				return 1;
			// ��ʂ̏���
			case 11010:
				return 1;
			// ��ʂ̕\��
			case 11020:
				return 1;
			// ��ʂ̐F���ύX
			case 11030:
				return 1;
			// ��ʂ̃t���b�V��
			case 11040:
				return 1;
			// ��ʂ̃V�F�C�N
			case 11050:
				return 1;
			// ��ʂ̃X�N���[��
			case 11060:
				return 1;
			// �V��G�t�F�N�g�̐ݒ�
			case 11070:
				return 1;
			// �s�N�`���[�̕\��
			case 11110:
				return 1;
			// �s�N�`���[�̈ړ�
			case 11120:
				return 1;
			// �s�N�`���[�̏���
			case 11130:
				return 1;
			// �퓬�A�j���̕\���i��퓬���j
			case 11210:
				return 1;
			// ��l���̓�����ԕύX
			case 11310:
				return 1;
			// �L�����N�^�[�̃t���b�V��
			case 11320:
				return 1;
			// �L�����N�^�[�̓���w��
			case 11330:
				return 1;
			// �w�蓮��̑S���s
			case 11340:
				return 1;
			// �w�蓮��̑O����
			case 11350:
				return 1;
			// �E�F�C�g
			case 11410:
				return NEXT_FRAME;
			// BGM�̉��t
			case 11510:
				return 1;
			// BGM�̃t�F�[�h�A�E�g
			case 11520:
				return 1;
			// ���݂�BGM�̋L��
			case 11530:
				return 1;
			// �L������BGM�����t
			case 11540:
				return 1;
			// ���ʉ��̉��t
			case 11550:
				return 1;
			// ���[�r�[�̍Đ�
			case 11560:
				return 1;
			// �L�[���͂̏���
			case 11610:
				return 1;
			// �`�b�v�Z�b�g�̕ύX
			case 11710:
				return 1;
			// ���i�̕ύX
			case 11720:
				return 1;
			// �G�o�������̕ύX
			case 11730:
				return 1;
			// �`�b�v�̒u��
			case 11740:
				return 1;
			// �e���|�[�g�ʒu�̑���
			case 11810:
				return 1;
			// �e���|�[�g�֎~�̕ύX
			case 11820:
				return 1;
			// �G�X�P�[�v�ʒu�̐ݒ�
			case 11830:
				return 1;
			// �G�X�P�[�v�֎~�̕ύX
			case 11840:
				return 1;
			// ���j���[��ʂ̌Ăяo��
			case 11950:
				return 1;
			// ���j���[��ʋ֎~�̕ύX
			case 11960:
				return 1;
			// ��������(�����𖞂����ꍇ�j
			case 12010:
				return 1;
			// ��������(�����𖞂����Ȃ��ꍇ)
			case 22010:
				return 1;
			// ��������(����I��)
			case 22011:
				return 1;
			// ���x���̐ݒ�
			case 12110:
				return 1;
			// �w�胉�x���֔��
			case 12120:
				return 1;
			// �J��Ԃ�����(�J�n)
			case 12210:
				return 1;
			// �J��Ԃ�����(�I�[)
			case 22210:
				return 1;
			// �J��Ԃ������̒��f
			case 12220:
				return 1;
			// �C�x���g�̒��f
			case 12310:
				return 1;
			// �C�x���g�̈ꎞ����
			case 12320:
				return 1;
			// �C�x���g�̌Ăяo��
			case 12330:
				return 1;
			// ���߁E�R�����g�i1�s�ځj
			case 12410:
				return 0;
			// ���߁E�R�����g�i2�s�ځ`�j
			case 22410:
				return 0;
			// �Q�[���I�[�o�[
			case 12420:
				SPRITE.setMode(GameMode.GAMEOVER);
				return CHANGE_MODE;
			// �^�C�g����ʂɖ߂�
			case 12510:
				SPRITE.setMode(GameMode.TITLE);
				return CHANGE_MODE;
			// �G�L������HP�̑���
			case 13110:
				return 1;
			// �G�L������MP�̑���
			case 13120:
				return 1;
			// �G�L�����̏�ԕύX
			case 13130:
				return 1;
			// �G�L�����̏o��
			case 13150:
				return 1;
			// �퓬�w�i�̕ύX
			case 13210:
				return 1;
			// �퓬�A�j���̕\��
			case 13260:
				return 1;
			// �퓬���̏�������i�����𖞂����ꍇ�j
			case 13310:
				return 1;
			// �퓬���̏�������i�����𖞂����Ȃ��ꍇ�j
			case 23310:
				return 1;
			// �퓬���̏�������i����I���j
			case 23311:
				return 1;
			// �퓬�̒��f
			case 13410:
				return 1;
			default:
				return 1;
		}
	}
}
