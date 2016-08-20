package rpg2k;

public class AttackPattern
{
	// 0x01
	public int TYPE = 0;
	// 0x02
	public int ACTION = 1;
	// 0x03
	public int SKILL = 1;
	// 0x04
	public int TRANSFORM = 1;
	// 0x05
	public int REQUIRE_TYPE = 0;
	// 0x06
	public int REQUIRE_A = 0;
	// 0x07
	public int REQUIRE_B = 0;
	// 0x08
	public int REQUIRE_SWITCH = 1;
	// 0x09
	public boolean ON_AFTER = false;
	// 0x0A
	public int SWITCH_ON_AFTER = 1;
	// 0x0B
	public boolean OFF_AFTER = false;
	// 0x0C
	public int SWITCH_OFF_AFTER = 1;
	// 0x0D
	public int PRIORITY = 50;
	
	public static AttackPattern[] newAttackPattern(byte input[]) {
		byte[][][] buff = Structure.get2DArray(input);
		AttackPattern ret[] = new AttackPattern[buff.length];
		for(int i = 0, i_len = buff.length; i < i_len; i++) {
			ret[i] = new AttackPattern();
			try {
				ret[i].TYPE = Structure.ber2int(buff[i][1]);
			} catch(Exception e) {}
			try {
				ret[i].ACTION = Structure.ber2int(buff[i][2]);
			} catch(Exception e) {}
			try {
				ret[i].SKILL = Structure.ber2int(buff[i][3]);
			} catch(Exception e) {}
			try {
				ret[i].TRANSFORM = Structure.ber2int(buff[i][4]);
			} catch(Exception e) {}
			try {
				ret[i].REQUIRE_TYPE = Structure.ber2int(buff[i][5]);
			} catch(Exception e) {}
			try {
				ret[i].REQUIRE_A = Structure.ber2int(buff[i][6]);
			} catch(Exception e) {}
			try {
				ret[i].REQUIRE_B = Structure.ber2int(buff[i][7]);
			} catch(Exception e) {}
			try {
				ret[i].REQUIRE_SWITCH = Structure.ber2int(buff[i][8]);
			} catch(Exception e) {}
			try {
				ret[i].ON_AFTER =
					Structure.ber2int(buff[i][10])!=1 ? false : true;
			} catch(Exception e) {}
			try {
				ret[i].SWITCH_ON_AFTER = Structure.ber2int(buff[i][11]);
			} catch(Exception e) {}
			try {
				ret[i].OFF_AFTER =
					Structure.ber2int(buff[i][12])!=1 ? false : true;
			} catch(Exception e) {}
			try {
			} catch(Exception e) {}
			try {
				ret[i].SWITCH_OFF_AFTER = Structure.ber2int(buff[i][13]);
			} catch(Exception e) {}
			try {
				ret[i].PRIORITY = Structure.ber2int(buff[i][14]);
			} catch(Exception e) {}
		}
		return ret;
	}
}
