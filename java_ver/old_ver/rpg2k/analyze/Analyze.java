package rpg2k.analyze;

import java.io.*;
import java.util.*;
import rpg2k.*;
import static rpg2k.analyze.Format.*;

public class Analyze implements Field
{
	private static boolean CALL_FROM_ROOT = false;
	private static boolean CALL_FROM_2D = false;
	private static boolean IS_LMT = false;
	
	private static int STREAM_SIZE;
	
	private static final int STRLEN_MINIMUM = 2;
	private static final int LINE = 16;
	
	private static final String TYPE[] = {
		"Ber     :",
		"String  :",
		"2Darray :",
		"1Darray :",
		"Binary  :",
	};
	
	private static int skipHeader(InputStream input)
		throws IOException
	{
		int length = Structure.ber2int(input);
		input.skip(length);
		return length+1;
	}
	public static Object analyzeFile(String file)
		throws IOException
	{
		byte src[] = null;
		
		STREAM_SIZE = (int) new File(file).length();
		
		BufferedInputStream reader =
			new BufferedInputStream(new FileInputStream(file));
		int headerSize = skipHeader(reader);
		src = new byte[STREAM_SIZE-headerSize];
		if(reader.read(src)==-1) throw new IOException();
		reader.close();
		IS_LMT = "lmt"
			.equalsIgnoreCase(file.substring(file.lastIndexOf(".") + 1));
	 	try {
			if(IS_LMT) throw new UnmatchedException();
			CALL_FROM_ROOT = true;
			byte[][] v = get1DArray(new ByteArrayInputStream(src));
			CALL_FROM_ROOT = false;
			Object[] ret = new Object[v.length];
			for(int i = 0; i < v.length; i++) {
				if(v[i] == null) continue;
				ret[i] = analyze(v[i]);
			}
			return (Object)ret;
		} catch(UnmatchedException e) {}
		try {
			CALL_FROM_ROOT = true;
			byte[][][] v = get2DArray(new ByteArrayInputStream(src));
			CALL_FROM_ROOT = false;
			Object[][] ret = new Object[v.length][];
			int i = 0, j = 0;
			for(i = 0; i < v.length; i++) {
				if(v[i] == null) continue;
				ret[i] = new Object[v[i].length];
				for(j = 0; j < v[i].length; j++) {
					if(v[i][j] == null) continue;
					ret[i][j] = analyze(v[i][j]);
				}
			}
			return (Object)ret;
		} catch(UnmatchedException e) {}
		return null;
	}
	public static Object analyze(final byte[] data) {
		try {
			return getString(data);
		} catch(UnmatchedException e) {}
		try {
			return ber2int(data);
		} catch(UnmatchedException e) {}
		try {
			byte[][][] v = get2DArray(new ByteArrayInputStream(data));
			Object[][] ret = new Object[v.length][];
			int i = 0, j = 0;
			for(i = 0; i < v.length; i++) {
				if(v[i] == null) continue;
				ret[i] = new Object[v[i].length];
				for(j = 0; j < v[i].length; j++) {
					if(v[i][j] == null) continue;
					ret[i][j] = analyze(v[i][j]);
				}
			}
			return ret;
		} catch(UnmatchedException e) {}
		try {
			byte[][] v = get1DArray(new ByteArrayInputStream(data));
			Object[] ret = new Object[v.length];
			for(int i = 0; i < v.length; i++) {
				if(v[i] == null) continue;
				ret[i] = analyze(v[i]);
			}
			return ret;
		} catch(UnmatchedException e) {}
		return new ByteArrayInputStream(data);
	}
	
	public static void print(final byte[] data) {
		printResult(analyze(data), System.out);
	}
	public static void print(String file)
		throws IOException
	{
		printResult(analyzeFile(file), System.out);
	}
	public static void print(String src, String dst)
		throws IOException
	{
		printResult(analyzeFile(src), new PrintStream(dst));
	}
	
