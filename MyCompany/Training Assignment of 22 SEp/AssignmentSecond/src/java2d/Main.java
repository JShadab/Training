package java2d;


import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
/**
 * class to start Application, create object of GUIWellForm and set system Look and Feel.
 * this class contains main method 
 *  
 * 
 */

public class Main 
{
	
/**
 * this method is called by JVM and starts our application	
 * 
 */
	
public static void main(String[] args) {
		//exception handling using try and catch block
		try {
			// Set cross-platform Java Look &Feel 
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception ClassNotFoundException
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception InstantiationException
		} catch (IllegalAccessException e) {
			// handle exception IllegalAccessException
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				
			new GUIWellForm(); // Create and show the GUI.
						}
		});

	}
}
