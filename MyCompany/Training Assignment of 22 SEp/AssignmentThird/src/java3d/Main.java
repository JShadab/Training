package java3d;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;
/**
 * 
 * @author ravi.chakravarti
 * Main class call class DisplayUI class 
 */

public class Main {
	
public static void main(String[] args) {
	try{
		String lookAndFeel=UIManager.getSystemLookAndFeelClassName();
		UIManager.setLookAndFeel(lookAndFeel);
	}
	catch (Exception e) {
		e.printStackTrace();
		
		}			JFrame frame=new DisplayGUI();
					frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
				
	}
}

