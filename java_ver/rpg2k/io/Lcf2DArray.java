package rpg2k.io;

import java.io.InputStream;

import static rpg2k.io.Decoder.*;

public abstract class Lcf2DArray extends Lcf1DArray
{
	// constructors
	public Lcf2DArray(DataBridge bridge, InputStream stream) {
		this(bridge, to1DArray(stream));
	}
	public Lcf2DArray(DataBridge bridge, byte input[]) {
		this(bridge, to1DArray(input));
	}
	public Lcf2DArray(DataBridge bridge, byte raw[][]) {
		bridge.store(this, raw);
	}
}