	private static int loopNum = 0;
	private static void tab(String s, PrintStream out) {
		StringBuffer buff = new StringBuffer();
		for(int i = loopNum; i > 0; i--) buff.append("\t");
		out.printf(buff.append(s).toString());
	}
	private static void printResult(Object input, PrintStream out) {
		if(input instanceof Integer) {
			out.printf("Ber "+((Integer)input)+"\n");
		} else if(input instanceof String) {
			out.printf("String "+(String)input+"\n");
		} else if(input instanceof Object[][]) {
			Object[][] src = (Object[][]) input;
			out.printf("2Darray\n");
			for(int i = 0; i < src.length; i++) {
				if(src[i]==null) continue;
				tab(String.format("0x%04x:\n", i), out);
				loopNum++;
				for(int j = 0; j < src[i].length; j++) {
					if(src[i][j]==null) continue;
					tab(String.format("0x%04x: ", j), out);
					loopNum++;
					printResult(src[i][j], out);
					loopNum--;
				}
				loopNum--;
			}
			statistics(src, out);
		} else if(input instanceof Object[]) {
			Object[] src = (Object[]) input;
			out.printf("1Darray\n");
			for(int i = 0; i < src.length; i++) {
				if(src[i]==null) continue;
				tab(String.format("0x%04x: ", i), out);
				loopNum++;
				printResult(src[i], out);
				loopNum--;
			}
		} else if(input instanceof ByteArrayInputStream) {
			ByteArrayInputStream src = (ByteArrayInputStream)input;
			if(src.available()==0) {
				out.printf("Null\n");
				return;
			}
			out.printf("Binary\n");
			int newLine = LINE, buff = 0;
			int loopTime = src.available() / newLine;
			if((src.available()%newLine)!=0) loopTime++;
			int i = 0, j = 0;
			for(i = 0; i < loopTime; i++) {
				String buff0 = new String();
				for(j = 0; j < newLine; j++) {
					buff = src.read();
					if(buff == -1) break;
					buff0 += String.format("%02x ", buff);
				}
				tab(buff0+"\n", out);
			}
		}
	}
	private static void statistics(Object[][] in, PrintStream out) {
		int i = 0, j = 0, size = 0;
		int[][] ret = new int[5][200];
		for(i = 0; i < in.length; i++) {
			if(in[i]==null) continue;
			for(j = 0; j < in[i].length; j++) {
				if(size <= j) size = j+1;
				if(in[i][j] instanceof Integer) ret[0][j]++;
				else if(in[i][j] instanceof String) ret[1][j]++;
				else if(in[i][j] instanceof Object[][]) ret[2][j]++;
				else if(in[i][j] instanceof Object[]) ret[3][j]++;
				else if(in[i][j] instanceof ByteArrayInputStream)
					ret[4][j]++;
			}
		}
		tab("Statistics:\n", out);
		loopNum++;
		for(i = 0; i < size; i++) {
			int c = 0;
			for(j = 0; j < 5; j++) if(ret[j][i]==0) c++;
			if(c==5) continue;
			tab(String.format("0x%04x:\n", i), out);
			loopNum++;
			for(j = 0; j < 5; j++)
				if(ret[j][i] != 0)
					tab(TYPE[j]+String.format("%8d\n", ret[j][i]), out);
			loopNum--;
		}
		loopNum--;
	}
	
	private static class UnmatchedException extends Exception {
	}
	
	private static String getString(final byte[] data)
		throws UnmatchedException
	{
		String str = Structure.readString(data);
		if(str.length() < STRLEN_MINIMUM) throw new UnmatchedException();
		for(int i = 0, i_length = str.length(); i < i_length; i++)
			if(Character.isISOControl(str.charAt(i))
				|| !(Character.isDefined((str.charAt(i))))
			)
				throw new UnmatchedException();
		return str;
	}
	private static int ber2int(final byte[] source)
		throws UnmatchedException
	{
		int data = 0, i = 0, buff = 0;
		ByteArrayInputStream reader = new ByteArrayInputStream(source);
		while(true) {
			buff = reader.read();
			if(buff > 0x80) {
				data = (data<<7) | (buff-0x80);
			} else {
				data = (data<<7) | buff;
				break;
			}
			i++;
		}
		if((data>VARIABLE_MAXIMUM) || (data<VARIABLE_MINIMUM))
			throw new UnmatchedException();
		if((i+1)==source.length) return data;
		else throw new UnmatchedException();
	}
	private static byte[][] get1DArray(ByteArrayInputStream input)
		throws UnmatchedException
	{
		try {
			Vector<byte[]> ret = new Vector<byte[]>(100, 10);
			
			int arrayNum = 0, size = 0;
			byte[] array = null;
			
			while(true) {
				arrayNum = Structure.ber2int(input);
				if((arrayNum==-1) || (arrayNum==0)) break;
				
				size = Structure.ber2int(input);
				if(
					(size>STREAM_SIZE) || (size<0) || (size>input.available())
				) throw new UnmatchedException();
				
				array = new byte[size];
				input.read(array);
				
				setSize(ret, arrayNum);
				ret.set(arrayNum, array);
			}
			
			if(ret.isEmpty()) throw new UnmatchedException();
			if(CALL_FROM_2D || (arrayNum==-1) || (input.read()==-1))
				return (byte[][])ret.toArray(new byte[0][]);
			else throw new UnmatchedException();
		} catch(IOException e) {
			return null;
		}
	}
	private static byte[][][] get2DArray(ByteArrayInputStream input)
		throws UnmatchedException
	{
		try {
			int size0 = Structure.ber2int(input), arrayNum0 = 0;
			CALL_FROM_2D = true;
			
			if((size0 < 0) || (STREAM_SIZE < size0)) {
				CALL_FROM_2D = false;
				throw new UnmatchedException();
			}
			
			Vector<byte[][]> ret0 = new Vector<byte[][]>(size0, 10);
			
			for(int i = 0; i < size0; i++) {
				arrayNum0 = Structure.ber2int(input);
				setSize(ret0, arrayNum0);
				ret0.set(arrayNum0, get1DArray(input));
			}
			
			CALL_FROM_2D = false;
			
			if((arrayNum0==-1) || (input.read()==-1)
				|| (CALL_FROM_ROOT && IS_LMT)
			) return (byte[][][])ret0.toArray(new byte[0][][]);
			else throw new UnmatchedException();
		} catch(IOException e) {
			return null;
		}
	}
	private static void setSize(Vector list, int size)
		throws UnmatchedException
	{
		if((size>STREAM_SIZE) || (size<0)) throw new UnmatchedException();
		for(int i = list.size(), i_len = (size+1); i < i_len; i++)
			list.add(null);
	}
}
