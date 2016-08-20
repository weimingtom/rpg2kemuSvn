import java.io.*;
import java.net.URL;
import java.util.*;
import rpg2k.*;
import rpg2k.database.*;

public class Model extends Project implements Field
{
	protected static final String GAME_NAME = "Emulater";
	
	protected Controller owner;
//	protected Project GAME_DATA;
//	protected URL SAVE_DIRECTORY;
	protected Vector<GameKey> GAME_KEY_BUFF;
	protected Vector<TestPlayKey> TEST_PLAY_KEY_BUFF;
	protected Vector<Integer> KEY_BUFF;
	
	protected Model(Controller c) {
		super(c.GAME_DIRECTORY, c.RTP_DIRECTORY, c.HIDE_TITLE);
		owner = c;
		GAME_KEY_BUFF = new Vector<GameKey>();
		TEST_PLAY_KEY_BUFF = new Vector<TestPlayKey>();
		KEY_BUFF = new Vector<Integer>();
	}
}
