package rpg2k.io;

import java.io.InputStream;

import static rpg2k.io.Decoder.*;

public abstract class Lcf1DArray
{
	// constructors
	public Lcf1DArray(DataBridge bridge, InputStream stream) {
		this(bridge, to1DArray(stream));
	}
	public Lcf1DArray(DataBridge bridge, byte input[]) {
		this(bridge, to1DArray(input));
	}
	public Lcf1DArray(DataBridge bridge, byte raw[][]) {
		bridge.store(this, raw);
	}
	// methods
	protected Element[] getArrayInfo() {
		
	}
	protected abstract String getSettingFile();
	// information of element
	protected class Element
	{
		// index of this element
		protected int INDEX;
		// data type of this element
		protected DataType TYPE;
		// name for databases field
		protected String NAME;
		// default value of this element
		protected Object DEFAULT;
	}
}
