package swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main extends JPanel {
	/*
	 * declaration of variable frame, toolbar, scrollPane
	 */
	JFrame frame;
	JToolBar toolbar;
	JScrollPane scrollPane; 
	/*
	 * declaration of toolBarStack type ToolbarUtils
	 */
	ToolbarStack toolBarStack;
	/*
	 * declaration of variable pnlStatus type JPanel
	 */
	JPanel pnlStatus;
	/*
	 * declaration of string titleAddress variable
	 */
	String titleAddress = AddressBar.txtAddressBar.getText();

	// constructor of Main class 
	Main() {
		JPanel pnl;
                                   
		frame = new JFrame();
		toolBarStack = new ToolbarStack();

		final CardPane pnlCardPane = new CardPane(toolBarStack);

		MenuBar menuExp = new MenuBar(pnlCardPane, toolBarStack);
		JMenuBar menubar = menuExp.getMenu();
		frame.setJMenuBar(menubar);
		menuExp.setFrame(frame);

		AddressBar addressbar = new AddressBar();
		JPanel topPanel = addressbar.getTopPanel();
		frame.add(topPanel, BorderLayout.NORTH);

		pnlStatus = new JPanel();
		pnlStatus.add(pnlCardPane.getList().getStatusLable());
		frame.add(pnlStatus, BorderLayout.SOUTH);
		menuExp.setStatusPanel(pnlStatus);

		final TreeView treeExp = new TreeView(pnlCardPane, toolBarStack, frame);
		JScrollPane leftScrollPane = treeExp.getTree();
		// setter for title address
		pnlCardPane.getExpTable().setFrame(frame);
		pnlCardPane.getList().setFrame(frame);
		
		pnlCardPane.getCardLayout().show(pnlCardPane, "" + 1);
		JScrollPane rightScrollPane = new JScrollPane(pnlCardPane);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				leftScrollPane, rightScrollPane);
		// fix divider size
		splitPane.setDividerSize(10);
		// to fix the position of divider
		// splitPane.setDividerLocation(200 + splitPane.getInsets().left);
		splitPane.setResizeWeight(0.25);
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);

		// event on Back button
		final JButton btnBack = (JButton) addressbar.getToolBar().getComponent(0);
		pnlCardPane.getList().setBtnBack(btnBack);
		pnlCardPane.getExpTable().setBtnBack(btnBack);
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Back")) {
					File f, file;

					try {
						f = toolBarStack.getStackBack().pop();
						toolBarStack.getStackForward().push(f);
					} catch (Exception exp) {
						btnBack.setEnabled(false);
						
					}
					try {
						file = toolBarStack.getStackBack().lastElement();
					} catch (Exception exp) {
						file = new File("My Computer");
					}
					pnlCardPane.getExpTable().generateEvent(file);
					pnlCardPane.setView(true);
					TableView expTable = pnlCardPane.getExpTable();
					expTable.getTable().setModel(
							expTable.new TableModel(file));
					expTable.getTable().getColumnModel().getColumn(0)
							.setCellRenderer(expTable.new RenderingTable());

					pnlCardPane.getList().generateEvent(file);
					
					ListView explist = pnlCardPane.getList();
					
					
					pnlCardPane.setView(false);
					explist.getList().setModel(explist.new ListModel(file));
					expTable.getTable().revalidate();
					explist.getList().revalidate();
					

				}
			}
		});

		// event on Forward button
	final	JButton forward = (JButton) addressbar.getToolBar().getComponent(1);
		pnlCardPane.getList().setBtnForward(forward);
		pnlCardPane.getExpTable().setBtnForword(forward);
		forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (e.getActionCommand().equals("Forword")) {

					File f, file;

					//try catch block for handling exception
					try {
						f = toolBarStack.getStackForward().pop();
						toolBarStack.getStackBack().push(f);
					} catch (Exception exp) {
						forward.setEnabled(false);

					}
					
					//try catch block for handling exception
					try {
						file = new File(toolBarStack.getStackForward().lastElement().toString());
					} catch (Exception exp) {

						file = new File("My Computer");
					}
					pnlCardPane.getExpTable().generateEvent(file);
					pnlCardPane.setView(true);
					TableView expTable = pnlCardPane.getExpTable();
					expTable.getTable().setModel(
							expTable.new TableModel(file));
					expTable.getTable().getColumnModel().getColumn(0)
							.setCellRenderer(expTable.new RenderingTable());

					pnlCardPane.getList().generateEvent(file);
					ListView explist = pnlCardPane.getList();

					pnlCardPane.setView(false);
					explist.getList().setModel(explist.new ListModel(file));
					expTable.getTable().revalidate();
					explist.getList().revalidate();

				}

			}
		});
		// event on Up button
		JButton btnUp = (JButton) addressbar.getToolBar().getComponent(2);
		btnUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Up")) {

					String uppath = AddressBar.txtAddressBar.getText();
					File  upFile = new File(uppath);
				//	System.out.println("Address File:"+upFile);
					
					File file=upFile.getParentFile();
								
										
					try {
						pnlCardPane.getExpTable().generateEvent(file);
						
						pnlCardPane.getList().generateEvent(file);
					} catch (Exception eaa) {

						file = new File("My Computer");
						AddressBar.txtAddressBar.setText("My Computer");
					}
					pnlCardPane.setView(true);
					TableView tblView = pnlCardPane.getExpTable();
					tblView.getTable().setModel(
							tblView.new TableModel(file));
					tblView.getTable().getColumnModel().getColumn(0)
							.setCellRenderer(tblView.new RenderingTable());

					ListView lstView = pnlCardPane.getList();

					pnlCardPane.setView(false);
					lstView.getList().setModel(lstView.new ListModel(file));
					tblView.getTable().revalidate();
					lstView.getList().revalidate();

					toolBarStack.getStackBack().push(file);

				}

			}
		});
		// event on Exit button
		
		//event on Go button
		JButton btnGo=AddressBar.getBtnGo();
		btnGo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String uppath = AddressBar.txtAddressBar.getText();
				File file = new File(uppath);
				try {
					pnlCardPane.getExpTable().generateEvent(file);
					pnlCardPane.getList().generateEvent(file);
				} catch (Exception eaa) {

					file = new File("My Computer");
					AddressBar.txtAddressBar.setText("My Computer");
				}
				pnlCardPane.setView(true);
				TableView expTable = pnlCardPane.getExpTable();
				expTable.getTable().setModel(
						expTable.new TableModel(file));
				expTable.getTable().getColumnModel().getColumn(0)
						.setCellRenderer(expTable.new RenderingTable());

				ListView explist = pnlCardPane.getList();

				pnlCardPane.setView(false);
				explist.getList().setModel(explist.new ListModel(file));
				expTable.getTable().revalidate();
				explist.getList().revalidate();

				toolBarStack.getStackBack().push(file);
							
			}
		});
				

		frame.add(splitPane, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

	}
	
	public static void main(String[] args) {
		
		//Exception Handling using try &catch block
		try {
			// Set cross-platform Java L&F 
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
		
		} catch (ClassNotFoundException e) {
		
		} catch (InstantiationException e) {
		
		} catch (IllegalAccessException e) {
		
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
		  // show the GUI.
				new Main(); 
			}
		});
		
		
		
	}

}
