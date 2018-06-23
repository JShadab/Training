package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

public class Toolbar implements ActionListener {
	private JToolBar toolbar;
	private JButton  btnBack, btnForward, btnUP;
	JToggleButton view;
/**
 * default constructor of class Toolbar
 */
	Toolbar() {
		
		toolbar = new JToolBar();
		ImageIcon iconBack = new ImageIcon(getClass().getResource(
				"/images/back.png"));
		ImageIcon iconForward = new ImageIcon(getClass().getResource(
				"/images/forward.png"));
		ImageIcon iconUp = new ImageIcon(getClass().getResource(
				"/images/up.png"));
	
		ImageIcon iconView = new ImageIcon(getClass().getResource(
		"/images/view.png"));
		btnBack = new JButton("Back", iconBack);
		btnForward = new JButton("Forword", iconForward);
		btnUP = new JButton("Up", iconUp);
		

		toolbar.add(btnBack);
		toolbar.add(btnForward);
		toolbar.add(btnUP);
	
		
	}

	/**
	 * 
	 * @return toolbar
	 */
	JToolBar getToolBar() {
		return toolbar;
	}
 public JButton getBtnBack() {
	return btnBack;

}
	
	 
	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
