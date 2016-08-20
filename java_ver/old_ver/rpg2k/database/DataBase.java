package rpg2k.database;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import rpg2k.*;
import static rpg2k.Structure.*;

public class DataBase implements Field
{
	// ヘッダ
	private static final String	LDB_HEADER = "LcfDataBase";
	// ファイル名
	private static final String	LDB_FILE_NAME = "RPG_RT.ldb";
	
	private byte[][] SOURCE;
	private URL GAME_DIRECTORY;
	
	protected DataBase() {}
	public DataBase(URL dir, String file) {
		try {
			GAME_DIRECTORY = dir;
			
			File ldb_file
				= new File(new URL
				(GAME_DIRECTORY.toString() + file).getFile());
			BufferedInputStream reader
				= new BufferedInputStream(new FileInputStream(ldb_file));
			checkHeader(reader, LDB_HEADER);
			SOURCE = load1DArray(reader);
			reader.close();
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public DataBase(URL dir) {
		this(dir, LDB_FILE_NAME);
	}
	public Attribute newAttribute() {
		return new Attribute(get2DArray(SOURCE[Attribute.INDEX]));
	}
	public BattleAnimation newBattleAnimation() {
		return new BattleAnimation(get2DArray(SOURCE[BattleAnimation.INDEX]));
	}
	public Character newCharacter() {
		return new Character(get2DArray(SOURCE[Character.INDEX]));
	}
	public ChipSet newChipSet() {
		return new ChipSet(get2DArray(SOURCE[ChipSet.INDEX]));
	}
	public CommonEvent newCommonEvent() {
		return new CommonEvent(get2DArray(SOURCE[CommonEvent.INDEX]));
	}
	public Condition newCondition() {
		return new Condition(get2DArray(SOURCE[Condition.INDEX]));
	}
	public Enemy newEnemy() {
		return new Enemy(get2DArray(SOURCE[Enemy.INDEX]));
	}
	public EnemyGroup newEnemyGroup() {
		return new EnemyGroup(get2DArray(SOURCE[EnemyGroup.INDEX]));
	}
	public Item newItem() {
		return new Item(get2DArray(SOURCE[Item.INDEX]));
	}
	public Skill newSkill() {
		return new Skill(get2DArray(SOURCE[Skill.INDEX]));
	}
	public Switch newSwitch() {
		return new Switch(get2DArray(SOURCE[Switch.INDEX]));
	}
	public SystemData newSystemData() {
		return new SystemData(get1DArray(SOURCE[SystemData.INDEX]));
	}
	public Term newTerm() {
		return new Term(get1DArray(SOURCE[Term.INDEX]));
	}
	public Variable newVariable() {
		return new Variable(get2DArray(SOURCE[Variable.INDEX]));
	}
}
