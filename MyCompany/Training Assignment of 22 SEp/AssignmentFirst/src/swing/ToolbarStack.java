package swing;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Stack;

/** class for management of stack */
public class ToolbarStack {

/**declaration of stack variable stackback and stackforward */
	private Stack<File> stackBack;
	private Stack<File> stackForward;

	/** Constructor of class ToolbarStack  */
	public ToolbarStack() {
		stackBack = new Stack<File>();
		stackForward = new Stack<File>();
	}

	/** getter method of stackback*/
	public Stack<File> getStackBack() {
		return stackBack;
	}

	/** getter method of stackforward */
	public Stack<File> getStackForward() {
		return stackForward;
	}
}
