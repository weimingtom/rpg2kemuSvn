package rpg2k;

import java.net.URL;
import rpg2k.*;
import rpg2k.database.*;

public class Project
{
	public MapTree MAP_TREE;
	public MapUnit MAP_UNIT;
	public SaveData SAVE_DATA;
	protected DataBase DATA_BASE;
	public Attribute ATTRIBUTE;
	public BattleAnimation BATTLE_ANIME;
	public rpg2k.database.Character CHARACTER;
	public ChipSet CHIP_SET;
	public CommonEvent COMMON_EVENT;
	public Condition CONDITION;
	public Enemy ENEMY;
	public EnemyGroup ENEMY_GROUP;
	public Item ITEM;
	public Skill SKILL;
	public SystemData SYS_DATA;
	public Switch SWITCH;
	public Term TERM;
	public Variable VARIABLE;
	public boolean HIDE_TITLE;
	public URL GAME_DIRECTORY, RTP_DIRECTORY;
	public String GAME_TITLE = "\0";
	
	protected Project() {}
	public Project(URL gameDir, URL rtpDir, boolean hideTitle) {
		GAME_DIRECTORY = gameDir;
		RTP_DIRECTORY = rtpDir;
		HIDE_TITLE = hideTitle;
		// データベース
		DATA_BASE = new DataBase(gameDir);
		// データベースの各項目
		ATTRIBUTE = DATA_BASE.newAttribute();
		BATTLE_ANIME = DATA_BASE.newBattleAnimation();
		CHARACTER = DATA_BASE.newCharacter();
		CHIP_SET = DATA_BASE.newChipSet();
		COMMON_EVENT = DATA_BASE.newCommonEvent();
		CONDITION = DATA_BASE.newCondition();
		ENEMY = DATA_BASE.newEnemy();
		ENEMY_GROUP = DATA_BASE.newEnemyGroup();
		ITEM = DATA_BASE.newItem();
		SKILL = DATA_BASE.newSkill();
		SYS_DATA = DATA_BASE.newSystemData();
		SWITCH = DATA_BASE.newSwitch();
		TERM = DATA_BASE.newTerm();
		VARIABLE = DATA_BASE.newVariable();
		DATA_BASE = null;
		// マップツリー
		MAP_TREE = new MapTree(gameDir);
		// ゲームタイトル
		GAME_TITLE = (String)MAP_TREE.getData(0)[1];
		// マップ
		MAP_UNIT = new MapUnit(gameDir, MAP_TREE.getMapNum());
		// セーブデータ
		SAVE_DATA = new SaveData(gameDir);
		SAVE_DATA.setGameData(this);
	}
}
