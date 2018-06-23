package java2d;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * class use for creating GUI and import file of well
 * 
 */

public class GUIWellForm implements ActionListener {

	/**
	 * declaration of frame and container variable
	 */
	JFrame frame;
	/**
	 * container of frame 
	 */
	Container container;

	/**
	 * declaration of JMenuItem, wellData and canvas variable
	 */
	JMenuItem menuItemWellData;
	/**
	 * declaration of well of WellData
	 * 
	 */
	WellData well;
	/**
	 * declaration of canvas of type CanvasTwoD
	 */
	CanvasTwoD canvas;

	/**
	 * declaration of vector and arrayList
	 */
	Vector<String> propertyVector = new Vector<String>();
	/**
	 * declaration of array list type WellData
	 */
	ArrayList<WellData> wellArrayList;
	/**
	 * declaration of JcomboBox variable cmbProperty
	 */

	JComboBox<String> cmbProperty;
	/**
	 * declaration of property array list
	 */
	
	ArrayList<Double> propertyArrayList;

	/**
	 * constructor of GUIWellForm class which import the selected file from the drive and create GUI according to the particular value passed to this 
	 */

	public GUIWellForm() {

		// method calling for creating GUI, which will create the GUI
		creatingGUI();

		// method call for importing the File
		fileImport();
	}

	/**
	 * method for creating GUI, which will create the GUI ie. frame is created and menubar is set and on clicking the welldata menuitem then dialogbox will be opened and user have to select the file
	 */
	void creatingGUI() { 
		//  creating  frame 
		frame = new JFrame("2D");
		// creating menu
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuImport = new JMenu("Import");
		menuItemWellData = new JMenuItem("Well Data");
		menuImport.add(menuItemWellData);
		menuBar.add(menuImport);
		//setting the menubar on frame
		frame.setJMenuBar(menuBar);

		// creating property panel with label 
		JPanel pnlNorthDir = new JPanel();
		JLabel lblSelect = new JLabel("Select");
		// property = null;
	
		cmbProperty = new JComboBox<String>(propertyVector);
		cmbProperty.setPreferredSize(new Dimension(80, 20));
		cmbProperty.setEnabled(false);
		cmbProperty.setActionCommand("Property_Name");

		pnlNorthDir.add(lblSelect);
		pnlNorthDir.add(cmbProperty);

		// creating canvas panel
		canvas = new CanvasTwoD();
		canvas.setBackground(Color.white);
		container = frame.getContentPane();
		canvas.setFocusable(true);
		container.add(canvas, BorderLayout.CENTER);
		container.add(pnlNorthDir, BorderLayout.NORTH);
//by default the JComponent is invisible so it is set true so that it secome visible
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

	}

	/**
	 * method call for adding listener
	 */

	private void fileImport() {
		menuItemWellData.addActionListener(this);
		cmbProperty.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// if user click on wellData menuItem then if condition will be executed
		if (e.getActionCommand().equals("Well Data")) {

			// Declaration of wellList of type ArrayList
			wellArrayList = new ArrayList<WellData>();
			propertyVector.removeAllElements();
			propertyVector.add("Select Property");
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File f = chooser.getSelectedFile();
				BufferedReader br;
				// try and catch block for exception Handling

				try {
					// for reading the File
					br = new BufferedReader(new FileReader(f));
					// declaration of the string and int type variable str &
					// counter
					String str = "";
					int counter = 1;
					while ((str = br.readLine()) != null) {
						counter++;
						StringTokenizer stringTokenizer = new StringTokenizer(
								str);
						if (counter >= 5 && counter < 8) {
							for (int i = 0; i < 3; i++) {
								while (stringTokenizer.hasMoreTokens()) {
									propertyVector.add(stringTokenizer.nextToken());
								}
							}
						}
						if (counter >= 8) {
							well = new WellData();
							propertyArrayList = new ArrayList<Double>();
							for (int i = 0; i <= stringTokenizer.countTokens(); i++) {

								while (stringTokenizer.hasMoreTokens()) {
									// well name setting
									well.setWellName(stringTokenizer
											.nextToken());
									// X coordinate setting
									well.setX(Double
											.parseDouble(stringTokenizer
													.nextToken()));
									// X coordinate setting
									well.setY(Double
											.parseDouble(stringTokenizer
													.nextToken()));
									// addition of property1
									propertyArrayList.add(Double
											.parseDouble(stringTokenizer
													.nextToken()));// property1
									// addition of property2
									propertyArrayList.add(Double
											.parseDouble(stringTokenizer
													.nextToken()));// property2
									// addition of property3
									propertyArrayList.add(Double
											.parseDouble(stringTokenizer
													.nextToken()));// property3
									// property setting
									well.setProperty(propertyArrayList);

									wellArrayList.add(well);
									cmbProperty.setEnabled(true);
									canvas.repaint();
								}
							}
						}
					}
					// wellList setting
					cmbProperty.setSelectedIndex(0);
					canvas.setWellList(wellArrayList);
				} catch (Exception ae) {
					ae.printStackTrace();
				}
			}
		}// end if
		if (e.getActionCommand().equals("Property_Name")) {

			int c = cmbProperty.getSelectedIndex();

			canvas.setSelectIndex(c);
			canvas.removeAll();
			canvas.repaint();

		}
	}
}
