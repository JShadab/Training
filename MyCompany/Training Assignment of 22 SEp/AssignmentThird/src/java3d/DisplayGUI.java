package java3d;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

@SuppressWarnings("serial")
public class DisplayGUI extends JFrame implements ActionListener {
	
	private GridData grid;
	private LayeringDialog layerOptionDialog = null;

	private JMenuBar menubar;
	private JMenu menuProject;
	private JMenu menuOptions;
	private JMenuItem menuitemLoadData;
	private JMenu menuitemDisplayData;
	private JMenuItem menuitemLayering;
	private JCheckBoxMenuItem cbmenuAxis;
	private JCheckBoxMenuItem cbmenuGrid;
	private JCheckBoxMenuItem cbmenuTitle;

	private ThreeDCanvas canvas;
	private boolean isGridShown = false;
	
	
	public DisplayGUI() {
		setTitle("3D Assignment");
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		setJMenuBar(creatingMenubar());
		creatingGUI();

		addListeners();
	}
/**
 * method to add addlisteners
 */
	private void addListeners() {
		menuitemLoadData.addActionListener(this);
		menuitemDisplayData.addActionListener(this);
		menuitemLayering.addActionListener(this);

		cbmenuGrid.addActionListener(this);
		cbmenuAxis.addActionListener(this);
		cbmenuTitle.addActionListener(this);
	}
/*
 * method to create menubar
 */
	private JMenuBar creatingMenubar() {

		menubar = new JMenuBar();

		menuProject = new JMenu("Project");
		menuOptions = new JMenu("Options");

		menuitemDisplayData = new JMenu("Display Loaded Grid");
		menuitemDisplayData.setEnabled(false);

		menuitemLayering = new JMenuItem("Layering");
		menuitemLoadData = new JMenuItem("Load Grid Data");

		cbmenuAxis = new JCheckBoxMenuItem("Show Axis");
		cbmenuAxis.setSelected(true);
		cbmenuGrid = new JCheckBoxMenuItem("Show Grid");
		cbmenuTitle = new JCheckBoxMenuItem("show Title");
		cbmenuTitle.setSelected(true);

		menuProject.add(menuitemLoadData);
		menuProject.addSeparator();
		menuProject.add(menuitemDisplayData);

		menuOptions.add(cbmenuAxis);
		menuOptions.add(cbmenuGrid);
		menuOptions.add(cbmenuTitle);
		menuOptions.addSeparator();
		menuOptions.add(menuitemLayering);

		menubar.add(menuProject);
		menubar.add(menuOptions);

		return menubar;
	}
/**
 * method to create GUI 
 */
	private void creatingGUI() {
		canvas = new ThreeDCanvas();
		add(canvas.creatingCanvas());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		//action performed file chooser dialog open
		if (source.equals(menuitemLoadData)) {
			JFileChooser filechooser = new JFileChooser();
			filechooser.setMultiSelectionEnabled(true);
			int option = filechooser.showOpenDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				File[] file = filechooser.getSelectedFiles();
				ButtonGroup bg = new ButtonGroup(); 

				for (final File f : file) {

					JRadioButtonMenuItem rbmenuFile = new JRadioButtonMenuItem(
							f.getName());
					bg.add(rbmenuFile);
					menuitemDisplayData.add(rbmenuFile);
					rbmenuFile.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							parseFile(f);
						}
					});
					menuitemDisplayData.setEnabled(true);
				}
			}
		}

		
		else if (source.equals(cbmenuGrid)) {
			if (isGridShown) {
				canvas.showHideGid(cbmenuGrid.isSelected());
			} else {
				JOptionPane.showMessageDialog(this,
						"There is no geometry to show/hide grid.",
						"No Shape Exist!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(source.equals(cbmenuTitle)){
			
			canvas.showHideTitle(cbmenuTitle.isSelected());
	
	}

		else if (source.equals(cbmenuAxis)) {
			if (isGridShown) {
				canvas.showHideAxis(cbmenuAxis.isSelected());
			} else {
				JOptionPane.showMessageDialog(this,
						"There is no geometry to show/hide Axis.",
						"No Shape Exist!", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		else if (source.equals(menuitemLayering)) {
			if (isGridShown) {
				cbmenuGrid.setSelected(true);
				canvas.showHideGid(cbmenuGrid.isSelected());

				if (layerOptionDialog == null) {
					layerOptionDialog = new LayeringDialog(this,
							"Layering Options");
					// layerOptionDialog.setVisible(true);
				}
				layerOptionDialog.setVisible(true);
				/*
				 * cbmenuGrid.setSelected(false);
				 * canvas.showHideGid(cbmenuGrid.isSelected());
				 */
			} else {
				JOptionPane.showMessageDialog(this,
						"There is no existing shape for Layering.",
						"No Shape Exist!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
 /**
  * 
  * @param file read and X,Y and Z coordinate add in ArrayList
  */
	private void parseFile(File file) {
		try {
			Reader reader = new FileReader(file);

			StreamTokenizer streamTokenizer = new StreamTokenizer(reader);

			grid = new GridData();
			grid.setGridName(file.getName());

			boolean isCoordinate = false;
			int count = 1;
			//ArrayList declaration
			ArrayList xValue = new ArrayList();
			ArrayList yValue = new ArrayList();
			ArrayList zValue = new ArrayList();

			while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) {
				switch (streamTokenizer.ttype) {
				case StreamTokenizer.TT_NUMBER:
					if (isCoordinate) {
						if (count == 1) {
							xValue.add(streamTokenizer.nval);
							count = 2;
						} else if (count == 2) {
							yValue.add(streamTokenizer.nval);
							count = 3;
						} else if (count == 3) {
							zValue.add(streamTokenizer.nval);
							count = 1;
						}
					}
					break;
				case StreamTokenizer.TT_WORD:
					if (streamTokenizer.sval.equals("NX")) {
						while (streamTokenizer.nextToken() != StreamTokenizer.TT_NUMBER) {
							continue;
						}
						grid.setNX((int) streamTokenizer.nval);
					} else if (streamTokenizer.sval.equals("NY")) {
						while (streamTokenizer.nextToken() != StreamTokenizer.TT_NUMBER) {
							continue;
						}
						grid.setNY((int) streamTokenizer.nval);
					} else if (streamTokenizer.sval.equals("NZ")) {
						while (streamTokenizer.nextToken() != StreamTokenizer.TT_NUMBER) {
							continue;
						}
						grid.setNZ((int) streamTokenizer.nval);
						isCoordinate = true;
					}
					break;
				}
			}
			grid.setxValues(xValue);
			grid.setyValues(yValue);
			grid.setzValues(zValue);

			canvas.setGrid(grid);
			canvas.creatingCanvas();
			isGridShown = true;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @return grid 
	 */

	public GridData getGrid() {
		return grid;
	}
/**
 * 
 * @return canvas
 */
	public ThreeDCanvas getCanvas() {
		return canvas;
	}

	
}