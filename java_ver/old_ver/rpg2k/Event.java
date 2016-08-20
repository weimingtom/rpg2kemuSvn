package rpg2k;

import java.io.*;
import java.util.Vector;
import static rpg2k.Structure.*;

public class Event implements Field
{
	public int CODE;
	public int INDENT;
	public String STRING;
	public int ARGS[];
	public Event(int code, int indent, String str, int[] ARGS) {
		CODE = code;
		INDENT = indent;
		STRING = str;
		ARGS = ARGS;
	}
	protected Event() {}
	public static Event[] newEvent(final byte[] data) {
		ByteArrayInputStream reader = new ByteArrayInputStream(data);
		Vector<Event> v = new Vector<Event>(10, 10);
		try {
			while(true) {
				Event ret = new Event();
				ret.CODE = ber2int(reader);
				ret.INDENT = ber2int(reader);
				int strLen = ber2int(reader);
				ret.STRING = readString(reader, strLen);
				int ARGSLen = ber2int(reader);
				ret.ARGS = new int[ber2int(reader)];
				for(int i = 0; i < ARGSLen; i++)
					ret.ARGS[i] = ber2int(reader);
				v.add(ret);
				if(reader.available() == 0) break;
			}
		} catch(IOException e) {}
		return (Event[])v.toArray(new Event[0]);
	}
	public static void toBinary(Event[] src, OutputStream output) {
	}
}
