package swing;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

public class MenuBar extends JMenuBar implements ActionListener {
	JFrame tabbedFrame,frame;
	JPanel pnlStatus;
	private JMenuBar menubar;
	JButton btnCancel, btnOk;
	private ToolbarStack utils;
	JCheckBox chkPathTitle, chkPathAddress, chkHideExt;
	private CardPane crdpane;
	boolean isChecked = true;
	JCheckBoxMenuItem toolBars, addressBars, statusBars;
	File file;

	// CheckBoxListener chkListener=new CheckBoxListener();

/**
 * parameterized constructor of class MenuBar 
 */
	MenuBar(CardPane cp, ToolbarStack tools) {
		this.crdpane = cp;
		this.utils = tools;
		menubar = new JMenuBar();

		JMenu view = new JMenu("View");
		
		toolBars = new JCheckBoxMenuItem("ToolBars");
		toolBars.setSelected(true);
		addressBars = new JCheckBoxMenuItem("AddressBars");
		addressBars.setSelected(true);
		statusBars = new JCheckBoxMenuItem("StatusBars");
		statusBars.setSelected(true);
		JRadioButton radiobtnList = new JRadioButton("List");
		radiobtnList.addActionListener(this);
		radiobtnList.setSelected(true);
		JRadioButton radiobtnDetails = new JRadioButton("Details");
		radiobtnDetails.addActionListener(this);
		ButtonGroup btngroup = new ButtonGroup();
		btngroup.add(radiobtnList);
		btngroup.add(radiobtnDetails);

		JMenu arrangeIcons = new JMenu("Arrange icons by");
		
		JMenuItem name = new JMenuItem("Name");
		JMenuItem size = new JMenuItem("Size");
		JMenuItem modified = new JMenuItem("Modified");

		view.setMnemonic(KeyEvent.VK_V);
		arrangeIcons.setMnemonic(KeyEvent.VK_A);
		view.add(toolBars);
		view.addSeparator();
		view.add(addressBars);
		view.addSeparator();
		view.add(statusBars);
		view.addSeparator();
		view.add(radiobtnList);
		view.addSeparator();
		view.add(radiobtnDetails);
		view.addSeparator();
		arrangeIcons.add(name);
		arrangeIcons.add(size);
		arrangeIcons.add(modified);
		view.add(arrangeIcons);
		menubar.add(view);

		JMenu tool = new JMenu("Tools");
		tool.setMnemonic(KeyEvent.VK_T);
		JMenuItem folderOption = new JMenuItem("Folder Option");
		tool.add(folderOption);
		menubar.add(tool);
		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		JMenuItem introduction = new JMenuItem("Introduction");
		help.add(introduction);
		menubar.add(help);
		introduction.addActionListener(this);
		toolBars.addActionListener(this);
		addressBars.addActionListener(this);
		statusBars.addActionListener(this);
		folderOption.addActionListener(new ActionListener() {
			// JCheckBox chkPathTitle,chkPathAddress,chkHideExt;
			@Override
			public void actionPerformed(ActionEvent e) {
				JTabbedPane jtabpane = new JTabbedPane();
				chkPathTitle = new JCheckBox("Display full path in title bar");
				chkPathTitle.setSelected(isChecked);
				chkPathTitle.addItemListener(new CheckBoxListener());

				chkPathAddress = new JCheckBox(
						"Display full path in address bar");
				chkPathAddress.setSelected(isChecked);
				chkPathAddress.addItemListener(new CheckBoxListener());

				chkHideExt = new JCheckBox("Hide extension of files");
				chkHideExt.setSelected(false);
				chkHideExt.addItemListener(new CheckBoxListener());

				JPanel pnlUpper = new JPanel(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.anchor = GridBagConstraints.LINE_START;
				gbc.gridx = 0;
				gbc.gridy = 0;
				pnlUpper.add(chkPathTitle, gbc);

				gbc.gridx = 0;
				gbc.gridy = 1;
				pnlUpper.add(chkPathAddress, gbc);

				gbc.gridx = 0;
				gbc.gridy = 2;
				pnlUpper.add(chkHideExt, gbc);
				// button setting
				btnOk = new JButton("Ok");
				btnCancel = new JButton("Cancel");
				JPanel pnlLower = new JPanel();
				pnlLower.add(btnOk);
				pnlLower.add(btnCancel);

				JPanel pnlMain = new JPanel(new BorderLayout());
				pnlMain.add(pnlUpper, BorderLayout.CENTER);
				pnlMain.add(pnlLower, BorderLayout.SOUTH);
				// message and component passing in jtabbedpane
				jtabpane.addTab("View", pnlMain);

				tabbedFrame = new JFrame("Folder Option");
				tabbedFrame.add(jtabpane);
				tabbedFrame.setVisible(true);
				tabbedFrame.setSize(250, 200);
				btnOk.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						crdpane.getExpTable().setChecked(isChecked);
						tabbedFrame.dispose();
					}
				});
				btnCancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						tabbedFrame.dispose();

					}
				});
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// by default list visible  
		if (e.getActionCommand().equals("List")) {
			crdpane.getCardLayout().show(crdpane, "" + 1);

		} else if (e.getActionCommand().equals("Details")) {
			crdpane.getCardLayout().show(crdpane, "" + 0);
		}
		if (e.getActionCommand().equals("ToolBars")) {
			if (!toolBars.isSelected()) {
				AddressBar.toolbar.setVisible(false);

			}
			if (toolBars.isSelected()) {
				AddressBar.toolbar.setVisible(true);
			}
		}
		if (e.getActionCommand().equals("AddressBars")) {
			if (!addressBars.isSelected()) {
				AddressBar.pnlAddress.setVisible(false);

			}
			if (addressBars.isSelected()) {
				AddressBar.pnlAddress.setVisible(true);
			}
		}

		if (e.getActionCommand().equals("StatusBars")) {
			if (!statusBars.isSelected()) {
				pnlStatus.setVisible(false);

			}
			if (statusBars.isSelected()) {
				pnlStatus.setVisible(true);
			}
		}
		if (e.getActionCommand().equals("Introduction")) {
			File file = new File(
					"E:\\eclp\\SwingAssignmentMain\\Help.txt");

			if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(file.toURI());
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Unable to open file: "
							+ "Help File", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/** getter method for getting menu*/
	JMenuBar getMenu() {
		return menubar;
	}

	
	public void setStatusPanel(JPanel statusPanel) {
		this.pnlStatus = statusPanel;
	}
	
	public void setFrame(JFrame frame) {
		this.frame = frame;
		
	}
/**
 * 
 *
 *class for listener
 */
	private class CheckBoxListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == chkPathTitle) {
				if (!chkPathTitle.isSelected()) {
					isChecked = false;
				

				}
			}
			if (e.getSource() == chkPathAddress) {
				
				if (!chkPathAddress.isSelected()) {
					isChecked = false;
					crdpane.getExpTable().setChecked(isChecked);
				String fileStr =AddressBar.txtAddressBar.getText();
				 file=new File(fileStr);
				AddressBar.txtAddressBar.setText(file.toString());
				frame.setTitle(file.toString());
				if (file.isDirectory()) {
					String str = file.toString();
				int i = str.lastIndexOf('\\');
					String filePath = str.substring(i + 1);
					AddressBar.txtAddressBar.setText(filePath);
					frame.setTitle(filePath);
				}
				
				}
			}
			if (e.getSource() == chkHideExt) {
				if (chkHideExt.isSelected()) {
				

				}
			}
		}
	}

}
