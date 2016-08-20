package rpg2k;

import java.io.*;
import java.util.Stack;
import java.util.Vector;

public final class Structure implements Field
{
	private Structure() {}
	// BER ���k������W�J
	public static int ber2int(InputStream input) throws IOException {
		int data = 0, buffer = 0;
		while(true) {
			buffer = input.read();
			if(buffer < 0x80) return (data<<7) + buffer;
			else data = (data<<7) + buffer-0x80;
		}
	}
	public static int ber2int(byte[] data) {
		try {
			return ber2int(new ByteArrayInputStream(data));
		} catch(IOException e) {
			return -1;
		}
	}
	// �������ǂݍ���
	public static String readString(byte[] input) {
		try {
			return new String(input, CHARSET);
		} catch(UnsupportedEncodingException e) {
			return null;
		}
	}
	public static String readString(InputStream input, int size) {
		try {
			byte[] ret = new byte[size];
			input.read(ret);
			return new String(ret, CHARSET);
		} catch(Exception e) {
			return null;
		}
	}
	// �ꎟ���z���W�J
	public static byte[][] load1DArray(InputStream input) throws IOException {
		int arrayNum = 0, size = 0;
		byte[] array = null;
		Vector<byte[]> ret = new Vector<byte[]>(100, 10);
		while(true) {
			// �z��ԍ��̎擾
			arrayNum = ber2int(input);
			// �z��̏I��肩����
			if((arrayNum==-1) || (arrayNum==0)) break;
			// �f�[�^�̑傫�����擾
			size = ber2int(input);
			// �f�[�^�̓ǂݍ���
			array = new byte[size];
			input.read(array);
			// �f�[�^�̏�������
			setSize(ret, arrayNum);
			ret.set(arrayNum, array);
		}
		return ret.toArray(new byte[0][]);
	}
	public static byte[][] get1DArray(byte[] input) {
		try {
			return load1DArray(new ByteArrayInputStream(input));
		} catch(IOException e) {
			return null;
		}
	}
	// �񎟌��z���W�J
	public static byte[][][] load2DArray(InputStream input)
		throws IOException
	{
		int i = 0, size0 = ber2int(input), arrayNum0 = 0;
		Vector<byte[][]> ret0 = new Vector<byte[][]>(10, 10);
		for(i = 0; i < size0; i++) {
			arrayNum0 = ber2int(input);
			setSize(ret0, arrayNum0);
			ret0.set(arrayNum0, load1DArray(input));
		}
		return ret0.toArray(new byte[0][][]);
	}
	public static byte[][][] get2DArray(byte[] input) {
		try {
			return load2DArray(new ByteArrayInputStream(input));
		} catch(IOException e) {
			return null;
		}
	}
	public static void checkHeader(InputStream reader, String header)
		throws IOException
	{
		if(ber2int(reader) != getLength(header))
			throw new IOException("The header is illegal.");
		if(!header.equals(readString(reader, getLength(header))))
			throw new IOException("The header is illegal.");
	}
	// Vector �̑傫����ݒ�
	private static void setSize(Vector list, int size) {
		for(int i = list.size(), i_len = size+1; i < i_len; i++)
			list.add(null);
	}
	// ������̒������擾(���p�ꕶ�����P�Ƃ����Ƃ�)
	private static int getLength(String str) {
		try {
			return str.getBytes(CHARSET).length;
		} catch (UnsupportedEncodingException e) {
			return -1;
		}
	}
	// ������ BER ���k�����ɕϊ�
	public static byte[] int2ber(int data) {
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
	public static byte[] toBytes(String str) {
		try {
			return str.getBytes(CHARSET);
		} catch(UnsupportedEncodingException e) {
			return null;
		}
	}
	public static ByteArrayOutputStream store1DArray(byte[][] input) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			for(int i = 0; i < input.length; i++) {
				if(input[i]==null) continue;
				// �z��ԍ��̏�������
				out.write(int2ber(i));
				// �f�[�^�̑傫���̏�������
				out.write(int2ber(input[i].length));
				// �f�[�^�̏�������
				out.write(input[i]);
			}
			// �z��̏I������������
			out.write(0);
		} catch(IOException e) {
			return null;
		}
		return out;
	}
	public static ByteArrayOutputStream store2DArray(byte[][][] input) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		// �z��̗v�f���̏�������
		int size = 0, i = 0;
		for(i = 0; i < input.length; i++)
			if(input[i] != null) size = size + 1;
		try {
			out.write(int2ber(size));
			for(i = 0; i < input.length; i++) {
				if(input[i]==null) continue;
				// �z��ԍ��̏�������
				out.write(int2ber(i));
				// �v�f�̏�������
				store1DArray(input[i]).writeTo(out);
			}
		} catch(IOException e) {
			return null;
		}
		return out;
	}
}
