package rpg2k.io;

import java.io.ByteArrayOutputStream;

public class Encoder
{
	public static final String CHARSET = "Shift_JIS";
	
	private Encoder() {}
	// fold integer to BER
	public static byte[] toBytes(int data) {
		Stack<Integer> s = new Stack<Integer>();
		int buff = data;
		s.push(buff&0x7f);
		buff >>= 7;
		while(buff != 0) {
			s.push((buff&0x7f)  | 0x80);
			buff >>= 7;
		}
		byte[] ret = new byte[s.size()];
		for(int i = 0; i < ret.length; i++)
			ret[i] = s.pop().byteValue();
		return ret;
	}
	// String to bytes
	public static byte[] toBytes(String str)
		throws UnsupportedEncodingException
	{
		return str.getBytes(CHARSET);
	}
	public static ByteArrayOutputStream fold2DArray(byte[][][] input) {
		ByteArrayOutputStream ret = new ByteArrayOutputStream();
		// sign length of this array
		int size = 0, i = 0;
		for(i = 0; i < input.length; i++) {
			if(input[i] != null) size = size + 1;
			ret.write(int2ber(size));
			for(i = 0; i < input.length; i++) {
				if(input[i]==null) continue;
				// sign element index
				ret.write(int2ber(i));
				// store element
				fold1DArray(input[i]).writeTo(ret);
			}
		}
		return ret;
	}
	public static ByteArrayOutputStream fold1DArray(byte[][] input) {
		ByteArrayOutputStream ret = new ByteArrayOutputStream();
		for(int i = 0; i < input.length; i++) {
			if(input[i]==null) continue;
			// sign element index
			ret.write(int2ber(i));
			// sign element size
			ret.write(int2ber(input[i].length));
			// store element
			ret.write(input[i]);
		}
		// sign end of Array
		ret.write(0);
		return ret;
	}
}
