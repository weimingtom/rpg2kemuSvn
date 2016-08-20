package rpg2k.analyze;

public class Format
{
	public static String HEX0(int data, int num) {
		return hex0(data, num).toUpperCase();
	}
	public static String hex0(int data, int num) {
		if(data==0) {
			String ret = new String();
			for(int i = 0; i < num; i++) ret+= "0";
			return ret;
		}
		int buff = data;
		int cmp = 0x0f << (4*(num-1));
		String ret = new String();
		for(; (buff&cmp) == 0; buff = buff<<4) ret += "0";
		return ret+Integer.toHexString(data);
	}
	public static String dec0(int data, int num) {
		String ret = String.valueOf(data);;
		for(int i = 0, i_length = num-ret.length(); i < i_length; i++)
			ret = "0" + ret;
		return ret;
	}
	public static String decSpace(int data,int num) {
		String ret = String.valueOf(data);;
		for(int i = 0, i_length = num-ret.length(); i < i_length; i++)
			ret = " " + ret;
		return ret;
	}
}
