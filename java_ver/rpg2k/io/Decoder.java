package rpg2k.io;

public class Decoder
{
	public static final String CHARSET = "Shift_JIS";
	
	private Decoder() {}
	
	// decode BER to integer
	public static int ber2int(InputStream stream)
		throws IOException
	{
		int data = 0, buffer = 0;
		while(true) {
			buffer = stream.read();
			if(buffer < 0x80) return (data<<7) + buffer;
			else data = (data<<7) + buffer-0x80;
		}
	}
	public static int ber2int(byte[] data) {
		return ber2int(new ByteArrayInputStream(data));
	}
	// raw to String
	public static String toString(byte[] input)
		throws UnsupportedEncodingException
	{
		return new String(input, CHARSET);
	}
	public static String toString(InputStream stream, int size)
		throws IOException UnsupportedEncodingException
	{
		byte[] ret = new byte[size];
		stream.read(ret);
		return new String(ret, CHARSET);
	}
	// unfold 1d array
	public static byte[][] unfold1DArray(InputStream stream)
		throws IOException
	{
		int arrayNum = 0, size = 0;
		byte[] array = null;
		boolean isByteArray = stream instanceof ByteArrayInputStream;
		Vector<byte[]> ret = new Vector<byte[]>(100, 10);
		while(true) {
			// get array index
			arrayNum = ber2int(stream);
			// check end of array
			if((arrayNum==0) || (!isByteArray && (arrayNum==-1))) break;
			// get element's size
			size = ber2int(stream);
			// copy bytes
			array = new byte[size];
			input.read(array);
			// copy to return
			setSize(ret, arrayNum);
			ret.set(arrayNum, array);
		}
		return ret.toArray(new byte[0][]);
	}
	public static byte[][] unfold1DArray(byte[] input)
		throws IOException
	{
		return unfold1DArray(new ByteArrayInputStream(input));
	}
	// unfold 2d array
	public static byte[][][] unfold2DArray(InputStream stream)
		throws IOException
	{
		int i = 0, size0 = ber2int(stream), arrayNum0 = 0;
		Vector<byte[][]> ret0 = new Vector<byte[][]>(10, 10);
		for(i = 0; i < size0; i++) {
			arrayNum0 = ber2int(stream);
			setSize(ret0, arrayNum0);
			ret0.set(arrayNum0, unfold1DArray(stream));
		}
		return ret0.toArray(new byte[0][][]);
	}
	public static byte[][][] unfold2DArray(byte[] input)
		throws IOException
	{
		return unfold2DArray(new ByteArrayInputStream(input));
	}
	// check Lcf file header
	public static void checkHeader(InputStream stream, String header)
		throws IOException
	{
		if(ber2int(stream) != getLength(header))
			throw new IOException("The header is illegal.");
		if(!header.equals(readString(stream, getLength(header))))
			throw new IOException("The header is illegal.");
	}
	// set Vector's size
	private static void setSize(Vector list, int size) {
		for(int i = list.size(), i_len = size+1; i < i_len; i++)
			list.add(null);
	}
	// get String size (byte)
	private static int getLength(String str)
		throws UnsupportedEncodingException
	{
		return str.getBytes(CHARSET).length;
	}
}
