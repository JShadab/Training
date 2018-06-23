package swing;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class Splitpane {
	JSplitPane splitPane;

	public Splitpane(JPanel paneLeft,JPanel paneRight) {
		splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, paneLeft, paneRight);
	}
	 JSplitPane getSplitPane()
	 {
		 return splitPane;
	 }
}
