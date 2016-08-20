import java.awt.Frame;

public class View
{
	// fields
	private Controller OWNER;
	private Frame FRAME;
	// constructer
	protected View(Controller owner) {
		OWNER = owner;
	}
}
